package br.com.fiap.events.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class ProdutoModel {
    private Integer id;
    private String nome;
    private BigDecimal preco;
}
