package com.example.fiap_bank.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.Random;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TB_CLIENTE")
@Builder
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Builder.Default
    private Integer numero = new Random().nextInt(0, 10000);

    @Column(name = "nm_nome")
    private String nome;

    @Column(name = "nr_documento")
    private String documento;

    @Enumerated(EnumType.STRING)
    private TipoCliente tipoCliente;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonIgnoreProperties
    private Conta conta;

    public enum TipoCliente {
        FISICO, JURIDICO
    }
}
