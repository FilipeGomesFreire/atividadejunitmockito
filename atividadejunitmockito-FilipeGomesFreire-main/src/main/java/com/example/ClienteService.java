package com.example;

import java.util.List;
import java.util.Optional;

public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Cliente cadastrarCliente(String cpf, String nome, String email) {
        return clienteRepository.salvar(new Cliente(cpf, nome, email));
    }

    public Optional<Cliente> buscarPorCpf(String cpf) {
        return clienteRepository.buscarPorCpf(cpf);
    }

    public List<Cliente> listarClientes() {
        return clienteRepository.listarTodos();
    }
}
