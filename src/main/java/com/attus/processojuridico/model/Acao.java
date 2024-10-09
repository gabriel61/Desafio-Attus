package com.attus.processojuridico.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import jakarta.persistence.*;
import java.time.LocalDate;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;

@Data
@Entity
@Table(name = "acoes")
public class Acao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TipoAcao tipo;

    @NotNull(message = "A data do registro é obrigatória")
    @Column(nullable = false)
    private LocalDate dataRegistro;

    @NotBlank(message = "A descrição é obrigatória")
    @Column(nullable = false)
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "processo_id", nullable = false)
    @JsonBackReference
    private Processo processo;
}