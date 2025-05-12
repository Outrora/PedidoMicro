package br.com.fiap.it;

import static org.awaitility.Awaitility.await;
import java.util.concurrent.TimeUnit;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;

import org.eclipse.microprofile.reactive.messaging.spi.Connector;
import org.junit.jupiter.api.Test;

import br.com.fiap.events.model.ProdutoModel;
import br.com.fiap.events.persistence.ProdutoRepository;
import br.com.fiap.profile.KafkaTestResourceLifecycleManager;
import br.com.fiap.profile.MongoProfile;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;
import io.quarkus.test.junit.mockito.InjectSpy;
import io.smallrye.reactive.messaging.memory.InMemoryConnector;
import io.smallrye.reactive.messaging.memory.InMemorySource;
import jakarta.inject.Inject;

@QuarkusTest
@TestProfile(MongoProfile.class)
@QuarkusTestResource(KafkaTestResourceLifecycleManager.class)
class ProdutoEventoIt {

    @Inject
    @Connector("smallrye-in-memory")
    InMemoryConnector connector;

    @InjectSpy
    ProdutoRepository repository;

    @Test
    void deverCasdastrarProduto() throws InterruptedException {
        InMemorySource<ProdutoModel> produtoIn = connector.source("produto-cadastrado");

        ProdutoModel model = new ProdutoModel(50, "teste", BigDecimal.ONE);

        var test = produtoIn.send(model);
        test.complete();

        await().atMost(2, TimeUnit.SECONDS)
                .untilAsserted(() -> verify(repository, times(1)).inserirOuEditarProduto(any()));
    }
}
