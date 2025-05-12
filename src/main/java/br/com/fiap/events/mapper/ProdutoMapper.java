package br.com.fiap.events.mapper;

import java.util.Optional;

import br.com.fiap.entities.Produto;
import br.com.fiap.events.model.ProdutoModel;
import br.com.fiap.events.persistence.ProdutoEventDTO;

public abstract class ProdutoMapper {

    public static Produto toEntity(ProdutoEventDTO dto) {
        return new Produto(dto.getId(), dto.getNome(), Optional.empty(), dto.getPreco());
    }

    public static ProdutoEventDTO toDTOEvent(Produto produto) {
        return ProdutoEventDTO.builder()
                .id(produto.getId())
                .preco(produto.getPreco())
                .nome(produto.getNome())
                .build();
    }

    public static Produto toEntity(ProdutoModel model) {
        return new Produto(model.getId(), model.getNome(), Optional.empty(), model.getPreco());
    }

}
