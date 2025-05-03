package br.com.fiap.userCases.pagamento;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.fiap.database.port.IPagamentoPort;
import br.com.fiap.entities.EstadoPagamento;
import br.com.fiap.exception.ErroValidacao;
import br.com.fiap.helps.CriarPedido;
import br.com.fiap.userCases.pedido.ListaPedidoUserCase;

public class AlterarPagamentoUseCaseTest {

    @Mock
    ListaPedidoUserCase listaPedido;

    @Mock
    IPagamentoPort pagamentoPort;

    AlterarEstadoPagamentoUseCase useCase;
    AutoCloseable openMocks;

    @BeforeEach
    void init() {
        openMocks = MockitoAnnotations.openMocks(this);
        useCase = new AlterarEstadoPagamentoUseCase(listaPedido, pagamentoPort);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void deveAlterarEstadoPagamentoComSucesso() {
        String id = "123";
        var pedido = CriarPedido.criarPedido(EstadoPagamento.PENDENTE);
        EstadoPagamento novoEstado = EstadoPagamento.PAGO;

        when(listaPedido.bucarPorId(id)).thenReturn(pedido);

        useCase.alterarPagamento(id, novoEstado);

        verify(pagamentoPort, times(1)).alterarPagamento(id, novoEstado);
    }

    @Test
    void deveLancarErroQuandoPagamentoNaoEncontrado() {
        String id = "123";
        var pedido = CriarPedido.criar();

        when(listaPedido.bucarPorId(id)).thenReturn(pedido);

        assertThatThrownBy(() -> useCase.alterarPagamento(id, EstadoPagamento.CANCELADO))
                .isInstanceOf(ErroValidacao.class)
                .hasMessageContaining("Pagamento não encontrado");

        verify(pagamentoPort, never()).alterarPagamento(anyString(), any());
    }

    @Test
    void deveLancarErroQuandoPagamentoJaEstaEmEstadoFinal() {
        String id = "123";
        var pedido = CriarPedido.criarPedido(EstadoPagamento.CANCELADO);

        when(listaPedido.bucarPorId(id)).thenReturn(pedido);

        assertThatThrownBy(() -> useCase.alterarPagamento(id, EstadoPagamento.PAGO))
                .isInstanceOf(ErroValidacao.class)
                .hasMessageContaining("Pagamento não pode ser alterado pois já está em estado final");

        verify(pagamentoPort, never()).alterarPagamento(anyString(), any());
    }

    @Test
    void deveLancarErroQuandoPagamentoJaEstaNoEstadoInformado() {
        String id = "123";
        var pedido = CriarPedido.criarPedido(EstadoPagamento.PENDENTE);

        when(listaPedido.bucarPorId(id)).thenReturn(pedido);

        assertThatThrownBy(() -> useCase.alterarPagamento(id, EstadoPagamento.PENDENTE))
                .isInstanceOf(ErroValidacao.class)
                .hasMessageContaining("Pagamento já está no estado final informado");

        verify(pagamentoPort, never()).alterarPagamento(anyString(), any());
    }

}
