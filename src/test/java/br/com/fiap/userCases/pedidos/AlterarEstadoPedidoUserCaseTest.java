package br.com.fiap.userCases.pedidos;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.fiap.database.port.IPedidoPort;
import br.com.fiap.entities.EstadoPedido;
import br.com.fiap.userCases.pedido.AlterarEstadoPedidoUseCase;

class AlterarEstadoPedidoUserCaseTest {
    @Mock
    IPedidoPort pedidoPort;

    AlterarEstadoPedidoUseCase useCase;
    AutoCloseable openMocks;

    @BeforeEach
    void init() {
        openMocks = MockitoAnnotations.openMocks(this);
        useCase = new AlterarEstadoPedidoUseCase(pedidoPort);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void deveAlterarOEstadoDoPedidoCorretamente() {
        String idPedido = "123";
        EstadoPedido novoEstado = EstadoPedido.FINALIZADO;

        useCase.alterarEstado(idPedido, novoEstado);

        verify(pedidoPort, times(1)).editarStatusPedido(idPedido, novoEstado);
    }
}
