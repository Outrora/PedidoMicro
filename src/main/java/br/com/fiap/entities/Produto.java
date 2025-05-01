package br.com.fiap.entities;

import java.math.BigDecimal;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Produto {

    private Integer id;
    private String nome;
    private Optional<Integer> quantidade;
    private BigDecimal preco;

    public void alterarQuantidade(Integer quant) {
        this.quantidade = Optional.of(quant);
    }

}
