package br.com.fiap.controller;

import br.com.fiap.entities.EstadoPagamento;
import br.com.fiap.userCases.pagamento.AlterarEstadoPagamentoUseCase;
import br.com.fiap.userCases.pagamento.CriarPagamentoUseCase;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import lombok.AllArgsConstructor;

@RequestScoped
@AllArgsConstructor(onConstructor = @__(@Inject))
public class PagamentoControllerImpl implements PagamentoController {

    private final CriarPagamentoUseCase criarPagamento;
    private final AlterarEstadoPagamentoUseCase alterarPagamento;

    @Override
    public void criarPagamento(String id, String formaPagamento) {
        criarPagamento.criarPagamento(id, formaPagamento);
    }

    @Override
    public void alteraPagamento(String id, EstadoPagamento estado) {
        alterarPagamento.alterarPagamento(id, estado);
    }

}
