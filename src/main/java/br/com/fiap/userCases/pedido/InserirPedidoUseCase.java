package br.com.fiap.userCases.pedido;

import java.util.Set;
import java.util.stream.Collectors;

import br.com.fiap.adapters.models.PedidosRequestAdapter;
import br.com.fiap.database.port.IPedidoPort;
import br.com.fiap.entities.Pedido;
import br.com.fiap.events.persistence.IProdutoEventPort;
import br.com.fiap.exception.ResultadoNaoEncontrado;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import lombok.AllArgsConstructor;

@RequestScoped
@AllArgsConstructor(onConstructor = @__(@Inject))
public class InserirPedidoUseCase {

    private final IProdutoEventPort produtoPort;
    private final IPedidoPort pedidoPort;

    public void inserirPedido(PedidosRequestAdapter pedidoResquest) {

        var quantidadePorProduto = pedidoResquest.getProdutos()
                .stream()
                .collect(Collectors.toMap(p -> p.id(), p -> p.quantidade()));

        Set<Integer> listaIds = quantidadePorProduto.keySet();

        var produtos = produtoPort.buscarTodosProdutos(listaIds);

        for (var produto : produtos) {
            var quantidade = quantidadePorProduto.get(produto.getId());

            if (quantidade == null) {
                throw new ResultadoNaoEncontrado("Produto não encontrado na requisição: " + produto.getId());
            }
            produto.alterarQuantidade(quantidade);
        }

        var pedido = new Pedido(pedidoResquest.getCodigoCliente(), produtos);
        pedidoPort.cadastrarPedido(pedido);
    }
}
