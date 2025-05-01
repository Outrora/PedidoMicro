package br.com.fiap.database.repository;

import br.com.fiap.database.dto.PedidoDTO;
import br.com.fiap.database.port.IPedidoPort;
import br.com.fiap.entities.Cliente;
import br.com.fiap.entities.EstadoPedido;
import br.com.fiap.entities.Pedido;
import br.com.fiap.mapper.PedidoMapper;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;

@ApplicationScoped
public class PedidoRepository implements PanacheMongoRepository<PedidoDTO>, IPedidoPort {

    @Override
    public void cadastrarPedido(Pedido pedido, Optional<Cliente> cliente) {
        persist(PedidoMapper.toDto(pedido, cliente));
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

}
