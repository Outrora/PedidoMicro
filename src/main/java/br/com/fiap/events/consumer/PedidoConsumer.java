package br.com.fiap.events.consumer;

import java.util.logging.Logger;

import org.eclipse.microprofile.reactive.messaging.Incoming;

import br.com.fiap.database.port.IPedidoPort;
import br.com.fiap.events.model.MPedidoAlterarEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor(onConstructor = @__(@Inject))
public class PedidoConsumer {
    private final Logger LOG = Logger.getLogger(this.getClass().getName());
    private final IPedidoPort port;

    @Incoming("pedido-alterado-produto")
    public void receber(MPedidoAlterarEvent model) {
        LOG.info("Recebeu :" + model.toString());
        port.editarStatusPedido(model.id(), model.estadoPedido());
    }

}
