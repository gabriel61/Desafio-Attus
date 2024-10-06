package com.attus.processojuridico.repository;

import com.attus.processojuridico.model.Processo;
import com.attus.processojuridico.model.StatusProcesso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProcessoRepository extends JpaRepository<Processo, Long> {
    List<Processo> findByStatus(StatusProcesso status);
    List<Processo> findByDataAberturaBetween(LocalDate inicio, LocalDate fim);
    Processo findByNumero(String numero);
}