package com.attus.processojuridico.service;

import com.attus.processojuridico.model.Processo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProcessoService {
    Processo criarProcesso(Processo processo);
    Processo atualizarProcesso(Long id, Processo processo);
    Optional<Processo> buscarProcessoPorId(Long id);
    Page<Processo> listarProcessos(Pageable pageable);
    void arquivarProcesso(Long id);
}