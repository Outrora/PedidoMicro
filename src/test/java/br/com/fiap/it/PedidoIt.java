package br.com.fiap.it;

import org.eclipse.microprofile.reactive.messaging.Message;
import org.eclipse.microprofile.reactive.messaging.spi.Connector;
import org.junit.jupiter.api.Test;

import br.com.fiap.adapters.models.PedidosRequestAdapter;
import br.com.fiap.adapters.models.PedidosRequestAdapter.ProdutoRequest;
import br.com.fiap.events.model.PedidoModelEvent;
import br.com.fiap.events.persistence.IProdutoEventPort;
import br.com.fiap.helps.CriarPedido;
import br.com.fiap.profile.KafkaTestResourceLifecycleManager;
import br.com.fiap.profile.MongoProfile;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;
import io.smallrye.reactive.messaging.memory.InMemoryConnector;
import io.smallrye.reactive.messaging.memory.InMemorySink;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MediaType;

import static br.com.fiap.helps.CriarPedido.geraProdutos;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@QuarkusTest
@TestProfile(MongoProfile.class)
@QuarkusTestResource(KafkaTestResourceLifecycleManager.class)
public class PedidoIt {

    @Inject
    IProdutoEventPort port;

    @Inject
    @Connector("smallrye-in-memory")
    InMemoryConnector connector;

    private PedidosRequestAdapter inserirProduto() {
        var produto = geraProdutos(10);
        produto.forEach(x -> port.inserirOuEditarProduto(x));
        Set<ProdutoRequest> listaProduto = produto.stream()
                .map(x -> new PedidosRequestAdapter.ProdutoRequest(x.getId(), 2))
                .collect(Collectors.toSet());

        return CriarPedido.criarRequest(listaProduto);

    }

    @Test
    void deveInserirPedido() {
        var pedido = inserirProduto();

        InMemorySink<PedidoModelEvent> pedidoCadastroEvento = connector.sink("pedido-cadastro");

        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(pedido)
                .when()
                .post("/pedido")
                .then()
                .statusCode(201)
                .body(notNullValue());

        await().<List<? extends Message<PedidoModelEvent>>>until(pedidoCadastroEvento::received, t -> t.size() == 1);

        PedidoModelEvent retonoMesagem = pedidoCadastroEvento.received().get(0).getPayload();
        assertThat(retonoMesagem)
                .isNotNull();

    }

    @Test
    void deveListaPedidoCorretamente() {
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .get("/pedido/todos")
                .then()
                .log().all()
                .statusCode(200);
    }

}
