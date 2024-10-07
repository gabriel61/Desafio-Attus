package com.attus.processojuridico.service;

import com.attus.processojuridico.exception.NotFoundException;
import com.attus.processojuridico.model.Acao;
import com.attus.processojuridico.model.Processo;
import com.attus.processojuridico.repository.AcaoRepository;
import com.attus.processojuridico.repository.ProcessoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AcaoServiceImpl implements AcaoService {

    @Autowired
    private AcaoRepository acaoRepository;

    @Autowired
    private ProcessoRepository processoRepository;

    @Override
    public Acao registrarAcao(Long processoId, Acao acao) {
        Processo processo = processoRepository.findById(processoId)
                .orElseThrow(() -> new NotFoundException("Processo não encontrado"));
        acao.setProcesso(processo);
        acao.setDataRegistro(LocalDate.now());
        return acaoRepository.save(acao);
    }

    @Override
    public Acao atualizarAcao(Long id, Acao acaoAtualizada) {
        Acao acao = buscarAcaoPorId(id);
        acao.setTipo(acaoAtualizada.getTipo());
        acao.setDescricao(acaoAtualizada.getDescricao());
        return acaoRepository.save(acao);
    }

    @Override
    public Acao buscarAcaoPorId(Long id) {
        return acaoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Ação não encontrada"));
    }

    @Override
    public List<Acao> listarAcoesPorProcesso(Long processoId) {
        Processo processo = processoRepository.findById(processoId)
                .orElseThrow(() -> new NotFoundException("Processo não encontrado"));
        return processo.getAcoes();
    }

    @Override
    public void removerAcao(Long id) {
        Acao acao = buscarAcaoPorId(id);
        acaoRepository.delete(acao);
    }
}