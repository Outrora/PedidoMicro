package br.com.fiap.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResultadoNaoEncontrado extends RuntimeException {
    private static final Logger log = LoggerFactory.getLogger(ResultadoNaoEncontrado.class);

    public ResultadoNaoEncontrado(String message) {
        super(message);
        log.error("Resultado nao Encontrado: {}", message);
    }
}
