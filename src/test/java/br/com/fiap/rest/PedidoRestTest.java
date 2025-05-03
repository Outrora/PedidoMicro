package br.com.fiap.rest;

import static br.com.fiap.helps.CriarPedido.criarId;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.fiap.controller.PedidoController;
import br.com.fiap.entities.EstadoPedido;
import br.com.fiap.helps.CriarPedido;
import br.com.fiap.rest.pedido.PedidoRest;
import br.com.fiap.rest.util.MesagemResposta;

class PedidoRestTest {

    @Mock
    PedidoController controller;

    PedidoRest rest;

    AutoCloseable openMocks;

    @BeforeEach
    void init() {
        openMocks = MockitoAnnotations.openMocks(this);
        rest = new PedidoRest(controller);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void deveInserirPedidoCorretamente() {
        var request = CriarPedido.criarRequest();

        doNothing().when(controller).criarPedido(any());

        var resposta = rest.inserirPedido(request);

        assertThat(resposta.getStatus())
                .isEqualTo(201);

        assertThat(resposta.getEntity())
                .isInstanceOf(MesagemResposta.class);

        verify(controller, times(1)).criarPedido(request);
    }

    @Test
    void deveListarTodosOsPedidosCorretamente() {
        var pedidos = CriarPedido.criarListaPedido();

        when(controller.listarTodosOsPedidos()).thenReturn(pedidos);

        var resposta = rest.listarTodos();

        assertThat(resposta)
                .isNotNull()
                .isInstanceOf(List.class)
                .hasSize(pedidos.size());

        verify(controller, times(1)).listarTodosOsPedidos();
    }

    @Test
    void deveAlterarEstadoPedidoCorretamente() {
        var id = criarId();

        doNothing().when(controller).alteraEstadoPedido(anyString(), any());

        var resposta = rest.alteraEstadoPedido(id, EstadoPedido.EM_PREPARACAO);

        verify(controller, times(1)).alteraEstadoPedido(id, EstadoPedido.EM_PREPARACAO);

        assertThat(resposta.getStatus())
                .isEqualTo(202);

        assertThat(resposta.getEntity())
                .isInstanceOf(MesagemResposta.class);
    }
}