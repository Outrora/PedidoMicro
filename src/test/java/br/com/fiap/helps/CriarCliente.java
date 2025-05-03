package br.com.fiap.helps;

import java.util.Random;

import br.com.fiap.entities.Cliente;

public abstract class CriarCliente {

    public static Cliente criarCliente() {
        String[] nomes = { "João", "Maria", "Pedro", "Ana", "Carlos", "Paula", "Lucas", "Julia", "Marcos", "Sofia" };
        String[] sobrenomes = { "Silva", "Santos", "Oliveira", "Souza", "Ferreira", "Pereira", "Lima", "Costa",
                "Rodrigues", "Almeida" };
        Random random = new Random();

        String nome = nomes[random.nextInt(nomes.length)] + " " + sobrenomes[random.nextInt(sobrenomes.length)];

        return new Cliente(random.nextInt(20), nome);
    }

}
