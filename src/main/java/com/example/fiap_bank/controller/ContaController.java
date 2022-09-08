package com.example.fiap_bank.controller;

import com.example.fiap_bank.model.Conta;
import com.example.fiap_bank.model.Operacao;
import com.example.fiap_bank.service.ContaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/conta")
public class ContaController {

    private ContaService contaService;

    public ContaController(ContaService contaService){
        this.contaService = contaService;
    }

    @GetMapping("ping")
    public String pong(){
        return "pong";
    }

    @PostMapping("/create")
    public ResponseEntity<Conta> create(@RequestBody Conta conta){
        return new ResponseEntity<>(contaService.insert(conta), HttpStatus.CREATED);
    }

    @PostMapping("/sacar")
    public ResponseEntity<Map<String, String>> sacar(@RequestBody Operacao operacao){
        return ResponseEntity.ok(contaService.sacar(operacao.getNumeroConta(), operacao.getSenha(), operacao.getValor()));
    }

    @PostMapping("/depositar")
    public ResponseEntity<Map<String, String>> depositar(@RequestBody Operacao operacao){
        return ResponseEntity.ok(contaService.depositar(operacao.getNumeroConta(), operacao.getSenha(), operacao.getValor()));
    }

    @PostMapping("/saldo")
    public ResponseEntity<Map<String, String>> saldo(@RequestBody Operacao operacao){
        return ResponseEntity.ok(contaService.saldo(operacao.getNumeroConta(), operacao.getSenha()));
    }
}
