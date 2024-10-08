package com.attus.processojuridico.service;

import com.attus.processojuridico.exception.NotFoundException;
import com.attus.processojuridico.model.Processo;
import com.attus.processojuridico.model.StatusProcesso;
import com.attus.processojuridico.repository.ProcessoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Optional;

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
        Optional<Processo> processoOpt = processoRepository.findById(id);
        if (processoOpt.isPresent()) {
            Processo processo = processoOpt.get();
            if (!processo.getNumero().equals(processoAtualizado.getNumero())) {
                if (processoRepository.findByNumero(processoAtualizado.getNumero()) != null) {
                    throw new NotFoundException("Número do processo já existe");
                }
            }
            processo.setDescricao(processoAtualizado.getDescricao());
            processo.setStatus(processoAtualizado.getStatus());
            processo.setNumero(processoAtualizado.getNumero());
            return processoRepository.save(processo);
        } else {
            throw new NotFoundException("Processo não encontrado");
        }
    }

    @Override
    public Optional<Processo> buscarProcessoPorId(Long id) {
        return processoRepository.findById(id);
    }

    @Override
    public Page<Processo> listarProcessos(Pageable pageable) {
        return processoRepository.findAll(pageable);
    }

    @Override
    public void arquivarProcesso(Long id) {
        Optional<Processo> processoOpt = processoRepository.findById(id);

        if (processoOpt.isPresent()) {
            Processo processo = processoOpt.get();
            processo.setStatus(StatusProcesso.ARQUIVADO);
            processoRepository.save(processo);
        } else {
            throw new NotFoundException("Processo não encontrado");
        }
    }
}