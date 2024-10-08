package com.attus.processojuridico.service;

import com.attus.processojuridico.model.Processo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProcessoService {
    Processo criarProcesso(Processo processo);
    Processo atualizarProcesso(Long id, Processo processo);
    Processo buscarProcessoPorId(Long id);
    Page<Processo> listarProcessos(Pageable pageable);
    void arquivarProcesso(Long id);
}