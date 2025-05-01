package br.com.fiap.adapters.pagamento;

import br.com.fiap.entities.Pagamento;
import br.com.fiap.entities.Pedido;

public interface IGerarPagamento {

    Pagamento gerarPagamento(Pedido pedido) throws Exception;
}
