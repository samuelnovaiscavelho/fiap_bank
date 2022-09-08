package com.example.fiap_bank.service;

import com.example.fiap_bank.model.Cliente;
import com.example.fiap_bank.model.Conta;
import com.example.fiap_bank.repository.ClienteRepository;
import com.example.fiap_bank.repository.ContaRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
public class ContaService {

    private ContaRepository contaRepository;

    private ClienteRepository clienteRepository;

    public ContaService(ContaRepository contaRepository, ClienteRepository clienteRepository){
        this.contaRepository = contaRepository;
        this.clienteRepository = clienteRepository;
    }

    public Map<String, String> depositar(Integer numeroConta, Integer senha, Double valor){
        Optional<Conta> conta = contaRepository.findContaByNumero(numeroConta);

        Map<String, String> response = new HashMap<>();

        if(conta.isPresent() && Objects.equals(conta.get().getSenha(), senha)) {
            if (valor > 0) {
                conta.get().setSaldo(conta.get().getSaldo() + valor);
                response.put("message", String.format("Depositado! Saldo em conta %2f", conta.get().getSaldo()));
                contaRepository.save(conta.get());
            } else
                response.put("message", String.format("Valor tem que ser positivo"));
        }else{
            response.put("message", String.format("Usuario não encontrado ou senha incorreta"));
        }

        return response;
    }

    public Map<String, String> sacar(Integer numeroConta, Integer senha, Double valor){
        Optional<Conta> conta = contaRepository.findContaByNumero(numeroConta);
        Map<String, String> response = new HashMap<>();

        if(conta.isPresent() && Objects.equals(conta.get().getSenha(), senha)) {
            if (valor > 0) {
                if(possuiLimite(conta.get())) {
                    conta.get().setSaldo(conta.get().getSaldo() - valor);
                    response.put("message", String.format("Sacado! Saldo em conta %2f", conta.get().getSaldo()));
                    contaRepository.save(conta.get());
                }else
                    response.put("message", String.format("Sem limite! Saldo em conta %2f", conta.get().getSaldo()));
            } else
                response.put("message", String.format("Valor tem que ser positivo"));
        }else{
            response.put("message", String.format("Usuario não encontrado ou senha incorreta"));
        }


        return response;
    }

    private boolean possuiLimite(Conta conta) {
        if(conta.getTipoConta().equals(Conta.TipoConta.POUPANCA) && conta.getSaldo() > 0)
            return true;

        if(conta.getTipoConta().equals(Conta.TipoConta.CORRENTE) && conta.getSaldo() > -1000)
            return true;

        return false;
    }

    public Map<String, String> saldo(Integer numeroConta, Integer senha){
        Optional<Conta> conta = contaRepository.findContaByNumero(numeroConta);

        Map<String, String> response = new HashMap<>();

        if(conta.isPresent() && Objects.equals(conta.get().getSenha(), senha)) {
            response.put("message", String.format("Saldo em conta %2f", conta.get().getSaldo()));
        }else{
            response.put("message", String.format("Usuario não encontrado ou senha incorreta"));
        }
        return response;
    }

    public Conta insert(Conta conta) {
        validateData(conta);
        conta.setCliente(clienteRepository.findClienteByNumero(conta.getCliente().getNumero()).get());
        return contaRepository.saveAndFlush(conta);
    }

    private void validateData(Conta conta) {
        Optional<Cliente> cliente = clienteRepository.findClienteByNumero(conta.getCliente().getNumero());

        if(cliente.isEmpty())
            throw new RuntimeException("Cliente não existe");

        if(Objects.nonNull(cliente.get().getConta()))
            throw new RuntimeException("Cliente já possui conta");

        if(cliente.get().getTipoCliente().equals(Cliente.TipoCliente.JURIDICO) & conta.getTipoConta().equals(Conta.TipoConta.POUPANCA))
            throw new RuntimeException("Pessoa Juridica não pode ter poupança");
    }
}