package br.com.fiap.controller;

import java.util.List;

import br.com.fiap.adapters.models.PedidosRequestAdapter;
import br.com.fiap.entities.EstadoPedido;
import br.com.fiap.entities.Pedido;

public interface PedidoController {

    void criarPedido(PedidosRequestAdapter adapter);

    void alteraEstadoPedido(String idPedido, EstadoPedido estadoPedido);

    List<Pedido> listarTodosOsPedidos();
}
