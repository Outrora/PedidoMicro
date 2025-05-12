package br.com.fiap.database.dto;

import java.math.BigDecimal;

public record ProdutoDTO(Integer id, String nome, Integer quantidade, BigDecimal preco) {

}
