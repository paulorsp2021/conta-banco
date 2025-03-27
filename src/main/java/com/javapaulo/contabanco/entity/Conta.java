package com.javapaulo.contabanco.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "conta")
@Builder
public class Conta {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "agencia", length = 4)
        private String agencia;

        @Column(name = "conta", length = 10)
        private String conta;

        @Column(name = "saldo", length = 30)
        private double saldo;

        @Column(name = "usuario_id")
        private Long usuario_id;

}
