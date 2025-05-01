package br.com.fiap.adapters.pedido;

import br.com.fiap.entities.Cliente;
import br.com.fiap.exception.ResultadoNaoEncontrado;

public interface ClienteRequest {

    Cliente buscarClientePorId(String id) throws ResultadoNaoEncontrado;
}
