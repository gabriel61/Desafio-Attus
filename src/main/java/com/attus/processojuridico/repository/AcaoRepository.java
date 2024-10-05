package com.attus.processojuridico.repository;

import com.attus.processojuridico.model.Acao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AcaoRepository extends JpaRepository<Acao, Long> {
}