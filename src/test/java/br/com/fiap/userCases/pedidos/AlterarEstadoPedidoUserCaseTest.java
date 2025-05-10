package br.com.fiap.userCases.pedidos;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.fiap.database.port.IPedidoPort;
import br.com.fiap.entities.EstadoPedido;
import br.com.fiap.entities.Pedido;
import br.com.fiap.events.producer.PedidoAlterarEvent;
import br.com.fiap.exception.ErroValidacao;
import br.com.fiap.helps.CriarPedido;
import br.com.fiap.userCases.pedido.AlterarEstadoPedidoUseCase;

class AlterarEstadoPedidoUserCaseTest {

    @Mock
    IPedidoPort pedidoPort;

    @Mock
    PedidoAlterarEvent event;

    AlterarEstadoPedidoUseCase useCase;
    AutoCloseable openMocks;

    @BeforeEach
    void init() {
        openMocks = MockitoAnnotations.openMocks(this);
        useCase = new AlterarEstadoPedidoUseCase(pedidoPort, event);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void deveAlterarOEstadoDoPedidoCorretamente() {
        String idPedido = "123";
        EstadoPedido novoEstado = EstadoPedido.FINALIZADO;
        Pedido pedido = CriarPedido.criarPedido();

        when(pedidoPort.buscarPorId(anyString())).thenReturn(pedido);
        doNothing().when(event).alterarPedido(anyString(), any());

        useCase.alterarEstado(idPedido, novoEstado);

        verify(pedidoPort, times(1)).buscarPorId(idPedido);
        verify(event, times(1)).alterarPedido(idPedido, novoEstado);
    }

    @Test
    void naoDeveAlterarOEstadoDoPedidoCorretamente() {
        String idPedido = "123";
        EstadoPedido novoEstado = EstadoPedido.PEDIDO_CADASTRADO;
        Pedido pedido = CriarPedido.criarPedido();

        when(pedidoPort.buscarPorId(anyString())).thenReturn(pedido);
        doNothing().when(event).alterarPedido(anyString(), any());

        assertThatThrownBy(() -> useCase.alterarEstado(idPedido, novoEstado))
                .hasMessageContaining("Não estado do pedido pode ser alterado")
                .isInstanceOf(ErroValidacao.class);

        verify(pedidoPort, never()).buscarPorId(idPedido);
        verify(event, never()).alterarPedido(idPedido, novoEstado);
    }

    @Test
    void naoDeveAlterarOEstadoDoPedidoCorretamentePoisEstarNoEstadoErrado() {
        String idPedido = "123";
        EstadoPedido novoEstado = EstadoPedido.PAGAMENTO_APROVADO;
        Pedido pedido = CriarPedido.criar(EstadoPedido.FINALIZADO);

        when(pedidoPort.buscarPorId(anyString())).thenReturn(pedido);
        doNothing().when(event).alterarPedido(anyString(), any());

        assertThatThrownBy(() -> useCase.alterarEstado(idPedido, novoEstado))
                .hasMessageContaining("Transição inválida: não é permitido mudar")
                .isInstanceOf(ErroValidacao.class);

        verify(pedidoPort, times(1)).buscarPorId(idPedido);
        verify(event, never()).alterarPedido(idPedido, novoEstado);
    }

}
