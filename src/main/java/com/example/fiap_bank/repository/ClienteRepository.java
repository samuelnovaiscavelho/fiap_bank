package com.example.fiap_bank.repository;

import com.example.fiap_bank.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findClienteByConta_Numero(Integer conta);
    Optional<Cliente> findClienteByNumero(Integer numeroCOnta);
}
