package br.com.fiap.events.model;

import br.com.fiap.entities.EstadoPedido;

public record MPedidoAlterarEvent(String id, EstadoPedido estadoPedido) {
}
