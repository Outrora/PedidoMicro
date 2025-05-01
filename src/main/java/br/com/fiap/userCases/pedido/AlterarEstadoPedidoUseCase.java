package br.com.fiap.userCases.pedido;

import br.com.fiap.database.port.IPedidoPort;
import br.com.fiap.entities.EstadoPedido;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import lombok.AllArgsConstructor;

@RequestScoped
@AllArgsConstructor(onConstructor = @__(@Inject))
public class AlterarEstadoPedidoUseCase {

    private final IPedidoPort pedidoPort;

    public void alterarEstado(String idPedido, EstadoPedido estadoPedido) {
        pedidoPort.editarStatusPedido(idPedido, estadoPedido);
    }
}
