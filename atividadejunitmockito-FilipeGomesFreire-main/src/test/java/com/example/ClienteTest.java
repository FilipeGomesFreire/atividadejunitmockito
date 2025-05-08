package com.example;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ClienteTest {

    // esse o chatgpt me ajudou, fiz um test pra ver se o cpf era valido
    private boolean isCpfValido(String cpf) {
        if (cpf == null || cpf.length() != 11 || cpf.matches("(\\d)\\1{10}"))
            return false;

        try {
            int soma = 0;
            for (int i = 0; i < 9; i++)
                soma += (cpf.charAt(i) - '0') * (10 - i);
            int dig1 = 11 - (soma % 11);
            if (dig1 >= 10) dig1 = 0;
            if (dig1 != (cpf.charAt(9) - '0')) return false;

            soma = 0;
            for (int i = 0; i < 10; i++)
                soma += (cpf.charAt(i) - '0') * (11 - i);
            int dig2 = 11 - (soma % 11);
            if (dig2 >= 10) dig2 = 0;
            return dig2 == (cpf.charAt(10) - '0');
        } catch (Exception e) {
            return false;
        }
    }

    @Test
    @DisplayName("Validação de CPF: deve considerar CPF válido")
    void deveAceitarCpfValido() {
        assertTrue(isCpfValido("12345678909")); // CPF válido
    }

    @Test
    @DisplayName("Validação de CPF: deve considerar CPF inválido")
    void deveRejeitarCpfInvalido() {
        assertFalse(isCpfValido("12345678900")); // CPF inválido
        assertFalse(isCpfValido("11111111111")); // todos iguais
        assertFalse(isCpfValido("abc"));         // string inválida
    }

    @Test
    @DisplayName("Criação de cliente com dados válidos")
    void deveCriarCliente() {
        Cliente cliente = new Cliente("12345678900", "Fulano", "fulano@email.com");
        assertNotNull(cliente);
        assertEquals("Fulano", cliente.getNome());
    }
}
