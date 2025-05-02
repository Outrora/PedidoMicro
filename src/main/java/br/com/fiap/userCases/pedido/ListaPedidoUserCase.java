package br.com.fiap.userCases.pedido;

import java.util.List;

import br.com.fiap.database.port.IPedidoPort;
import br.com.fiap.entities.Pedido;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import lombok.AllArgsConstructor;

@RequestScoped
@AllArgsConstructor(onConstructor = @__(@Inject))
public class ListaPedidoUserCase {

    private final IPedidoPort port;

    public List<Pedido> listarTodosOsProdutos() {
        return port.listarPedidos();
    }

    public Pedido bucarPorId(String id) {
        return port.buscarPorId(id);
    }

}
