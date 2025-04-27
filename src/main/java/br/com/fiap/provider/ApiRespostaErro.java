package br.com.fiap.provider;

import jakarta.ws.rs.core.Response;

import java.time.LocalDateTime;

public record ApiRespostaErro(
        String exceptionType,
        int code,
        String error,
        LocalDateTime timestamp
) {
    public static Response criarRespostaErro(
            Exception exception,
            Response.Status status
    ) {
        var reposta = new ApiRespostaErro(exception.getClass().getName(),
                status.getStatusCode(),
                exception.getMessage(),
                LocalDateTime.now()
        );


        return Response
                .status(status.getStatusCode())
                .entity(reposta)
                .build();
    }
}


