package com.attus.processojuridico.service;

import com.attus.processojuridico.model.Acao;
import java.util.List;

public interface AcaoService {
    Acao registrarAcao(Long processoId, Acao acao);
    Acao atualizarAcao(Long id, Acao acao);
    Acao buscarAcaoPorId(Long id);
    List<Acao> listarAcoesPorProcesso(Long processoId);
    void removerAcao(Long id);
}