package br.com.fiap.userCases.pagamento;

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
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.fiap.database.port.IPagamentoPort;
import br.com.fiap.entities.EstadoPagamento;
import br.com.fiap.entities.Pagamento;
import br.com.fiap.entities.Pedido;
import br.com.fiap.helps.CriarPedido;
import br.com.fiap.userCases.pedido.ListaPedidoUserCase;

class CriarPagamentoUseCaseTest {
    @Mock
    ListaPedidoUserCase listaPedido;

    @Mock
    IPagamentoPort pagamentoPort;

    CriarPagamentoUseCase useCase;
    AutoCloseable openMocks;

    @BeforeEach
    void init() {
        openMocks = MockitoAnnotations.openMocks(this);
        useCase = new CriarPagamentoUseCase(listaPedido, pagamentoPort);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void deveCriarPagametoCorretamente() {

        var pedido = CriarPedido.criar();

        when(listaPedido.bucarPorId(anyString())).thenReturn(pedido);
        doNothing().when(pagamentoPort).cadastrarPagamento(any());

        useCase.criarPagamento("1", "PIX");

        var captor = ArgumentCaptor.forClass(Pagamento.class);
        verify(listaPedido, times(1)).bucarPorId("1");
        verify(pagamentoPort, times(1)).cadastrarPagamento(captor.capture());

        assertThat(captor.getValue())
                .isNotNull();

        assertThat(captor.getValue().formaPagamento())
                .isEqualTo("PIX");

        assertThat(captor.getValue().status())
                .isEqualTo(EstadoPagamento.PENDENTE);

        assertThat(captor.getValue().pedido())
                .isNotEmpty()
                .isPresent();
    }
}
