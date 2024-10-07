package com.attus.processojuridico.service;

import com.attus.processojuridico.exception.NotFoundException;
import com.attus.processojuridico.model.Processo;
import com.attus.processojuridico.model.StatusProcesso;
import com.attus.processojuridico.repository.ProcessoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProcessoServiceImpl implements ProcessoService {

    @Autowired
    private ProcessoRepository processoRepository;

    @Override
    public Processo criarProcesso(Processo processo) {
        if (processoRepository.findByNumero(processo.getNumero()) != null) {
            throw new NotFoundException("Número do processo já existe");
        }
        processo.setStatus(StatusProcesso.ATIVO);
        return processoRepository.save(processo);
    }

    @Override
    public Processo atualizarProcesso(Long id, Processo processoAtualizado) {
        Processo processo = buscarProcessoPorId(id);
        if (!processo.getNumero().equals(processoAtualizado.getNumero())) {
            if (processoRepository.findByNumero(processoAtualizado.getNumero()) != null) {
                throw new NotFoundException("Número do processo já existe");
            }
        }
        processo.setDescricao(processoAtualizado.getDescricao());
        processo.setStatus(processoAtualizado.getStatus());
        processo.setNumero(processoAtualizado.getNumero());
        return processoRepository.save(processo);
    }

    @Override
    public Processo buscarProcessoPorId(Long id) {
        return processoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Processo não encontrado"));
    }

    @Override
    public List<Processo> listarProcessos() {
        return processoRepository.findAll();
    }

    @Override
    public void arquivarProcesso(Long id) {
        Processo processo = buscarProcessoPorId(id);
        processo.setStatus(StatusProcesso.ARQUIVADO);
        processoRepository.save(processo);
    }
}