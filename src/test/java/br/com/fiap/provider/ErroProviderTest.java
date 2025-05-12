package br.com.fiap.provider;

import br.com.fiap.exception.ErroValidacao;
import br.com.fiap.exception.ResultadoNaoEncontrado;
import br.com.fiap.exception.ResultadoVazioErro;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ErroProviderTest {

        private final ErrorProvider errorProvider = new ErrorProvider();

        @Test
        public void DeveRetornarErro404() {

                var erro = new ResultadoVazioErro("Nao foi encontrado");
                var resultado = errorProvider.toResponse(erro);

                assertThat(resultado)
                                .isNotNull()
                                .isInstanceOfAny(Response.class);

                assertThat(resultado.getStatus())
                                .isEqualTo(404);

                assertThat(resultado.getEntity())
                                .isInstanceOfAny(ApiRespostaErro.class);

        }

        @Test
        public void DeveRetornarErro500() {

                var erro = new RuntimeException("Nao foi encontrado");
                var resultado = errorProvider.toResponse(erro);

                assertThat(resultado)
                                .isNotNull()
                                .isInstanceOfAny(Response.class);

                assertThat(resultado.getStatus())
                                .isEqualTo(500);

                assertThat(resultado.getEntity())
                                .isInstanceOfAny(ApiRespostaErro.class);

        }

        @Test
        public void DeveRetornarErro400() {

                var erro = new ErroValidacao("Nao foi encontrado");
                var resultado = errorProvider.toResponse(erro);

                assertThat(resultado)
                                .isNotNull()
                                .isInstanceOfAny(Response.class);

                assertThat(resultado.getStatus())
                                .isEqualTo(400);

                assertThat(resultado.getEntity())
                                .isInstanceOfAny(ApiRespostaErro.class);

        }

        @Test
        public void DeveRetornarErro400NaoEncontrado() {

                var erro = new ResultadoNaoEncontrado("Nao foi encontrado");
                var resultado = errorProvider.toResponse(erro);

                assertThat(resultado)
                                .isNotNull()
                                .isInstanceOfAny(Response.class);

                assertThat(resultado.getStatus())
                                .isEqualTo(404);

                assertThat(resultado.getEntity())
                                .isInstanceOfAny(ApiRespostaErro.class);

        }
}
