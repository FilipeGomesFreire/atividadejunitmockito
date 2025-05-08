package com.example;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;

class PedidoServiceTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private PedidoService pedidoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Inicia os mocks manualmente
    }

    @Test
    @DisplayName("Deve criar pedido com sucesso")
    void deveCriarPedidoComSucesso() {
        // Arrange (preparação dos mocks e dados)
        String cpf = "12345678900";
        Cliente cliente = new Cliente(cpf, "João", "joao@email.com");
        Pedido pedidoMock = new Pedido(1, "Monitor", 2, 500.0);

        when(clienteRepository.buscarPorCpf(cpf)).thenReturn(Optional.of(cliente));
        when(pedidoRepository.salvar(any(Pedido.class))).thenReturn(pedidoMock);

        // Act
        Pedido resultado = pedidoService.criarPedido(cpf, "Monitor", 2, 500.0);

        // Assert
        assertNotNull(resultado);
        assertEquals("Monitor", resultado.getProduto());
        assertEquals(2, resultado.getQuantidade());
        assertEquals(500.0, resultado.getPrecoUnitario());
    }
}
