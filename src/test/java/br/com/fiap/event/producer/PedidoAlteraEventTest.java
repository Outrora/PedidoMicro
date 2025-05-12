package br.com.fiap.event.producer;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.concurrent.CompletableFuture;

import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.fiap.entities.EstadoPedido;
import br.com.fiap.events.model.MPedidoAlterarEvent;
import br.com.fiap.events.model.PedidoModelEvent;
import br.com.fiap.events.producer.PedidoAlterarEvent;
import br.com.fiap.events.producer.ProdutoProducer;
import br.com.fiap.exception.ErroValidacao;

class PedidoAlteraEventTest {

    AutoCloseable openMocks;

    PedidoAlterarEvent producer;

    @Mock
    Emitter<PedidoModelEvent> emiterCastadrado;
    @Mock
    Emitter<MPedidoAlterarEvent> emiterAlterado;

    @BeforeEach
    void init() {
        openMocks = MockitoAnnotations.openMocks(this);
        producer = new ProdutoProducer(emiterCastadrado, emiterAlterado);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void deveEmitirUmaMesagemCorretamente() {
        when(emiterAlterado.send(any(MPedidoAlterarEvent.class))).thenReturn(CompletableFuture.completedFuture(null));
        producer.alterarPedido("1", EstadoPedido.EM_PREPARACAO);
        verify(emiterAlterado, times(1)).send(any(MPedidoAlterarEvent.class));
    }

    @Test
    void naoDeveEmitirUmaMesagemCorretamente() {
        CompletableFuture<Void> futureComErro = new CompletableFuture<>();
        futureComErro.completeExceptionally(new RuntimeException("Falha de envio"));
        when(emiterAlterado.send(any(MPedidoAlterarEvent.class))).thenReturn(futureComErro);
        assertThatThrownBy(() -> producer.alterarPedido("1", EstadoPedido.EM_PREPARACAO))
                .hasMessage("Erro")
                .isInstanceOf(ErroValidacao.class);
        verify(emiterAlterado, times(1)).send(any(MPedidoAlterarEvent.class));
    }

}
