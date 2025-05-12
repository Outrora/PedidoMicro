package br.com.fiap.entities;

import lombok.Getter;

@Getter
public enum EstadoPagamento {
    PAGO(false, EstadoPedido.PAGAMENTO_APROVADO),
    CANCELADO(true, EstadoPedido.CANCELADO),
    PENDENTE(false, EstadoPedido.PEDIDO_CADASTRADO),
    DEVOLVIDO(true, EstadoPedido.CANCELADO),
    PAGAMENTO_RECUSADO(false, EstadoPedido.PAGAMENTO_RECUSADO);

    private final boolean eFinal;
    private final EstadoPedido estadoPedido;

    EstadoPagamento(Boolean eFinal, EstadoPedido pedido) {
        this.eFinal = eFinal;
        this.estadoPedido = pedido;
    }

}
