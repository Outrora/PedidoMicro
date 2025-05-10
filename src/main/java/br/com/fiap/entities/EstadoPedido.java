package br.com.fiap.entities;

import java.util.List;

import lombok.Getter;

@Getter
public enum EstadoPedido {
    FINALIZADO(true, List.of()),
    CANCELADO(true, List.of()),
    EM_PREPARACAO(false, List.of(CANCELADO, FINALIZADO)),
    PAGAMENTO_APROVADO(true, List.of(EM_PREPARACAO, CANCELADO)),
    PAGAMENTO_RECUSADO(true, List.of(PAGAMENTO_APROVADO, CANCELADO)),
    PEDIDO_CADASTRADO(false, List.of(PAGAMENTO_APROVADO, PAGAMENTO_RECUSADO));

    private final boolean podeSerAlterado;
    private final List<EstadoPedido> proximosEstados;

    EstadoPedido(Boolean podeSerAlterado, List<EstadoPedido> proximosEstados) {
        this.podeSerAlterado = podeSerAlterado;
        this.proximosEstados = proximosEstados;
    }

    public boolean podeTransitarPara(EstadoPedido novoEstado) {
        return proximosEstados.contains(novoEstado);
    }
}
