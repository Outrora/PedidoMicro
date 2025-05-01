package br.com.fiap.adapters.mocks;

import br.com.fiap.adapters.pedido.ClienteRequest;
import br.com.fiap.entities.Cliente;
import br.com.fiap.exception.ResultadoNaoEncontrado;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Random;

@ApplicationScoped
public class MockCliente implements ClienteRequest {

    private static final String[] NOMES = {
            "João", "Maria", "Pedro", "Ana", "Carlos",
            "Paula", "Lucas", "Julia", "Marcos", "Sofia"
    };

    private static final String[] SOBRENOMES = {
            "Silva", "Santos", "Oliveira", "Souza", "Ferreira",
            "Pereira", "Lima", "Costa", "Rodrigues", "Almeida"
    };

    @Override
    public Cliente buscarClientePorId(String id) throws ResultadoNaoEncontrado {
        if (id == null || id.trim().isEmpty()) {
            throw new ResultadoNaoEncontrado("ID do cliente não pode ser vazio");
        }
        return gerarCliente();
    }

    private Cliente gerarCliente() {
        Random random = new Random();
        String nome = NOMES[random.nextInt(NOMES.length)] + " " +
                SOBRENOMES[random.nextInt(SOBRENOMES.length)];
        return new Cliente(random.nextInt(100000000), nome);
    }

}
