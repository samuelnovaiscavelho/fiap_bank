package com.example.fiap_bank.repository;


import com.example.fiap_bank.model.Conta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Long> {
    Optional<Conta> findContaByNumero(Integer numero);
}
