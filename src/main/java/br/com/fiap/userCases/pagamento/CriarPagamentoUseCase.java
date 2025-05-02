package br.com.fiap.userCases.pagamento;

import java.util.Optional;

import br.com.fiap.database.port.IPagamentoPort;
import br.com.fiap.entities.EstadoPagamento;
import br.com.fiap.entities.Pagamento;
import br.com.fiap.userCases.pedido.ListaPedidoUserCase;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import lombok.AllArgsConstructor;

@RequestScoped
@AllArgsConstructor(onConstructor = @__(@Inject))
public class CriarPagamentoUseCase {

    private ListaPedidoUserCase listaPedido;
    private IPagamentoPort pagamentoPort;

    public void criarPagamento(String id, String formaPagamento) {
        var pedido = listaPedido.bucarPorId(id);

        var pagamento = Pagamento.builder()
                .formaPagamento(formaPagamento)
                .pedido(Optional.ofNullable(pedido))
                .status(EstadoPagamento.PENDENTE)
                .build();

        pagamentoPort.cadastrarPagamento(pagamento);
    }
}
