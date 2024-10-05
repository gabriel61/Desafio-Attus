package com.attus.processojuridico.model;

import lombok.Data;
import jakarta.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "acoes")
public class Acao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TipoAcao tipo;

    @Column(nullable = false)
    private LocalDate dataRegistro;

    @Column(nullable = false)
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "processo_id", nullable = false)
    private Processo processo;
}