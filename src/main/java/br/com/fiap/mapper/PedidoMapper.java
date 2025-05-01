package br.com.fiap.mapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import br.com.fiap.database.dto.PedidoDTO;
import br.com.fiap.database.dto.ProdutoDTO;
import br.com.fiap.entities.Cliente;
import br.com.fiap.entities.Pedido;
import br.com.fiap.entities.Produto;

public abstract class PedidoMapper {

    public static PedidoDTO toDto(Pedido pedido, Optional<Cliente> cliente) {
        var listaProduto = pedido.getProdutos()
                .stream()
                .map(produto -> new ProdutoDTO(
                        produto.getId(),
                        produto.getNome(),
                        produto.getQuantidade().orElse(0),
                        produto.getPreco()))
                .toList();

        return PedidoDTO.builder()
                .cliente(cliente.orElse(null))
                .dataInclucao(LocalDateTime.now())
                .estadoPedido(pedido.getEstadoPedido())
                .produtos(listaProduto)
                .build();
    }

    public static Pedido toEntity(PedidoDTO dto) {

        List<Produto> listaProduto = List.of();
        var idCliente = 0;

        if (dto.getProdutos() != null) {
            listaProduto = dto.getProdutos()
                    .stream()
                    .map(produto -> new Produto(produto.id(), produto.nome(), Optional.of(produto.quantidade()),
                            produto.preco()))
                    .toList();
        }

        if (dto.getCliente() != null) {
            idCliente = dto.getCliente().codigo();
        }

        return new Pedido(dto.getId().toHexString(),
                idCliente,
                listaProduto,
                dto.getEstadoPedido());
    }
}
