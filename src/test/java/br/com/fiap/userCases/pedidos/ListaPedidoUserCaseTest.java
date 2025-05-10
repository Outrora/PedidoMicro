package br.com.fiap.userCases.pedidos;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.fiap.database.port.IPedidoPort;
import br.com.fiap.helps.CriarPedido;
import br.com.fiap.userCases.pedido.ListaPedidoUserCase;

class ListaPedidoUserCaseTest {

    @Mock
    IPedidoPort port;

    ListaPedidoUserCase useCase;
    AutoCloseable openMocks;

    @BeforeEach
    void init() {
        openMocks = MockitoAnnotations.openMocks(this);
        useCase = new ListaPedidoUserCase(port);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void deveRetornarTodosOsPedidosCorretamente() {
        var lista = CriarPedido.criarListaPedido();

        when(port.listarPedidos()).thenReturn(lista);

        var retorno = useCase.listarTodosOsPedidos();

        verify(port, times(1)).listarPedidos();

        assertThat(retorno)
                .isNotEmpty()
                .isEqualTo(lista);
    }

    @Test
    void deveRetornarOPedidoPorId() {
        var pedido = CriarPedido.criarPedido();

        when(port.buscarPorId(anyString())).thenReturn(pedido);

        var retorno = useCase.bucarPorId(pedido.getId().get());

        assertThat(retorno).isNotNull().isEqualTo(pedido);

        verify(port, times(1)).buscarPorId(pedido.getId().get());
    }

}
