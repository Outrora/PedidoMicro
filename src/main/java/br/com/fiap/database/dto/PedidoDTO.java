package br.com.fiap.database.dto;

import br.com.fiap.entities.Cliente;
import br.com.fiap.entities.EstadoPedido;
import br.com.fiap.entities.Pagamento;
import io.quarkus.mongodb.panache.common.MongoEntity;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@MongoEntity(collection = "pedidos")
public class PedidoDTO {

    @BsonId
    private ObjectId id;

    @BsonProperty(value = "data_inclusao")
    private LocalDateTime dataInclucao;

    @Nullable
    @BsonProperty("cliente")
    private Cliente cliente;

    @BsonProperty(value = "estado_pedido")
    private EstadoPedido estadoPedido;

    @BsonProperty("valor_total")
    private BigDecimal valorTotal;

    @Nullable
    @BsonProperty("produtos")
    private List<ProdutoDTO> produtos;

    @Nullable
    @BsonProperty("pagamento")
    private Pagamento pagamento;

}
