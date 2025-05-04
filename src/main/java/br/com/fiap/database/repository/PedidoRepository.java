package br.com.fiap.database.repository;

import br.com.fiap.database.dto.PedidoDTO;
import br.com.fiap.database.port.IPagamentoPort;
import br.com.fiap.database.port.IPedidoPort;
import br.com.fiap.entities.EstadoPagamento;
import br.com.fiap.entities.EstadoPedido;
import br.com.fiap.entities.Pagamento;
import br.com.fiap.entities.Pedido;
import br.com.fiap.exception.ErroValidacao;
import br.com.fiap.exception.ResultadoNaoEncontrado;
import br.com.fiap.mapper.PagamentoMapper;
import br.com.fiap.mapper.PedidoMapper;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

import org.bson.types.ObjectId;

@ApplicationScoped
public class PedidoRepository implements PanacheMongoRepository<PedidoDTO>, IPedidoPort, IPagamentoPort {

    @Override
    public void cadastrarPedido(Pedido pedido) {
        persist(PedidoMapper.toDto(pedido));
    }

    @Override
    public void editarStatusPedido(String id, EstadoPedido estado) {
        var pedido = findById(new ObjectId(id));
        pedido.setEstadoPedido(estado);
        persistOrUpdate(pedido);
    }

    @Override
    public List<Pedido> listarPedidos() {
        var lista = findAll();

        return lista.stream()
                .map(dto -> PedidoMapper.toEntity(dto))
                .toList();

    }

    @Override
    public void cadastrarPagamento(Pagamento pagamento) {

        var id = pagamento.pedido()
                .flatMap(Pedido::getId)
                .orElseThrow(() -> new ErroValidacao("Pedido nao Encontrado"));
        var pedido = buscarPeloIdInterno(id);
        pedido.setPagamento(PagamentoMapper.tDto(pagamento));
        persistOrUpdate(pedido);
    }

    @Override
    public void alterarPagamento(String id, EstadoPagamento estadoPagamento) {
        var pedido = buscarPeloIdInterno(id);
        pedido.getPagamento().setStatus(estadoPagamento);
        persistOrUpdate(pedido);

    }

    @Override
    public Pedido buscarPorId(String id) {
        return PedidoMapper.toEntity(buscarPeloIdInterno(id));
    }

    private PedidoDTO buscarPeloIdInterno(String id) {
        return findByIdOptional(new ObjectId(id))
                .orElseThrow(() -> new ResultadoNaoEncontrado("Id do pedido encontrado"));
    }

}
