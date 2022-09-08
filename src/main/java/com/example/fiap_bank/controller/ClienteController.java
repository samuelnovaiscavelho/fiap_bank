package com.example.fiap_bank.controller;

import com.example.fiap_bank.model.Cliente;
import com.example.fiap_bank.service.ClienteService;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    private ClienteService clienteService;

    public ClienteController(ClienteService clienteService){
        this.clienteService = clienteService;
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<Cliente> create(@RequestBody Cliente cliente){
        return new ResponseEntity<>(clienteService.create(cliente), HttpStatus.CREATED);
    }

    @GetMapping("/get")
    public ResponseEntity<Optional<Cliente>> getByNumeroConta(@Param(value = "numero_conta") Integer numeroConta){
        return clienteService.findByNumeroConta(numeroConta).map(cliente ->
                new ResponseEntity<>(cliente, HttpStatus.OK)).orElse(new ResponseEntity(null, HttpStatus.NOT_FOUND));
    }

    @GetMapping("/get/{tipoCliente}")
    public ResponseEntity<List<Cliente>> getAllByTipoConta(@PathVariable Cliente.TipoCliente tipoCliente){
        return new ResponseEntity<>(clienteService.findAllByTipoCliente(tipoCliente), HttpStatus.OK);
    }

    @GetMapping("/ping")
    public String ping(){
        return "pong";
    }
}
