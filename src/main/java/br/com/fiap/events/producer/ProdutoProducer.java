package br.com.fiap.events.producer;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import br.com.fiap.events.model.PedidoModelEvent;
import br.com.fiap.exception.ErroValidacao;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class ProdutoProducer implements PedidoCadastradoEvent {
    private final Logger LOG = Logger.getLogger(this.getClass().getName());

    Emitter<PedidoModelEvent> emiter;

    @Inject
    public ProdutoProducer(@Channel("pedido-cadastro") Emitter<PedidoModelEvent> emiter) {
        this.emiter = emiter;
    }

    @Override
    public void cadastouPedido(PedidoModelEvent adapter) {
        LOG.info("Peido Cadastro" + adapter.toString());
        emiter.send(adapter)
                .thenRun(() -> LOG.info("Pedido Cadastrado"))
                .exceptionally(ex -> {
                    LOG.log(Level.SEVERE, "Erro ao cadastrar pedido" + ex.getMessage());
                    throw new ErroValidacao("Erro");
                });
    }

}
