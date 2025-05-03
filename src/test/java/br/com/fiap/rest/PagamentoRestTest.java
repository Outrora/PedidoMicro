package br.com.fiap.rest;

import static br.com.fiap.helps.CriarPedido.criarId;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.fiap.controller.PagamentoController;
import br.com.fiap.entities.EstadoPagamento;
import br.com.fiap.rest.pagamento.PagamentoRest;
import br.com.fiap.rest.util.MesagemResposta;

class PagamentoRestTest {

    @Mock
    PagamentoController controller;

    PagamentoRest rest;

    AutoCloseable openMocks;

    @BeforeEach
    void init() {
        openMocks = MockitoAnnotations.openMocks(this);
        rest = new PagamentoRest(controller);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void deveInserirPagamentoCorretamente() {
        var id = criarId();

        doNothing().when(controller).criarPagamento(anyString(), any());

        var resposta = rest.inserirPagamento(id, "pix");

        assertThat(resposta.getStatus())
                .isEqualTo(201);

        assertThat(resposta.getEntity())
                .isInstanceOf(MesagemResposta.class);

        verify(controller, times(1)).criarPagamento(id, "pix");
    }

    @Test
    void deveAlterarPagamentoCorretamente() {
        var id = criarId();

        doNothing().when(controller).alteraPagamento(anyString(), any());

        var resposta = rest.alterarPagamento(id, EstadoPagamento.DEVOLVIDO);

        verify(controller, times(1)).alteraPagamento(id, EstadoPagamento.DEVOLVIDO);

        assertThat(resposta.getStatus())
                .isEqualTo(202);

        assertThat(resposta.getEntity())
                .isInstanceOf(MesagemResposta.class);
    }

}
