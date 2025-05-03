package br.com.fiap.userCases.pedidos;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.fiap.adapters.models.PedidosRequestAdapter;
import br.com.fiap.adapters.pedido.ClienteRequest;
import br.com.fiap.adapters.pedido.ProdutoRequest;
import br.com.fiap.database.port.IPedidoPort;
import br.com.fiap.entities.Cliente;
import br.com.fiap.entities.Pedido;
import br.com.fiap.entities.Produto;
import br.com.fiap.exception.ResultadoNaoEncontrado;
import br.com.fiap.helps.CriarCliente;
import br.com.fiap.helps.CriarPedido;
import br.com.fiap.userCases.pedido.InserirPedidoUseCase;
import br.com.fiap.userCases.pedido.ListaPedidoUserCase;

class InserirPedidoUserCaseTest {

    @Mock
    ClienteRequest clienteRequest;
    @Mock
    ProdutoRequest produtoRequest;
    @Mock
    IPedidoPort pedidoPort;

    InserirPedidoUseCase useCase;

    AutoCloseable openMocks;

    @BeforeEach
    void init() {
        openMocks = MockitoAnnotations.openMocks(this);
        useCase = new InserirPedidoUseCase(clienteRequest, produtoRequest, pedidoPort);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void deveCadastraCorretamenteUmPedidoComIdCliente() {
        var request = CriarPedido.criarRequest();
        var cliente = CriarCliente.criarCliente();
        List<Produto> listaProduto = request.produtos()
                .stream()
                .map(p -> CriarPedido.gerarProduto(p.id()))
                .toList();

        when(clienteRequest.buscarClientePorId(anyString()))
                .thenReturn(cliente);

        when(produtoRequest.buscarTodosProdutos(anySet()))
                .thenReturn(listaProduto);

        doNothing().when(pedidoPort).cadastrarPedido(any(), any());

        useCase.inserirPedido(request);

        var captorPedido = ArgumentCaptor.forClass(Pedido.class);

        verify(clienteRequest, times(1)).buscarClientePorId(anyString());
        verify(produtoRequest, times(1)).buscarTodosProdutos(anySet());
        verify(pedidoPort, times(1)).cadastrarPedido(captorPedido.capture(), any());

        var pedido = captorPedido.getValue();
        assertThat(pedido)
                .isNotNull();

        assertThat(pedido.getIdCliente())
                .isNotNull()
                .isEqualTo(request.idCliente());

        assertThat(pedido.getProdutos())
                .isNotEmpty()
                .hasSize(listaProduto.size());

    }

    @Test
    void deveCadastraCorretamenteUmPedidoQuandoNaoTemOIdClienteEZero() {
        var request = CriarPedido.criarRequest(0);
        List<Produto> listaProduto = request.produtos()
                .stream()
                .map(p -> CriarPedido.gerarProduto(p.id()))
                .toList();

        when(produtoRequest.buscarTodosProdutos(anySet()))
                .thenReturn(listaProduto);

        doNothing().when(pedidoPort).cadastrarPedido(any(), any());

        useCase.inserirPedido(request);

        var captorPedido = ArgumentCaptor.forClass(Pedido.class);

        verify(clienteRequest, times(0)).buscarClientePorId(anyString());
        verify(produtoRequest, times(1)).buscarTodosProdutos(anySet());
        verify(pedidoPort, times(1)).cadastrarPedido(captorPedido.capture(), any());

        var pedido = captorPedido.getValue();
        assertThat(pedido)
                .isNotNull();

        assertThat(pedido.getIdCliente())
                .isNotNull()
                .isEqualTo(request.idCliente());

        assertThat(pedido.getProdutos())
                .isNotEmpty()
                .hasSize(listaProduto.size());

    }

    @Test
    void naoDeveCadastraCorretamenteUmPedidoQuandoPoisNaoEncontrouOProduto() {
        var request = CriarPedido.criarRequest(0);
        List<Produto> listaProduto = request.produtos()
                .stream()
                .map(p -> CriarPedido.gerarProduto(p.id() + 12))
                .toList();

        when(produtoRequest.buscarTodosProdutos(anySet()))
                .thenReturn(listaProduto);

        doNothing().when(pedidoPort).cadastrarPedido(any(), any());

        assertThatThrownBy(() -> useCase.inserirPedido(request))
                .isInstanceOf(ResultadoNaoEncontrado.class)
                .hasMessageContaining("Produto não encontrado na requisição");

        verify(produtoRequest, times(1)).buscarTodosProdutos(anySet());
        verify(pedidoPort, times(0)).cadastrarPedido(any(), any());

    }

}
