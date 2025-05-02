package br.com.fiap.rest.pedido;

import java.util.List;
import java.util.logging.Logger;

import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import br.com.fiap.adapters.models.PedidosRequestAdapter;
import br.com.fiap.controller.PedidoController;
import br.com.fiap.entities.EstadoPedido;
import br.com.fiap.entities.Pedido;
import br.com.fiap.rest.util.RespostaBuilder;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
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
@Tag(name = "pedido", description = "Endpoints do pedido")
public class PedidoRest {

    private final Logger LOG = Logger.getLogger(this.getClass().getName());

    private final PedidoController controller;

    @POST
    public Response inserirPedido(PedidosRequestAdapter request) {
        LOG.info("Recebendo Request de Inserir Pedido");
        controller.criarPedido(request);
        return RespostaBuilder.criarResposta(Status.CREATED, "Pedido Criado Com Sucesso");
    }

    @GET
    @Path("todos")
    public List<Pedido> listarTodos() {
        return controller.listarTodosOsProdutos();
    }

    @PUT
    @Path("alterarEstado/{id}")
    public Response alteraEstadoPedido(@PathParam("id") String id, EstadoPedido estadoPedido) {
        controller.alteraEstadoPedido(id, estadoPedido);
        return RespostaBuilder.criarResposta(Status.ACCEPTED, "Pedido Editado com Sucesso");
    }

}
