package br.com.fiap.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.fiap.entities.EstadoPagamento;
import br.com.fiap.userCases.pagamento.AlterarEstadoPagamentoUseCase;
import br.com.fiap.userCases.pagamento.CriarPagamentoUseCase;

class PagamentoControllerTest {

    @Mock
    CriarPagamentoUseCase criarPagamento;

    @Mock
    AlterarEstadoPagamentoUseCase alterarPagamento;

    PagamentoController controller;

    AutoCloseable openMocks;

    @BeforeEach
    void init() {
        openMocks = MockitoAnnotations.openMocks(this);
        controller = new PagamentoControllerImpl(criarPagamento, alterarPagamento);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void deveRetornaCorretamentoQuandoCriarPagamento() {

        var id = "4f56ds";
        var forma = "Cartao";

        doNothing()
                .when(criarPagamento)
                .criarPagamento(anyString(), anyString());

        controller.criarPagamento(id, forma);

        ArgumentCaptor<String> idCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> formaCaptor = ArgumentCaptor.forClass(String.class);
        verify(criarPagamento, times(1))
                .criarPagamento(idCaptor.capture(), formaCaptor.capture());

        assertThat(idCaptor.getValue()).isEqualTo(id);
        assertThat(formaCaptor.getValue()).isEqualTo(forma);
    }

    @Test
    void deveRetornaCorretamentoQuandoAlteraPagamento() {

        var id = "4f56ds";
        var estado = EstadoPagamento.PAGO;

        doNothing()
                .when(alterarPagamento)
                .alterarPagamento(anyString(), any(EstadoPagamento.class));
        ;

        controller.alteraPagamento(id, estado);

        ArgumentCaptor<String> idCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<EstadoPagamento> formaCaptor = ArgumentCaptor.forClass(EstadoPagamento.class);
        verify(alterarPagamento, times(1))
                .alterarPagamento(idCaptor.capture(), formaCaptor.capture());

        assertThat(idCaptor.getValue()).isEqualTo(id);
        assertThat(formaCaptor.getValue()).isEqualTo(estado);
    }

}
