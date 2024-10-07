package com.attus.processojuridico.model;

import lombok.Data;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Data
@Entity
@Table(name = "partes")
public class Parte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome é obrigatório")
    @Column(nullable = false)
    private String nome;

    @NotBlank(message = "O cpf/cnpj é obrigatório")
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