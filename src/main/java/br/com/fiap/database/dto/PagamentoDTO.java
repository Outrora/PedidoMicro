package br.com.fiap.database.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.mongodb.lang.Nullable;

import br.com.fiap.entities.EstadoPagamento;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter // Usado no Panche para setar dados
@Builder
public class PagamentoDTO {
    private String formaPagamento;

    private EstadoPagamento status;

    @Nullable
    private List<LocalDateTime> dadasAlteracoes;
}
