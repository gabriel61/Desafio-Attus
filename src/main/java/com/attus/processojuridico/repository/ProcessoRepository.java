package com.attus.processojuridico.repository;

import com.attus.processojuridico.model.Processo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcessoRepository extends JpaRepository<Processo, Long> {
}