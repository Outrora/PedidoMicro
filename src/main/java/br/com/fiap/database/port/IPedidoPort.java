package br.com.fiap.database.port;

import java.util.List;

import br.com.fiap.entities.EstadoPedido;
import br.com.fiap.entities.Pedido;
import br.com.fiap.exception.ResultadoNaoEncontrado;

public interface IPedidoPort {

    String cadastrarPedido(Pedido pedido);

    void editarStatusPedido(String id, EstadoPedido estado);

    List<Pedido> listarPedidos();

    Pedido buscarPorId(String id) throws ResultadoNaoEncontrado;

}
