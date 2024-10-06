package com.attus.processojuridico.repository;

import com.attus.processojuridico.model.Acao;
import com.attus.processojuridico.model.TipoAcao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AcaoRepository extends JpaRepository<Acao, Long> {
    List<Acao> findByProcessoId(Long processoId);
    List<Acao> findByTipo(TipoAcao tipo);
    List<Acao> findByDataRegistroBetween(LocalDate inicio, LocalDate fim);
}