package br.com.fiap.events.persistence;

import java.util.List;
import java.util.Set;

import br.com.fiap.entities.Produto;
import br.com.fiap.exception.ResultadoNaoEncontrado;
import br.com.fiap.exception.ResultadoVazioErro;

public interface IProdutoEventPort {

    Produto bucarProdutoPorId(Integer id) throws ResultadoNaoEncontrado;

    List<Produto> buscarTodosProdutos(Set<Integer> ids) throws ResultadoVazioErro;

    void inserirOuEditarProduto(Produto produto);
}
