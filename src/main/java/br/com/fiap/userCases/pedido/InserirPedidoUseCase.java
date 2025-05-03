package br.com.fiap.userCases.pedido;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import br.com.fiap.adapters.models.PedidosRequestAdapter;
import br.com.fiap.adapters.pedido.ClienteRequest;
import br.com.fiap.adapters.pedido.ProdutoRequest;
import br.com.fiap.database.port.IPedidoPort;
import br.com.fiap.entities.Cliente;
import br.com.fiap.entities.Pedido;
import br.com.fiap.exception.ResultadoNaoEncontrado;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import lombok.AllArgsConstructor;

@RequestScoped
@AllArgsConstructor(onConstructor = @__(@Inject))
public class InserirPedidoUseCase {

    private final ClienteRequest clienteRequest;
    private final ProdutoRequest produtoRequest;
    private final IPedidoPort pedidoPort;

    public void inserirPedido(PedidosRequestAdapter pedidoResquest) {
        Optional<Cliente> cliente = Optional.empty();
        var idCliente = pedidoResquest.idCliente();

        if (idCliente != 0) {
            cliente = Optional.of(clienteRequest.buscarClientePorId(idCliente.toString()));
        }
        var quantidadePorProduto = pedidoResquest.produtos()
                .stream()
                .collect(Collectors.toMap(p -> p.id(), p -> p.quantidade()));

        Set<Integer> listaIds = quantidadePorProduto.keySet();

        var produtos = produtoRequest.buscarTodosProdutos(listaIds);

        for (var produto : produtos) {
            var quantidade = quantidadePorProduto.get(produto.getId());

            if (quantidade == null) {
                throw new ResultadoNaoEncontrado("Produto não encontrado na requisição: " + produto.getId());
            }
            produto.alterarQuantidade(quantidade);
        }

        var pedido = new Pedido(pedidoResquest.idCliente(), produtos);
        pedidoPort.cadastrarPedido(pedido, cliente);
    }
}
