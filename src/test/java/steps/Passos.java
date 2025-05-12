package steps;

import static br.com.fiap.helps.CriarPedido.geraProdutos;
import static io.restassured.RestAssured.given;
import static org.awaitility.Awaitility.await;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.eclipse.microprofile.reactive.messaging.Message;
import org.eclipse.microprofile.reactive.messaging.spi.Connector;

import br.com.fiap.events.model.PedidoModelEvent;
import br.com.fiap.events.persistence.IProdutoEventPort;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import io.smallrye.reactive.messaging.memory.InMemoryConnector;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MediaType;

public class Passos {

    @Inject
    EstadoBean bean;

    @Inject
    IProdutoEventPort port;

    @Inject
    @Connector("smallrye-in-memory")
    InMemoryConnector connector;

    @Dado("que produto existe")
    public void queProdutoExiste() {
        bean.setPedidoCadastroEvento(connector.sink("pedido-cadastro"));
        var lista = geraProdutos(10);
        lista.forEach(x -> port.inserirOuEditarProduto(x));
        bean.setProduto(lista);
    }

    @Dado("o usuario preenche os dados do Pedido")
    public void oUsuarioPreencheOsDadosDoPedido() {
        bean.requisicao();

    }

    @Quando("o usuario envia o pedido")
    public void oUsuarioEnviaOPedido() {
        var pedido = bean.getRequest();

        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(pedido)
                .when()
                .post("/pedido")
                .then();

    }

    @Então("o sistema cadastra o pedido")
    public void oSistemaCadastraOPedido() {
        var pedidoCadastroEvento = bean.getPedidoCadastroEvento();

        await().<List<? extends Message<PedidoModelEvent>>>until(pedidoCadastroEvento::received, t -> t.size() == 1);

        PedidoModelEvent retonoMesagem = pedidoCadastroEvento.received().get(0).getPayload();
        assertThat(retonoMesagem)
                .isNotNull();

    }
}
