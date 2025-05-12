package br.com.fiap.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.fiap.adapters.models.PedidosRequestAdapter;
import br.com.fiap.entities.EstadoPagamento;
import br.com.fiap.entities.EstadoPedido;
import br.com.fiap.helps.CriarPedido;
import br.com.fiap.userCases.pedido.AlterarEstadoPedidoUseCase;
import br.com.fiap.userCases.pedido.InserirPedidoUseCase;
import br.com.fiap.userCases.pedido.ListaPedidoUserCase;

class PedidoControllerTest {

    @Mock
    InserirPedidoUseCase inserirPedido;

    @Mock
    AlterarEstadoPedidoUseCase alterarEstadoPedido;

    @Mock
    ListaPedidoUserCase listaPedido;

    AutoCloseable openMocks;
    PedidoController controller;

    @BeforeEach
    void init() {
        openMocks = MockitoAnnotations.openMocks(this);
        controller = new PedidoControllerImpl(inserirPedido, alterarEstadoPedido, listaPedido);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void deveRodaCorretamenteAoCriarPedido() {

        var adapter = CriarPedido.criarRequest();

        doNothing()
                .when(inserirPedido)
                .inserirPedido(any(PedidosRequestAdapter.class));

        controller.criarPedido(adapter);

        var captor = ArgumentCaptor.forClass(PedidosRequestAdapter.class);
        verify(inserirPedido, times(1)).inserirPedido(captor.capture());

        assertThat(captor.getValue())
                .isNotNull()
                .isEqualTo(adapter);

    }

    @Test
    void deveRodaCorretamentoAlterarEstadoPedido() {
        var id = new ObjectId().toHexString();
        var estado = EstadoPedido.EM_PREPARACAO;

        doNothing()
                .when(alterarEstadoPedido)
                .alterarEstado(anyString(), any(EstadoPedido.class));

        controller.alteraEstadoPedido(id, estado);

        verify(alterarEstadoPedido, times(1)).alterarEstado(id, estado);
    }

    @Test
    void deveRodaListarTodosOsPedidosCorretamente() {
        var lista = CriarPedido.criarListaPedido();

        when(listaPedido.listarTodosOsPedidos()).thenReturn(lista);

        var retorno = controller.listarTodosOsPedidos();

        verify(listaPedido, times(1)).listarTodosOsPedidos();

        assertThat(retorno)
                .isNotNull()
                .isEqualTo(lista);

    }

}
