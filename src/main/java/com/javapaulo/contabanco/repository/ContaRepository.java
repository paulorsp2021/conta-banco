package com.javapaulo.contabanco.repository;

import com.javapaulo.contabanco.entity.Conta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Long> {

    Optional<Conta> findByAgenciaAndConta(String agencia, String conta);
}
