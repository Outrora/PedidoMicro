package br.com.fiap.adapters.mocks;

import br.com.fiap.adapters.pedido.ProdutoRequest;
import br.com.fiap.entities.Produto;
import br.com.fiap.exception.ResultadoNaoEncontrado;
import br.com.fiap.exception.ResultadoVazioErro;
import jakarta.enterprise.context.ApplicationScoped;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

@ApplicationScoped
public class MockProduto implements ProdutoRequest {

    private Random random = new Random();
    private static final String[] PRODUTOS = {
            "Arroz", "Feijão", "Macarrão", "Óleo", "Açúcar",
            "Café", "Leite", "Pão", "Queijo", "Presunto",
            "Biscoito", "Chocolate", "Refrigerante", "Suco", "Iogurte"
    };

    @Override
    public Produto bucarProdutoPorId(Integer id) throws ResultadoNaoEncontrado {
        return criarProduto(id);
    }

    @Override
    public List<Produto> buscarTodosProdutos(Set<Integer> ids) throws ResultadoVazioErro {
        return ids.stream()
                .map(id -> criarProduto(id))
                .toList();
    }

    private Produto criarProduto(Integer id) {
        return new Produto(
                id,
                PRODUTOS[random.nextInt(PRODUTOS.length)],
                Optional.empty(),
                BigDecimal.valueOf(random.nextDouble() * 100));
    }
}
