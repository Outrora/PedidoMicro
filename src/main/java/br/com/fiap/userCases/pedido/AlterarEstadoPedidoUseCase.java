package br.com.fiap.userCases.pedido;

import br.com.fiap.database.port.IPedidoPort;
import br.com.fiap.entities.EstadoPedido;
import br.com.fiap.events.producer.PedidoAlterarEvent;
import br.com.fiap.exception.ErroValidacao;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import lombok.AllArgsConstructor;

@RequestScoped
@AllArgsConstructor(onConstructor = @__(@Inject))
public class AlterarEstadoPedidoUseCase {

    private final IPedidoPort pedidoPort;
    private final PedidoAlterarEvent event;

    public void alterarEstado(String idPedido, EstadoPedido estadoPedido) {

        if (!estadoPedido.isPodeSerAlterado()) {
            throw new ErroValidacao("Não estado do pedido pode ser alterado");
        }
        var pedido = pedidoPort.buscarPorId(idPedido);

        if (!pedido.getEstadoPedido().podeTransitarPara(estadoPedido)) {
            var messagem = String.format("Transição inválida: não é permitido mudar de '%s' para '%s'.",
                    pedido.getEstadoPedido().name(),
                    estadoPedido.name());
            throw new ErroValidacao(messagem);
        }

        pedidoPort.editarStatusPedido(idPedido, estadoPedido);
        event.alterarPedido(idPedido, estadoPedido);
    }
}
