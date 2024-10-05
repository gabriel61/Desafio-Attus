package com.attus.processojuridico.model;

import lombok.Data;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "processos")
public class Processo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String numero;

    @Column(nullable = false)
    private LocalDate dataAbertura;

    @Column(nullable = false)
    private String descricao;

    @Enumerated(EnumType.STRING)
    private StatusProcesso status;

    @OneToMany(mappedBy = "processo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Parte> partes;

    @OneToMany(mappedBy = "processo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Acao> acoes;
}