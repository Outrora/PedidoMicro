package br.com.fiap.controller;

import java.util.List;

import br.com.fiap.adapters.models.PedidosRequestAdapter;
import br.com.fiap.entities.EstadoPedido;
import br.com.fiap.entities.Pedido;
import br.com.fiap.userCases.pedido.AlterarEstadoPedidoUseCase;
import br.com.fiap.userCases.pedido.InserirPedidoUseCase;
import br.com.fiap.userCases.pedido.ListaPedidoUserCase;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import lombok.AllArgsConstructor;

@RequestScoped
@AllArgsConstructor(onConstructor = @__(@Inject))
public class PedidoControllerImpl implements PedidoController {

    private final InserirPedidoUseCase inserirPedido;
    private final AlterarEstadoPedidoUseCase alterarEstadoPedido;

    private final ListaPedidoUserCase listaPedido;

    @Override
    public void criarPedido(PedidosRequestAdapter adapter) {
        inserirPedido.inserirPedido(adapter);
    }

    @Override
    public void alteraEstadoPedido(String idPedido, EstadoPedido estadoPedido) {
        alterarEstadoPedido.alterarEstado(idPedido, estadoPedido);
    }

    @Override
    public List<Pedido> listarTodosOsPedidos() {
        return listaPedido.listarTodosOsPedidos();
    }

}
