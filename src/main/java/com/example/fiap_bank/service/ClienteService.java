package com.example.fiap_bank.service;

import com.example.fiap_bank.model.Cliente;
import com.example.fiap_bank.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    private ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public List<Cliente> findAll(){
        return clienteRepository.findAll();
    }

    public List<Cliente> findAllByTipoCliente(Cliente.TipoCliente tipoCliente){
        return findAll().stream()
                .filter(cliente -> cliente.getTipoCliente().equals(tipoCliente))
                .collect(Collectors.toList());
    }

    public Cliente create(Cliente cliente) {
        validateData(cliente);
        return clienteRepository.save(cliente);
    }

    public Optional<Cliente> findByNumeroConta(Integer numeroConta){
        return clienteRepository.findClienteByNumero(numeroConta);
    }

    public void delete(Integer numeroConta){
        clienteRepository.findClienteByNumero(numeroConta).ifPresent(cliente -> clienteRepository.delete(cliente));
    }

    private void validateData(Cliente cliente) {
        if (cliente.getTipoCliente().equals(Cliente.TipoCliente.FISICO)) {
            if (!cliente.getDocumento().matches("^[0-9]{3}\\.?[0-9]{3}\\.?[0-9]{3}\\-?[0-9]{2}$")) {
                throw new RuntimeException("CPF invalido");
            }

            if (cliente.getTipoCliente().equals(Cliente.TipoCliente.JURIDICO)) {
                if (!cliente.getDocumento().matches("^[0-9]{2}\\.?[0-9]{3}\\.?[0-9]{3}\\/?[0-9]{4}\\-?[0-9]{2}$"))
                    throw new RuntimeException("CNPJ invalido");
            }
        }
    }
}
