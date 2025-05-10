package br.com.fiap.event.consumer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.fiap.database.port.IPedidoPort;
import br.com.fiap.entities.EstadoPedido;
import br.com.fiap.events.consumer.PedidoConsumer;
import br.com.fiap.events.model.MPedidoAlterarEvent;

class PedidoConsumerTest {

    @Mock
    private IPedidoPort port;
    private PedidoConsumer consumer;

    AutoCloseable openMocks;

    @BeforeEach
    void init() {
        openMocks = MockitoAnnotations.openMocks(this);
        consumer = new PedidoConsumer(port);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void deveReceberCorretamente() {
        var event = new MPedidoAlterarEvent("1", EstadoPedido.CANCELADO);

        doNothing().when(port).editarStatusPedido(anyString(), any());

        consumer.receber(event);
        verify(port, times(1)).editarStatusPedido("1", EstadoPedido.CANCELADO);
    }

}
