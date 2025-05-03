package br.com.fiap.it;

import org.junit.jupiter.api.Test;

import br.com.fiap.helps.CriarPedido;
import br.com.fiap.profile.MongoProfile;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;
import jakarta.ws.rs.core.MediaType;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

@QuarkusTest
@TestProfile(MongoProfile.class)
public class PedidoIt {

    @Test
    void deveInserirPedido() {
        var pedido = CriarPedido.criarRequest();

        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(pedido)
                .when()
                .post("/pedido")
                .then()
                .statusCode(201)
                .body(notNullValue());
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
