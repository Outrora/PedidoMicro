package br.com.fiap.userCases.pagamento;

import br.com.fiap.database.port.IPagamentoPort;
import br.com.fiap.entities.EstadoPagamento;
import br.com.fiap.entities.Pagamento;
import br.com.fiap.exception.ErroValidacao;
import br.com.fiap.userCases.pedido.AlterarEstadoPedidoUseCase;
import br.com.fiap.userCases.pedido.ListaPedidoUserCase;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import lombok.AllArgsConstructor;

@RequestScoped
@AllArgsConstructor(onConstructor = @__(@Inject))
public class AlterarEstadoPagamentoUseCase {

    private final ListaPedidoUserCase listaPedido;
    private final IPagamentoPort pagamentoPort;
    private final AlterarEstadoPedidoUseCase estadoPedido;

    public void alterarPagamento(String id, EstadoPagamento estadoPagamento) {
        Pagamento pagamento = listaPedido.bucarPorId(id)
                .pegarPagamento()
                .orElseThrow(() -> new ErroValidacao("Pagamento não encontrado"));

        if (pagamento.status().isEFinal()) {
            throw new ErroValidacao("Pagamento não pode ser alterado pois já está em estado final");
        }

        if (pagamento.status().equals(estadoPagamento)) {
            throw new ErroValidacao("Pagamento já está no estado final informado");
        }

        estadoPedido.alterarEstado(id, estadoPagamento.getEstadoPedido());
        pagamentoPort.alterarPagamento(id, estadoPagamento);

    }
}
