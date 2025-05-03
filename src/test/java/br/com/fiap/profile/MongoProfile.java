package br.com.fiap.profile;

import static br.com.fiap.helps.CriarPedido.listaPedidoDto;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.bson.Document;
import org.testcontainers.containers.MongoDBContainer;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

import br.com.fiap.helps.CriarPedido;
import io.quarkus.test.junit.QuarkusTestProfile;

public class MongoProfile implements QuarkusTestProfile {
    static final MongoDBContainer MONGO = new MongoDBContainer("mongo:6.0");

    static {
        MONGO.start();
    }

    @Override
    public String getConfigProfile() {
        return "testcontainers"; // ativa application-testcontainers.properties, se existir
    }

    @Override
    public Map<String, String> getConfigOverrides() {
        return Map.of(
                "quarkus.mongodb.connection-string", MONGO.getReplicaSetUrl());
    }

    private static void seedBancoDados() {
        try (MongoClient mongoClient = MongoClients.create(MONGO.getReplicaSetUrl())) {
            MongoDatabase db = mongoClient.getDatabase("pedidos");

            List<Document> documents = CriarPedido.listaPedidoDto()
                    .stream()
                    .map(pedidoDTO -> new Document()
                            .append("_id", pedidoDTO.getId())
                            .append("idCliente", pedidoDTO.getCliente())
                            .append("produtos", pedidoDTO.getProdutos()
                                    .stream()
                                    .map(produto -> new Document()
                                            .append("id", produto.id())
                                            .append("nome", produto.nome())
                                            .append("quantidade", produto.quantidade())
                                            .append("preco", produto.preco()))
                                    .toList())
                            .append("estado", pedidoDTO.getEstadoPedido())
                            .append("pagamento", new Document()
                                    .append("formaPagamento", pedidoDTO.getPagamento().getFormaPagamento())
                                    .append("EstadoPagamento", pedidoDTO.getPagamento().getStatus())))
                    .toList();

            db.getCollection("pedidos").insertMany(documents);
        }
    }
}
