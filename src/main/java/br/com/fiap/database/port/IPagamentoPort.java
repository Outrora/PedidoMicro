package br.com.fiap.database.port;

import br.com.fiap.entities.EstadoPagamento;
import br.com.fiap.entities.Pagamento;

public interface IPagamentoPort {

    void cadastrarPagamento(Pagamento pagamento);

    void alterarPagamento(String id, EstadoPagamento estadoPagamento);
}
