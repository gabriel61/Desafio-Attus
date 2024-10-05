package com.attus.processojuridico.model;

import lombok.Data;
import jakarta.persistence.*;

@Data
@Entity
@Table(name = "partes")
public class Parte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String cpfCnpj;

    @Enumerated(EnumType.STRING)
    private TipoParte tipo;

    private String email;

    private String telefone;

    @ManyToOne
    @JoinColumn(name = "processo_id", nullable = false)
    private Processo processo;
}