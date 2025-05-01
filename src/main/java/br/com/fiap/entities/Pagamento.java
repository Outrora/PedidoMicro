package br.com.fiap.entities;

import java.util.Optional;

public record Pagamento(Optional<String> id, String formaPagamento, EstadoPagamento status, Optional<Pedido> pedido) {

}
