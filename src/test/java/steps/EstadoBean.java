package steps;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import br.com.fiap.adapters.models.PedidosRequestAdapter;
import br.com.fiap.adapters.models.PedidosRequestAdapter.ProdutoRequest;
import br.com.fiap.entities.Produto;
import br.com.fiap.events.model.PedidoModelEvent;
import br.com.fiap.helps.CriarPedido;
import io.quarkiverse.cucumber.ScenarioScope;
import io.smallrye.reactive.messaging.memory.InMemorySink;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ScenarioScope
@Getter
@Setter
@NoArgsConstructor
public class EstadoBean {

    List<Produto> produto;
    InMemorySink<PedidoModelEvent> pedidoCadastroEvento;
    PedidosRequestAdapter request;

    public void requisicao() {
        Set<ProdutoRequest> listaProduto = produto.stream()
                .map(x -> new PedidosRequestAdapter.ProdutoRequest(x.getId(), 2))
                .collect(Collectors.toSet());

        request = CriarPedido.criarRequest(listaProduto);
    }
}
