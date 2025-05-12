package br.com.fiap.rest.util;

import java.time.LocalDateTime;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.StatusType;

public abstract class RespostaBuilder {

    public static Response criarResposta(StatusType status, String mensagem) {
        var resposta = new MesagemResposta(mensagem, LocalDateTime.now(), status.getStatusCode());

        return Response
                .status(status)
                .entity(resposta)
                .build();
    }

}
