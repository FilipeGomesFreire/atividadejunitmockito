package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LivroTest {
    private Livro livro;

    @BeforeEach
    public void setUp() {
        livro = new Livro("Livro A", "Autor A");
    }

}
