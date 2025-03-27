package com.javapaulo.contabanco.business;

import com.javapaulo.contabanco.entity.Conta;

import java.util.List;

public interface ContaBancoService {

    List<Conta> getAllContas();
    Conta getContaById(Long id);
    Conta findByAgenciaAndConta(String agenciaDest, String contaDest);
}
