package com.javapaulo.contabanco.business.dto;

import jakarta.persistence.Column;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContaDTO {

    private Long id;
    private String agencia;
    private String conta;
    private double saldo;
    private double valor;
    private String agenciaDest;
    private String contaDest;
}
