package com.example;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ClienteTest {

    // isso eu quis colocar pra ficar um diferencial bonito, ai pedi pra o chatgpt me ajudar
    
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
    @DisplayName("✅ CRIANDO CLIENTE")
    void deveCriarCliente() {
        Cliente cliente = new Cliente("12345678900", "João", "joao@email.com");

        assertEquals("12345678900", cliente.getCpf());
        assertEquals("João", cliente.getNome());
        assertEquals("joao@email.com", cliente.getEmail());
    }

    @Test
    @DisplayName("✅ VALIDANDO CPF")
    void deveValidarCpfValido() {
        assertTrue(isCpfValido("12345678909")); // CPF válido
    }

    @Test
    @DisplayName("❌ INVALIDANDO CPF")
    void deveInvalidarCpfInvalido() {
        assertFalse(isCpfValido("12345678900")); // dígito errado
        assertFalse(isCpfValido("11111111111")); // repetido
        assertFalse(isCpfValido("abc123"));      // letras
        assertFalse(isCpfValido(""));            // vazio
    }
}
