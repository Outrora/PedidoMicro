package br.com.fiap.rest.pagamento;

import java.util.logging.Logger;

import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import br.com.fiap.adapters.models.PedidosRequestAdapter;
import br.com.fiap.controller.PagamentoController;
import br.com.fiap.entities.EstadoPagamento;
import br.com.fiap.rest.util.RespostaBuilder;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import lombok.AllArgsConstructor;

@Path("pedido")
@RequestScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@AllArgsConstructor(onConstructor = @__(@Inject))
@Tag(name = "pagamento", description = "Endpoints do pagamento")
public class PagamentoRest {
    private final Logger LOG = Logger.getLogger(this.getClass().getName());
    private PagamentoController controller;

    @POST
    @Path("{id}")
    public Response inserirPagamento(@PathParam("id") String id, String formaPagamento) {
        LOG.info("Recebendo Request de Inserir Pagamento");
        controller.criarPagamento(id, formaPagamento);
        return RespostaBuilder.criarResposta(Status.CREATED, "Pagamento Criado Com Sucesso");
    }

    @PUT
    @Path("{id}")
    public Response alterarPagamento(@PathParam("id") String id, EstadoPagamento estado) {
        LOG.info("Recebendo Request de Altera Pagamento");
        controller.alteraPagamento(id, estado);
        return RespostaBuilder.criarResposta(Status.ACCEPTED, "Pagamento alterado com sucesso");
    }

}
