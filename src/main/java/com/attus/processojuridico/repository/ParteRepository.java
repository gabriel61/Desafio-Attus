package com.attus.processojuridico.repository;

import com.attus.processojuridico.model.Parte;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParteRepository extends JpaRepository<Parte, Long> {
}