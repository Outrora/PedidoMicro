package br.com.fiap.userCases.pedido;

import java.util.Set;
import java.util.stream.Collectors;

import br.com.fiap.adapters.models.PedidosRequestAdapter;
import br.com.fiap.database.port.IPedidoPort;
import br.com.fiap.entities.Pedido;
import br.com.fiap.entities.Produto;
import br.com.fiap.events.model.PedidoModelEvent;
import br.com.fiap.events.persistence.IProdutoEventPort;
import br.com.fiap.events.producer.PedidoCadastradoEvent;
import br.com.fiap.exception.ResultadoNaoEncontrado;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import lombok.AllArgsConstructor;

@RequestScoped
@AllArgsConstructor(onConstructor = @__(@Inject))
public class InserirPedidoUseCase {

    private final IProdutoEventPort produtoPort;
    private final IPedidoPort pedidoPort;
    private final PedidoCadastradoEvent cadastrado;

    public void inserirPedido(PedidosRequestAdapter pedidoResquest) {

        var quantidadePorProduto = pedidoResquest.getProdutos()
                .stream()
                .collect(Collectors.toMap(p -> p.id(), p -> p.quantidade()));

        Set<Integer> listaIds = quantidadePorProduto.keySet();

        var produtos = produtoPort.buscarTodosProdutos(listaIds);

        for (var id : listaIds) {
            Produto produto = produtos.stream()
                    .filter(x -> x.getId() == id)
                    .findFirst()
                    .orElseThrow(
                            () -> new ResultadoNaoEncontrado(
                                    "Produto não encontrado na requisição: " + id));

            Integer quant = quantidadePorProduto.get(id);
            produto.alterarQuantidade(quant);
        }

        var pedido = new Pedido(pedidoResquest.getCodigoCliente(), produtos);
        var id = pedidoPort.cadastrarPedido(pedido);

        var event = new PedidoModelEvent(pedidoResquest, id);
        cadastrado.cadastouPedido(event);
    }
}
