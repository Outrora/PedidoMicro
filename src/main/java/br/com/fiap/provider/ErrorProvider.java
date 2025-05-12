package br.com.fiap.provider;

import br.com.fiap.exception.ErroValidacao;
import br.com.fiap.exception.ResultadoNaoEncontrado;
import br.com.fiap.exception.ResultadoVazioErro;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.jboss.logging.Logger;
import org.jboss.resteasy.reactive.ResponseStatus;

@Provider
public class ErrorProvider implements ExceptionMapper<Exception> {

    private static final Logger logger = Logger.getLogger(ErrorProvider.class);

    @Override
    @ResponseStatus(500)
    public Response toResponse(Exception exception) {

        logger.error("Erro capturado: ");

        if (exception instanceof ResultadoVazioErro) {
            return ApiRespostaErro.criarRespostaErro(
                    exception,
                    Response.Status.NOT_FOUND);
        }

        if (exception instanceof ErroValidacao) {
            return ApiRespostaErro.criarRespostaErro(
                    exception,
                    Response.Status.BAD_REQUEST);
        }

        if (exception instanceof ResultadoNaoEncontrado) {
            return ApiRespostaErro.criarRespostaErro(
                    exception,
                    Response.Status.NOT_FOUND);
        }

        return ApiRespostaErro.criarRespostaErro(
                exception,
                Response.Status.INTERNAL_SERVER_ERROR);
    }

}
