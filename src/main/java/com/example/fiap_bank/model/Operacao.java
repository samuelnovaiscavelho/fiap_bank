package com.example.fiap_bank.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Operacao {
    private Integer numeroConta;
    private Integer senha;
    private Double valor;

    public Operacao(Integer numeroConta, Integer senha){
        this.numeroConta = numeroConta;
        this.senha = senha;
    }
}
