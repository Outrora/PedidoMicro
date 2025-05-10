package br.com.fiap.events.persistence;

import java.util.List;
import java.util.Set;

import br.com.fiap.entities.Produto;
import br.com.fiap.events.mapper.ProdutoMapper;
import br.com.fiap.exception.ResultadoNaoEncontrado;
import br.com.fiap.exception.ResultadoVazioErro;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ProdutoRepository implements PanacheMongoRepository<ProdutoEventDTO>, IProdutoEventPort {

    @Override
    public Produto bucarProdutoPorId(Integer id) throws ResultadoNaoEncontrado {
        return find("id", id)
                .firstResultOptional()
                .map(ProdutoMapper::toEntity)
                .orElseThrow(() -> new ResultadoNaoEncontrado("Produto não encontrado com ID: " + id));
    }

    @Override
    public List<Produto> buscarTodosProdutos(Set<Integer> ids) throws ResultadoVazioErro {
        return list("id in ?1", ids)
                .stream()
                .map(ProdutoMapper::toEntity)
                .toList();
    }

    @Override
    public void inserirOuEditarProduto(Produto produto) {

        var persistencia = find("id", produto.getId())
                .firstResultOptional()
                .orElse(ProdutoMapper.toDTOEvent(produto));

        persistOrUpdate(persistencia);

    }

}
