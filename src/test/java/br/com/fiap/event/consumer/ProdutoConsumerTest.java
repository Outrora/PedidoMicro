package br.com.fiap.event.consumer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.fiap.events.consumer.ProdutoCadastrado;
import br.com.fiap.events.model.ProdutoModel;
import br.com.fiap.events.persistence.IProdutoEventPort;

class ProdutoConsumerTest {

    @Mock
    private IProdutoEventPort port;
    private ProdutoCadastrado consumer;

    AutoCloseable openMocks;

    @BeforeEach
    void init() {
        openMocks = MockitoAnnotations.openMocks(this);
        consumer = new ProdutoCadastrado(port);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void deveReceberCorretamente() {
        var event = new ProdutoModel(1, "test", BigDecimal.valueOf(12));

        doNothing().when(port).inserirOuEditarProduto(any());

        consumer.receber(event);
        verify(port, times(1)).inserirOuEditarProduto(any());
    }

}
