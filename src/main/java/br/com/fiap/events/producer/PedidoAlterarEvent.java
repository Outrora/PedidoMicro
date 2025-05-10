package br.com.fiap.events.producer;

import br.com.fiap.entities.EstadoPedido;

public interface PedidoAlterarEvent {
    void alterarPedido(String id, EstadoPedido estadoPedido);
}