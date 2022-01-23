package com.example.labdiopadroesprojetospring.service.impl;

import com.example.labdiopadroesprojetospring.exception.ClienteNaoEncontrado;
import com.example.labdiopadroesprojetospring.model.Cliente;
import com.example.labdiopadroesprojetospring.model.ClienteRepository;
import com.example.labdiopadroesprojetospring.model.Endereco;
import com.example.labdiopadroesprojetospring.model.EnderecoRepository;
import com.example.labdiopadroesprojetospring.service.ClienteService;
import com.example.labdiopadroesprojetospring.service.ViaCepService;
import org.springframework.stereotype.Service;

@Service
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;
    private final EnderecoRepository enderecoRepository;
    private final ViaCepService viaCepService;

    public ClienteServiceImpl(ClienteRepository clienteRepository, EnderecoRepository enderecoRepository, ViaCepService viaCepService) {
        this.clienteRepository = clienteRepository;
        this.enderecoRepository = enderecoRepository;
        this.viaCepService = viaCepService;
    }

    @Override
    public Iterable<Cliente> buscarTodos() {
        return clienteRepository.findAll();
    }

    @Override
    public Cliente buscarPorId(Long id) {
        return clienteRepository
                .findById(id).orElseThrow(() -> new ClienteNaoEncontrado(id));
    }

    @Override
    public Cliente incluir(Cliente cliente) {
        String cep = cliente.getEndereco().getCep();

        Endereco endereco = enderecoRepository.findById(cep).orElseGet(() -> {
            Endereco novoEndereco = viaCepService.consultarCep(cep);
            return enderecoRepository.save(novoEndereco);
        });

        cliente.setEndereco(endereco);

        return clienteRepository.save(cliente);
    }

    @Override
    public Cliente atualizar(Long id, Cliente cliente) {
        Cliente clienteParaAtualizar = clienteRepository
                .findById(id).orElseThrow(() -> new ClienteNaoEncontrado(id));

        String cep = cliente.getEndereco().getCep();

        Endereco endereco = enderecoRepository.findById(cep).orElseGet(() -> {
            Endereco novoEndereco = viaCepService.consultarCep(cep);
            return enderecoRepository.save(novoEndereco);
        });

        clienteParaAtualizar.setEndereco(endereco);
        clienteParaAtualizar.setNome(cliente.getNome());

        return clienteRepository.save(clienteParaAtualizar);
    }

    @Override
    public void excluir(Long id) {
        clienteRepository.deleteById(id);
    }
}
