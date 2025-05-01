package br.com.fiap.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Pedido {

    private Optional<String> id;
    private Integer idCliente;
    private List<Produto> produtos;
    private EstadoPedido estadoPedido;
    private BigDecimal valorTotal;

    public Pedido(Integer idCliente, List<Produto> produtos) {
        this.id = Optional.empty();
        this.idCliente = idCliente;
        this.produtos = produtos;
        this.estadoPedido = EstadoPedido.PEDIDO_CADASTRADO;
        this.valorTotal = calcularValorTotal();
    }

    public Pedido(String id, Integer idCliente, List<Produto> produtos, EstadoPedido estadoPedido) {
        this.id = Optional.of(id);
        this.idCliente = idCliente;
        this.produtos = produtos;
        this.estadoPedido = estadoPedido;
        this.valorTotal = calcularValorTotal();
    }

    private BigDecimal calcularValorTotal() {
        return produtos
                .stream()
                .map(p -> p.getPreco().multiply(BigDecimal.valueOf(p.getQuantidade().orElse(0))))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public ObjectId pegarID() {
        return new ObjectId(id.orElse("0"));
    }

}
