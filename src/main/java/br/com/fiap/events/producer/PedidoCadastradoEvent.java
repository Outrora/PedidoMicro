package br.com.fiap.events.producer;

import br.com.fiap.events.model.PedidoModelEvent;

public interface PedidoCadastradoEvent {
    void cadastouPedido(PedidoModelEvent adapter);
}
