package br.com.fiap.event.database;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.fiap.entities.Produto;
import br.com.fiap.events.mapper.ProdutoMapper;
import br.com.fiap.events.persistence.ProdutoEventDTO;
import br.com.fiap.events.persistence.ProdutoRepository;
import br.com.fiap.exception.ResultadoNaoEncontrado;
import br.com.fiap.helps.CriarPedido;
import io.quarkus.mongodb.panache.PanacheQuery;
import lombok.var;

class ProdutoRepositoryTest {

    @Mock
    ProdutoRepository repository;

    AutoCloseable openMocks;

    @BeforeEach
    void init() {
        new ProdutoRepository();
        openMocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void deverBuscarPorIdCorretamente() {

        var produto = CriarPedido.gerarProduto();
        var dto = ProdutoMapper.toDTOEvent(produto);

        when(repository.bucarProdutoPorId(anyInt())).thenCallRealMethod();
        @SuppressWarnings("unchecked")
        PanacheQuery<ProdutoEventDTO> queryMock = mock(PanacheQuery.class);
        when(queryMock.firstResultOptional()).thenReturn(Optional.of(dto));
        when(repository.find(anyString(), anyInt())).thenReturn(queryMock);

        var retorno = repository.bucarProdutoPorId(1);

        verify(repository, times(1)).find(anyString(), anyInt());

        assertThat(retorno)
                .isNotNull()
                .isInstanceOf(Produto.class);

    }

    @Test
    void deverBuscarPorIdENaoEncontrar() {

        when(repository.bucarProdutoPorId(anyInt())).thenCallRealMethod();
        @SuppressWarnings("unchecked")
        PanacheQuery<ProdutoEventDTO> queryMock = mock(PanacheQuery.class);
        when(queryMock.firstResultOptional()).thenReturn(Optional.empty());
        when(repository.find(anyString(), anyInt())).thenReturn(queryMock);

        assertThatThrownBy(() -> repository.bucarProdutoPorId(1))
                .hasMessageContaining("Produto não encontrado com ID:")
                .isInstanceOf(ResultadoNaoEncontrado.class);

        verify(repository, times(1)).find(anyString(), anyInt());

    }

    @Test
    void deverBuscarPorTodososIdCorretamente() {

        var lista = CriarPedido.geraProdutos()
                .stream()
                .map(ProdutoMapper::toDTOEvent)
                .toList();

        var listIds = lista.stream().map(x -> x.getId()).collect(Collectors.toSet());

        when(repository.buscarTodosProdutos(anySet())).thenCallRealMethod();
        when(repository.list(anyString(), anySet())).thenReturn(lista);

        var retorno = repository.buscarTodosProdutos(listIds);
        assertThat(retorno)
                .isNotEmpty()
                .hasSize(lista.size());

    }

    @Test
    void deveInseirCorretamente() {
        var produto = CriarPedido.gerarProduto();

        @SuppressWarnings("unchecked")
        PanacheQuery<ProdutoEventDTO> queryMock = mock(PanacheQuery.class);
        when(queryMock.firstResultOptional()).thenReturn(Optional.empty());
        when(repository.find(anyString(), anyInt())).thenReturn(queryMock);
        doNothing().when(repository).persistOrUpdate(any(ProdutoEventDTO.class));
        doCallRealMethod().when(repository).inserirOuEditarProduto(any(Produto.class));

        repository.inserirOuEditarProduto(produto);

        verify(repository, times(1)).persistOrUpdate(any(ProdutoEventDTO.class));

    }

}
