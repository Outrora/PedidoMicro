package br.com.fiap.helps;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.bson.types.ObjectId;

import br.com.fiap.adapters.models.PedidosRequestAdapter;
import br.com.fiap.database.dto.PedidoDTO;
import br.com.fiap.entities.EstadoPagamento;
import br.com.fiap.entities.EstadoPedido;
import br.com.fiap.entities.Pedido;
import br.com.fiap.entities.Produto;
import br.com.fiap.mapper.PedidoMapper;

public abstract class CriarPedido {
    private static Random random = new Random();

    public static Pedido criar() {
        Integer idCliente = random.nextInt(1000);
        var idPedido = new ObjectId().toHexString();
        return new Pedido(idPedido, idCliente, geraProdutos(), EstadoPedido.EM_PREPARACAO);
    }

    public static PedidoDTO criarPedidoDTO() {
        var pedido = PedidoMapper.toDto(criarPedido(), Optional.of(CriarCliente.criarCliente()));
        pedido.setPagamento(CriarPagamento.criarDTO());
        return pedido;
    }

    public static List<PedidoDTO> listaPedidoDto() {
        return IntStream.range(2, 10)
                .mapToObj(i -> criarPedidoDTO())
                .toList();

    }

    public static Pedido criarPedido() {
        var pedido = criar();
        pedido.alteraPagamento(CriarPagamento.criarPagamento(pedido));
        return pedido;
    }

    public static Pedido criarPedido(EstadoPagamento pagamento) {
        var pedido = criar();
        pedido.alteraPagamento(CriarPagamento.criarPagamento(pedido, pagamento));
        return pedido;
    }

    public static List<Pedido> criarListaPedido() {
        return IntStream.range(1, 10)
                .mapToObj(i -> criarPedido())
                .toList();
    }

    public static PedidosRequestAdapter criarRequest() {
        var id = random.nextInt(1, 5);
        return criarRequest(id);
    }

    public static PedidosRequestAdapter criarRequest(Integer id) {

        var lista = IntStream.range(1, 10)
                .mapToObj(i -> new PedidosRequestAdapter.ProdutoRequest(i, random.nextInt(100)))
                .collect(Collectors.toSet());

        return new PedidosRequestAdapter(id, lista);
    }

    public static List<Produto> geraProdutos() {
        int quantidade = random.nextInt(1, 5);
        return IntStream.range(1, quantidade)
                .mapToObj(i -> gerarProduto())
                .toList();

    }

    public static Produto gerarProduto() {
        Integer id = random.nextInt(1000);
        return gerarProduto(id);
    }

    public static Produto gerarProduto(Integer id) {
        String nome = "Produto" + id;
        Integer quantidade = random.nextInt(1, 20);
        BigDecimal preco = BigDecimal.valueOf(random.nextDouble(10.0, 500.0));

        return new Produto(
                id,
                nome,
                Optional.of(quantidade),
                preco);
    }

    public static String criarId() {
        return new ObjectId().toHexString();
    }
}
