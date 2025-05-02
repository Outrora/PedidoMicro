package br.com.fiap.database.port;

import java.util.List;
import java.util.Optional;

import br.com.fiap.entities.Cliente;
import br.com.fiap.entities.EstadoPedido;
import br.com.fiap.entities.Pedido;

public interface IPedidoPort {

    void cadastrarPedido(Pedido pedido, Optional<Cliente> cliente);

    void editarStatusPedido(String id, EstadoPedido estado);

    List<Pedido> listarPedidos();

    Pedido buscarPorId(String id);

}
