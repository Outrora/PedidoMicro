package br.com.fiap.adapters.pedido;

import br.com.fiap.entities.Produto;
import br.com.fiap.exception.ResultadoNaoEncontrado;
import br.com.fiap.exception.ResultadoVazioErro;

import java.util.List;
import java.util.Set;

public interface ProdutoRequest {

    Produto bucarProdutoPorId(Integer id) throws ResultadoNaoEncontrado;

    List<Produto> buscarTodosProdutos(Set<Integer> ids) throws ResultadoVazioErro;
}
