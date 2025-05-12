package br.com.fiap.events.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.fiap.adapters.models.PedidosRequestAdapter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PedidoModelEvent extends PedidosRequestAdapter {

    @JsonProperty("id_produto")
    private String idPedido;

    public PedidoModelEvent(PedidosRequestAdapter adapter, String idPedido) {
        this.codigoCliente = adapter.getCodigoCliente();
        this.produtos = adapter.getProdutos();
        this.idPedido = idPedido;
    }

}
