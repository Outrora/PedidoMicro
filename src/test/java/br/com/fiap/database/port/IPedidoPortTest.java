package br.com.fiap.database.port;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.fiap.database.dto.PedidoDTO;
import br.com.fiap.database.repository.PedidoRepository;
import br.com.fiap.entities.EstadoPedido;
import br.com.fiap.exception.ResultadoNaoEncontrado;
import br.com.fiap.helps.CriarCliente;
import br.com.fiap.helps.CriarPedido;
import br.com.fiap.mapper.PedidoMapper;
import io.quarkus.mongodb.panache.PanacheQuery;

class IPedidoPortTest {

    @Mock
    PedidoRepository repository;

    IPedidoPort pedidoPort;

    AutoCloseable openMocks;

    @BeforeEach
    void init() {
        openMocks = MockitoAnnotations.openMocks(this);
        pedidoPort = repository;
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void deveCadastrarPedidoCorretamente() {
        var pedido = CriarPedido.criarPedido();
        var cliente = Optional.of(CriarCliente.criarCliente());

        doNothing().when(repository).persist(any(PedidoDTO.class));
        doCallRealMethod().when(pedidoPort).cadastrarPedido(any(), any());

        pedidoPort.cadastrarPedido(pedido, cliente);
        var captorPedido = ArgumentCaptor.forClass(PedidoDTO.class);
        verify(repository, times(1)).persist(captorPedido.capture());

        assertThat(captorPedido.getValue())
                .isNotNull();

        assertThat(captorPedido.getValue().getProdutos())
                .isNotEmpty()
                .hasSize(pedido.getProdutos().size());
    }

    @Test
    void deveEditarSatusPedidoCorretamente() {
        var pedido = CriarPedido.criarPedido();
        var dto = PedidoMapper.toDto(pedido, Optional.empty());

        doNothing().when(repository).persistOrUpdate(any(PedidoDTO.class));
        when(repository.findById(any())).thenReturn(dto);
        doCallRealMethod().when(repository).editarStatusPedido(anyString(), any());

        pedidoPort.editarStatusPedido(pedido.pegarID().toHexString(), EstadoPedido.FINALIZADO);

        var captorPedido = ArgumentCaptor.forClass(PedidoDTO.class);

        verify(repository, times(1)).persistOrUpdate(captorPedido.capture());

        assertThat(captorPedido.getValue())
                .isNotNull();

        assertThat(captorPedido.getValue().getEstadoPedido())
                .isEqualTo(EstadoPedido.FINALIZADO);
    }

    @Test
    void deveListarTodosOsProdutos() {
        var cliente = CriarCliente.criarCliente();
        List<PedidoDTO> lista = CriarPedido.criarListaPedido()
                .stream()
                .map(p -> PedidoMapper.toDto(p, Optional.of(cliente)))
                .toList();

        @SuppressWarnings("unchecked")
        PanacheQuery<PedidoDTO> panache = mock(PanacheQuery.class);

        when(repository.listarPedidos()).thenCallRealMethod();
        when(repository.findAll()).thenReturn(panache);
        when(panache.stream()).thenReturn(lista.stream());

        var retorno = pedidoPort.listarPedidos();

        verify(repository, times(1)).findAll();

        assertThat(retorno)
                .isNotEmpty()
                .hasSize(lista.size());
    }

    @Test
    void deveBucarPoIdCorretamente() {
        var pedido = PedidoMapper.toDto(CriarPedido.criarPedido(), Optional.empty());

        when(repository.findByIdOptional(any())).thenReturn(Optional.of(pedido));
        doCallRealMethod().when(pedidoPort).buscarPorId(anyString());

        var resultado = pedidoPort.buscarPorId(pedido.getId().toHexString());

        verify(repository, times(1)).findByIdOptional(any());

        assertThat(resultado)
                .isNotNull();

        assertThat(resultado.getProdutos())
                .isNotEmpty()
                .hasSize(pedido.getProdutos().size());

    }

    @Test
    void deveBucarPoIdERetonarErro() {

        when(repository.findByIdOptional(any())).thenReturn(Optional.empty());
        doCallRealMethod().when(pedidoPort).buscarPorId(anyString());

        assertThatThrownBy(() -> pedidoPort.buscarPorId(new ObjectId().toHexString()))
                .isInstanceOf(ResultadoNaoEncontrado.class)
                .hasMessageContaining("Id do pedido encontrado");

        verify(repository, times(1)).findByIdOptional(any());

    }

}
