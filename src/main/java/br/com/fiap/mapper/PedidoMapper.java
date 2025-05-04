package br.com.fiap.mapper;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;

import br.com.fiap.database.dto.PedidoDTO;
import br.com.fiap.database.dto.ProdutoDTO;
import br.com.fiap.entities.Pedido;
import br.com.fiap.entities.Produto;

public abstract class PedidoMapper {

        public static PedidoDTO toDto(Pedido pedido) {

                var id = pedido.getId()
                                .map(ObjectId::new)
                                .orElse(null);

                var listaProduto = pedido.getProdutos()
                                .stream()
                                .map(produto -> new ProdutoDTO(
                                                produto.getId(),
                                                produto.getNome(),
                                                produto.getQuantidade().orElse(0),
                                                produto.getPreco()))
                                .toList();

                return PedidoDTO.builder()
                                .id(id)
                                .idCliente(pedido.getIdCliente())
                                .dataInclucao(LocalDateTime.now())
                                .estadoPedido(pedido.getEstadoPedido())
                                .produtos(listaProduto)
                                .build();
        }

        public static Pedido toEntity(PedidoDTO dto) {

                List<Produto> listaProduto = Optional.ofNullable(dto.getProdutos())
                                .orElse(Collections.emptyList())
                                .stream()
                                .map(produto -> new Produto(produto.id(), produto.nome(),
                                                Optional.ofNullable(produto.quantidade()),
                                                produto.preco()))
                                .toList();

                var retorno = new Pedido(dto.getId().toHexString(), dto.getIdCliente(), listaProduto,
                                dto.getEstadoPedido());

                if (dto.getPagamento() != null) {
                        var pagamento = PagamentoMapper.toEntity(dto.getPagamento(), retorno);
                        retorno.alteraPagamento(pagamento);
                }

                return retorno;

        }
}
