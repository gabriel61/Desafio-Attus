package com.attus.processojuridico.service;

import com.attus.processojuridico.exception.NotFoundException;
import com.attus.processojuridico.model.Parte;
import com.attus.processojuridico.model.Processo;
import com.attus.processojuridico.repository.ParteRepository;
import com.attus.processojuridico.repository.ProcessoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParteServiceImpl implements ParteService {

    @Autowired
    private ParteRepository parteRepository;

    @Autowired
    private ProcessoRepository processoRepository;

    @Override
    public Parte criarParte(Long processoId, Parte parte) {
        if (parteRepository.existsByCpfCnpj(parte.getCpfCnpj())) {
            throw new RuntimeException("CPF/CNPJ já registrado");
        }
        Processo processo = processoRepository.findById(processoId)
                .orElseThrow(() -> new NotFoundException("Processo não encontrado"));
        parte.setProcesso(processo);
        return parteRepository.save(parte);
    }

    @Override
    public Parte atualizarParte(Long id, Parte parteAtualizada) {
        Parte parte = buscarPartePorIdEProcesso(id, parteAtualizada.getProcesso().getId());
        parte.setNome(parteAtualizada.getNome());
        parte.setCpfCnpj(parteAtualizada.getCpfCnpj());
        parte.setTipo(parteAtualizada.getTipo());
        parte.setEmail(parteAtualizada.getEmail());
        parte.setTelefone(parteAtualizada.getTelefone());
        return parteRepository.save(parte);
    }

    public Parte buscarPartePorIdEProcesso(Long id, Long processoId) {
        return parteRepository.findByIdAndProcessoId(id, processoId)
                .orElseThrow(() -> new NotFoundException("Parte não encontrada no processo"));
    }

    @Override
    public List<Parte> listarPartesPorProcesso(Long processoId) {
        Processo processo = processoRepository.findById(processoId)
                .orElseThrow(() -> new NotFoundException("Processo não encontrado"));
        return processo.getPartes();
    }

    @Override
    public boolean removerParte(Long id, Long processoId) {
        Parte parte = buscarPartePorIdEProcesso(id, processoId);
        parteRepository.delete(parte);
        return true;
    }
}