package br.com.fiap.controller;

import br.com.fiap.entities.EstadoPagamento;

public interface PagamentoController {

    void criarPagamento(String id, String formaPagamento);

    void alteraPagamento(String id, EstadoPagamento estado);
}
