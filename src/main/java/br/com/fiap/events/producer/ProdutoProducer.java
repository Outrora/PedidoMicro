package br.com.fiap.events.producer;

import java.util.concurrent.CompletionException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import br.com.fiap.entities.EstadoPedido;
import br.com.fiap.events.model.MPedidoAlterarEvent;
import br.com.fiap.events.model.PedidoModelEvent;
import br.com.fiap.exception.ErroValidacao;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class ProdutoProducer implements PedidoCadastradoEvent, PedidoAlterarEvent {
    private final Logger log = Logger.getLogger(this.getClass().getName());

    Emitter<PedidoModelEvent> emiterCastadrado;
    Emitter<MPedidoAlterarEvent> emiterAlterado;

    @Inject
    public ProdutoProducer(
            @Channel("pedido-cadastro") Emitter<PedidoModelEvent> emiterCastadrado,
            @Channel("pedido-alterado-central") Emitter<MPedidoAlterarEvent> emiterAlterado) {
        this.emiterCastadrado = emiterCastadrado;
        this.emiterAlterado = emiterAlterado;
    }

    @Override
    public void cadastouPedido(PedidoModelEvent adapter) {
        log.log(Level.INFO, "Mandando para a fila -  Pedido Cadastro : {}", adapter.getIdPedido());
        try {
            emiterCastadrado.send(adapter)
                    .thenRun(() -> log.info("Enviado para a Fila - pedido cadastrado"))
                    .toCompletableFuture()
                    .join();

        } catch (CompletionException ex) {

            log.log(Level.SEVERE, "Erro ao cadastrar pedido {}", ex.getMessage());
            throw new ErroValidacao("Erro");
        }
        ;
    }

    @Override
    public void alterarPedido(String id, EstadoPedido estadoPedido) {

        var info = "Mandando para a fila -  Pedido Alterado com id: " + id.replaceAll("[\n\r]", "_");
        log.info(info);
        var dadosParaEnviar = new MPedidoAlterarEvent(id, estadoPedido);

        try {
            emiterAlterado.send(dadosParaEnviar)
                    .thenRun(() -> log.info("Enviado para a Fila -  pedido alterado com id:" + id))
                    .toCompletableFuture()
                    .join(); // força execução imediata e propaga exceção
        } catch (CompletionException ex) {
            log.log(Level.SEVERE, "Erro ao alterar pedido: {}", ex.getMessage());
            throw new ErroValidacao("Erro");
        }
    }

}
