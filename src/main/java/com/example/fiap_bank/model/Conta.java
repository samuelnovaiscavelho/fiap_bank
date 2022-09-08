package com.example.fiap_bank.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Random;

@Getter
@Setter
@Builder
@Entity
@Table(name = "TB_CONTA")
@NoArgsConstructor
@AllArgsConstructor
public class Conta implements Serializable {
    private static final long serialVersionUID = 986589124772488369L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Builder.Default
    @Column(name = "nr_numero", nullable = false)
    private Integer numero = new Random().nextInt(0, 10000);

    @Builder.Default
    @Column(name = "nr_senha", nullable = false)
    private Integer senha = new Random().nextInt(1000, 9999);

    @Builder.Default
    @Column(name = "nr_saldo")
    private Double saldo = 0.0;

    @Enumerated(EnumType.STRING)
    private TipoConta tipoConta;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "cliente_id", referencedColumnName = "id")
    private Cliente cliente;

    public enum TipoConta{
        POUPANCA, CORRENTE
    }
}
