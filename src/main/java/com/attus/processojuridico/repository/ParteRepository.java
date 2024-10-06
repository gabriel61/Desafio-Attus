package com.attus.processojuridico.repository;

import com.attus.processojuridico.model.Parte;
import com.attus.processojuridico.model.TipoParte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParteRepository extends JpaRepository<Parte, Long> {
    List<Parte> findByProcessoId(Long processoId);
    List<Parte> findByTipo(TipoParte tipo);
    Parte findByCpfCnpj(String cpfCnpj);
}