package br.com.fiap.adapters.models;

import java.util.Set;

public record PedidosRequestAdapter(Integer idCliente, Set<ProdutoRequest> produtos) {

    public record ProdutoRequest(Integer id, Integer quantidade) {
    }
}
