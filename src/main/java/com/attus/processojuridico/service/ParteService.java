package com.attus.processojuridico.service;

import com.attus.processojuridico.model.Parte;
import java.util.List;

public interface ParteService {
    Parte criarParte(Long processoId, Parte parte);
    Parte atualizarParte(Long id, Parte parte);
    Parte buscarPartePorId(Long id);
    List<Parte> listarPartesPorProcesso(Long processoId);
    void removerParte(Long id);
}