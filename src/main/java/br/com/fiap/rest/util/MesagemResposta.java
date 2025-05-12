package br.com.fiap.rest.util;

import java.time.LocalDateTime;

public record MesagemResposta(String mensagem, LocalDateTime hora, Integer code) {
}
