package br.com.fiap.entities;

import lombok.Getter;

@Getter
public enum EstadoPagamento {
    PAGO(false),
    CANCELADO(true),
    PENDENTE(false),
    DEVOLVIDO(true);

    private final boolean eFinal;

    EstadoPagamento(Boolean eFinal) {
        this.eFinal = eFinal;
    }

}
