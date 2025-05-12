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

import br.com.fiap.events.model.MPedidoAlterarEvent;
import br.com.fiap.events.model.PedidoModelEvent;
import br.com.fiap.events.producer.PedidoCadastradoEvent;
import br.com.fiap.events.producer.ProdutoProducer;
import br.com.fiap.exception.ErroValidacao;

class PedidoCadastroEventTest {

    AutoCloseable openMocks;

    PedidoCadastradoEvent producer;

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
        var model = new PedidoModelEvent();
        when(emiterCastadrado.send(any(PedidoModelEvent.class))).thenReturn(CompletableFuture.completedFuture(null));
        producer.cadastouPedido(model);
        verify(emiterCastadrado, times(1)).send(any(PedidoModelEvent.class));
    }

    @Test
    void naoDeveEmitirUmaMesagemCorretamente() {
        var model = new PedidoModelEvent();
        CompletableFuture<Void> futureComErro = new CompletableFuture<>();
        futureComErro.completeExceptionally(new RuntimeException("Falha de envio"));
        when(emiterCastadrado.send(any(PedidoModelEvent.class))).thenReturn(futureComErro);
        assertThatThrownBy(() -> producer.cadastouPedido(model))
                .hasMessage("Erro")
                .isInstanceOf(ErroValidacao.class);
        verify(emiterCastadrado, times(1)).send(any(PedidoModelEvent.class));
    }

}
