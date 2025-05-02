package br.com.fiap.entities;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Builder;

@Builder
public record Pagamento(
                String formaPagamento,
                EstadoPagamento status,
                @JsonIgnore Optional<Pedido> pedido) {

}
