package br.com.fiap.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ErroValidacao extends RuntimeException {
    private static final Logger log = LoggerFactory.getLogger(ErroValidacao.class);

    public ErroValidacao(String erro) {
        super(erro);
        log.error("Erro de Validacao:{}", erro);
    }
}
