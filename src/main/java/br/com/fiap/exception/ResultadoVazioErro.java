package br.com.fiap.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResultadoVazioErro extends RuntimeException {
    private static final Logger log = LoggerFactory.getLogger(ResultadoVazioErro.class);

    public ResultadoVazioErro(String message) {
        super(message);
        log.error("ResultadoVazioErro: {}", message);
    }
}
