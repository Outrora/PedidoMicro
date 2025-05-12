package br.com.fiap.mapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import br.com.fiap.database.dto.PagamentoDTO;
import br.com.fiap.entities.Pagamento;
import br.com.fiap.entities.Pedido;

public abstract class PagamentoMapper {

    public static PagamentoDTO tDto(Pagamento pagamento) {
        return PagamentoDTO.builder()
                .formaPagamento(pagamento.formaPagamento())
                .status(pagamento.status())
                .dadasAlteracoes(List.of(LocalDateTime.now()))
                .build();
    }

    public static Pagamento toEntity(PagamentoDTO dto, Pedido pedido) {

        return Pagamento.builder()
                .pedido(Optional.ofNullable(pedido))
                .formaPagamento(dto.getFormaPagamento())
                .status(dto.getStatus())
                .build();
    }
}
