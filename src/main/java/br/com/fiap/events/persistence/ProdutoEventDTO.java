package br.com.fiap.events.persistence;

import java.math.BigDecimal;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@MongoEntity(collection = "produto_eventos")
public class ProdutoEventDTO {

    @BsonId
    private ObjectId idDocumeto;

    private Integer id;

    String nome;

    BigDecimal preco;

}
