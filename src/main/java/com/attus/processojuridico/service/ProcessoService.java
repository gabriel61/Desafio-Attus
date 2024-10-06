package com.attus.processojuridico.service;

import com.attus.processojuridico.model.Processo;
import java.util.List;

public interface ProcessoService {
    Processo criarProcesso(Processo processo);
    Processo atualizarProcesso(Long id, Processo processo);
    Processo buscarProcessoPorId(Long id);
    List<Processo> listarProcessos();
    void arquivarProcesso(Long id);
}