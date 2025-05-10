package br.com.fiap.events.consumer;

import java.util.logging.Logger;

import org.eclipse.microprofile.reactive.messaging.Incoming;

import br.com.fiap.events.mapper.ProdutoMapper;
import br.com.fiap.events.model.ProdutoModel;
import br.com.fiap.events.persistence.IProdutoEventPort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor(onConstructor = @__(@Inject))
public class ProdutoCadastrado {
    private final Logger LOG = Logger.getLogger(this.getClass().getName());
    private final IProdutoEventPort port;

    @Incoming("produto-cadastrado")
    public void receber(ProdutoModel model) {
        LOG.info("Recebeu :" + model.toString());
        port.inserirOuEditarProduto(ProdutoMapper.toEntity(model));
    }
}
