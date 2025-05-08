package com.example;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;

public class PedidoTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private PedidoService pedidoService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    // Parte 1 — Testes unitários

    @Test
    @DisplayName("✅ CRIANDO PEDIDO")
    void deveCriarPedidoComSucesso() {
        Cliente cliente = new Cliente("12345678900", "João", "joao@email.com");
        Pedido pedido = new Pedido(1, "Monitor", 2, 500.0);

        when(clienteRepository.buscarPorCpf("12345678900")).thenReturn(Optional.of(cliente));
        when(pedidoRepository.salvar(any(Pedido.class))).thenReturn(pedido);

        Pedido resultado = pedidoService.criarPedido("12345678900", "Monitor", 2, 500.0);

        assertNotNull(resultado);
        assertEquals("Monitor", resultado.getProduto());
        assertEquals(2, resultado.getQuantidade());

        verify(clienteRepository).buscarPorCpf("12345678900");
        verify(pedidoRepository).salvar(any());
    }

    @Test
    @DisplayName("❌ TENTANDO UMA QUANTIDADE INVALIDA")
    void deveLancarExcecaoQuantidadeInvalida() {
        assertThrows(IllegalArgumentException.class, () -> {
            pedidoService.criarPedido("12345678900", "Monitor", 0, 500.0);
        });
    }

    @Test
    @DisplayName("❌ TENTANDO CRIAR PEDIDO COM CLIENTE Q NÃO EXISTE")
    void deveLancarExcecaoClienteInexistente() {
        when(clienteRepository.buscarPorCpf("00000000000")).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            pedidoService.criarPedido("00000000000", "Teclado", 1, 150.0);
        });

        verify(clienteRepository).buscarPorCpf("00000000000");
    }

    @Test
    @DisplayName("✅ LISTAR PEDIDOS POR CPF")
    void deveListarPedidosPorCpf() {
        List<Pedido> pedidos = Arrays.asList(
            new Pedido(1, "Mouse", 1, 100.0),
            new Pedido(2, "Teclado", 1, 200.0)
        );

        when(pedidoRepository.listarPedidosPorCliente("12345678900")).thenReturn(pedidos);

        List<Pedido> resultado = pedidoService.listarPedidosCliente("12345678900");

        assertEquals(2, resultado.size());
        assertEquals("Mouse", resultado.get(0).getProduto());

        verify(pedidoRepository).listarPedidosPorCliente("12345678900");
    }

    @Test
    @DisplayName("✅ CANCELAR PEDIDOS")
    void deveCancelarPedidoComSucesso() {
        when(pedidoRepository.removerPedido(1)).thenReturn(true);

        boolean resultado = pedidoService.cancelarPedido(1);

        assertTrue(resultado);
        verify(pedidoRepository).removerPedido(1);
    }

    @Test
    @DisplayName("❌ TENTAR CANCELAR PEDIDO INEXISTENTE")
    void deveRetornarFalsoAoCancelarPedidoInexistente() {
        when(pedidoRepository.removerPedido(99)).thenReturn(false);

        boolean resultado = pedidoService.cancelarPedido(99);

        assertFalse(resultado);
        verify(pedidoRepository).removerPedido(99);
    }

    @Test
    @DisplayName("✅ CADASTRAR CLIENTE")
    void deveCadastrarClienteComSucesso() {
        Cliente cliente = new Cliente("12345678900", "Maria", "maria@email.com");

        when(clienteRepository.salvar(any())).thenReturn(cliente);

        Cliente resultado = pedidoService.cadastrarCliente("12345678900", "Maria", "maria@email.com");

        assertNotNull(resultado);
        assertEquals("Maria", resultado.getNome());

        verify(clienteRepository).salvar(any());
    }

    // Parte 2 — Teste com Pedido + PedidoService
    @Test
    @DisplayName("✅ CALCULAR VALOR TOTAL PEDIDO")
    void deveCalcularTotalDoPedido() {
        Pedido pedido = new Pedido(1, "Monitor", 2, 500.0);
        assertEquals(1000.0, pedido.calcularTotal());
    }
}
