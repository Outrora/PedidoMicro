package br.com.fiap.database.port;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.fiap.database.dto.PedidoDTO;
import br.com.fiap.database.repository.PedidoRepository;
import br.com.fiap.entities.EstadoPagamento;
import br.com.fiap.exception.ErroValidacao;
import br.com.fiap.helps.CriarPagamento;
import br.com.fiap.helps.CriarPedido;
import br.com.fiap.mapper.PagamentoMapper;
import br.com.fiap.mapper.PedidoMapper;

class IPagamentoPortTest {

    @Mock
    PedidoRepository repository;

    IPagamentoPort port;

    AutoCloseable openMocks;

    @BeforeEach
    void init() {
        openMocks = MockitoAnnotations.openMocks(this);
        port = repository;
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void deveVerificarSeEstarCadastroPagamentoCorretamente() {
        var pedido = CriarPedido.criarPedido();
        var dto = PedidoMapper.toDto(pedido);
        var pagamentoDto = PagamentoMapper.tDto(pedido.getPagamento().get());
        dto.setPagamento(pagamentoDto);

        when(repository.findByIdOptional(any())).thenReturn(Optional.of(dto));
        doNothing().when(repository).persistOrUpdate(any(PedidoDTO.class));
        doCallRealMethod().when(port).cadastrarPagamento(pedido.getPagamento().get());

        port.cadastrarPagamento(pedido.getPagamento().get());

        verify(repository, times(1)).findByIdOptional(any());
        verify(repository, times(1)).persistOrUpdate(dto);
    }

    @Test
    void deveVerificarSeEstarCadastroPagamentoRetornaErroValidacao() {
        var pagamento = CriarPagamento.criarPagamento();

        doCallRealMethod().when(port).cadastrarPagamento(any());

        assertThatThrownBy(() -> port.cadastrarPagamento(pagamento))
                .isInstanceOf(ErroValidacao.class)
                .hasMessage("Pedido nao Encontrado");

        verify(repository, times(0)).findByIdOptional(any());
        verify(repository, times(0)).persistOrUpdate(any(PedidoDTO.class));
    }

    @Test
    void deveVerificarSeEstarAlterarPagamentoCorretamente() {
        var pedido = CriarPedido.criarPedido();
        var dto = PedidoMapper.toDto(pedido);
        var pagamentoDto = PagamentoMapper.tDto(pedido.getPagamento().get());
        dto.setPagamento(pagamentoDto);

        when(repository.findByIdOptional(any())).thenReturn(Optional.of(dto));
        doNothing().when(repository).persistOrUpdate(any(PedidoDTO.class));
        doCallRealMethod().when(port).alterarPagamento(anyString(), any());

        port.alterarPagamento(pedido.getId().get(), EstadoPagamento.PAGO);

        verify(repository, times(1)).findByIdOptional(any());
        verify(repository, times(1)).persistOrUpdate(dto);
    }
}
