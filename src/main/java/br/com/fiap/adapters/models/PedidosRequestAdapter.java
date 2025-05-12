package br.com.fiap.adapters.models;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PedidosRequestAdapter {

    protected Integer codigoCliente;

    @JsonProperty("lista_produtos")
    protected Set<ProdutoRequest> produtos;

    public record ProdutoRequest(Integer id, Integer quantidade) {
    }
}
