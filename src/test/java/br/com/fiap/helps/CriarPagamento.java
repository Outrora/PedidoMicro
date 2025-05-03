package br.com.fiap.helps;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import br.com.fiap.adapters.models.PedidosRequestAdapter;
import br.com.fiap.entities.EstadoPagamento;
import br.com.fiap.entities.Pagamento;
import br.com.fiap.entities.Pedido;

public abstract class CriarPagamento {

    private static List<String> formas = List.of("Cartao", "Boleto", "Pix", "Dinheiro");
    private static EstadoPagamento[] estados = EstadoPagamento.values();
    private static Random random = new Random();

    public static Pagamento criarPagamento() {
        return Pagamento.builder()
                .formaPagamento(formas.get(random.nextInt(formas.size())))
                .status(estados[random.nextInt(estados.length)])
                .build();

    }

    public static Pagamento criarPagamento(Pedido pedido) {
        return Pagamento.builder()
                .formaPagamento(formas.get(random.nextInt(formas.size())))
                .status(estados[random.nextInt(estados.length)])
                .pedido(Optional.of(pedido))
                .build();

    }

    public static Pagamento criarPagamento(Pedido pedido, EstadoPagamento estadoPagamento) {
        return Pagamento.builder()
                .formaPagamento(formas.get(random.nextInt(formas.size())))
                .status(estadoPagamento)
                .pedido(Optional.of(pedido))
                .build();

    }

}
