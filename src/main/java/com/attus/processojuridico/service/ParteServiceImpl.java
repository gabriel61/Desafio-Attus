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
        Processo processo = processoRepository.findById(processoId)
                .orElseThrow(() -> new NotFoundException("Processo não encontrado"));
        parte.setProcesso(processo);
        return parteRepository.save(parte);
    }

    @Override
    public Parte atualizarParte(Long id, Parte parteAtualizada) {
        Parte parte = buscarPartePorId(id);
        parte.setNome(parteAtualizada.getNome());
        parte.setCpfCnpj(parteAtualizada.getCpfCnpj());
        parte.setTipo(parteAtualizada.getTipo());
        parte.setEmail(parteAtualizada.getEmail());
        parte.setTelefone(parteAtualizada.getTelefone());
        return parteRepository.save(parte);
    }

    @Override
    public Parte buscarPartePorId(Long id) {
        return parteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Parte não encontrada"));
    }

    @Override
    public List<Parte> listarPartesPorProcesso(Long processoId) {
        Processo processo = processoRepository.findById(processoId)
                .orElseThrow(() -> new NotFoundException("Processo não encontrado"));
        return processo.getPartes();
    }

    @Override
    public void removerParte(Long id) {
        Parte parte = buscarPartePorId(id);
        parteRepository.delete(parte);
    }
}