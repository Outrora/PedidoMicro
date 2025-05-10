package br.com.fiap.events.producer;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Message;

import br.com.fiap.adapters.models.PedidosRequestAdapter;
import br.com.fiap.exception.ErroValidacao;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor(onConstructor = @__(@Inject))
public class ProdutoProducer {
    private final Logger LOG = Logger.getLogger(this.getClass().getName());

    @Channel("pedido-cadastro")
    Emitter<PedidosRequestAdapter> emiter;

    public void cadastrouPedido(PedidosRequestAdapter adapter) {
        emiter.send(adapter)
                .thenRun(() -> LOG.info("Pedido Cadastrado"))
                .exceptionally(ex -> {
                    LOG.log(Level.SEVERE, "Erro ao cadastrar pedido" + ex.getMessage());
                    throw new ErroValidacao("Erro");
                });

    }

}
