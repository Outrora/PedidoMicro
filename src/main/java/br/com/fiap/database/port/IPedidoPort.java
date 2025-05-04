package br.com.fiap.database.port;

import java.util.List;

import br.com.fiap.entities.EstadoPedido;
import br.com.fiap.entities.Pedido;

public interface IPedidoPort {

    void cadastrarPedido(Pedido pedido);

    void editarStatusPedido(String id, EstadoPedido estado);

    List<Pedido> listarPedidos();

    Pedido buscarPorId(String id);

}
