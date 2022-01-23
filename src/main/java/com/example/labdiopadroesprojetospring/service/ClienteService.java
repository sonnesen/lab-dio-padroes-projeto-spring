package com.example.labdiopadroesprojetospring.service;

import com.example.labdiopadroesprojetospring.model.Cliente;

public interface ClienteService {
    Iterable<Cliente> buscarTodos();

    Cliente buscarPorId(Long id);

    Cliente incluir(Cliente cliente);

    Cliente atualizar(Long id, Cliente cliente);

    void excluir(Long id);
}
