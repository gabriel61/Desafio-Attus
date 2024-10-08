package com.attus.processojuridico.service;

import com.attus.processojuridico.model.Processo;
import com.attus.processojuridico.model.StatusProcesso;
import com.attus.processojuridico.repository.ProcessoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProcessoServiceTest {

    @Mock
    private ProcessoRepository processoRepository;

    @InjectMocks
    private ProcessoServiceImpl processoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveCriarProcesso() {
        Processo processo = new Processo();
        processo.setNumero("12345");
        processo.setDescricao("Processo de teste");
        processo.setDataAbertura(LocalDate.now());
        processo.setStatus(StatusProcesso.ATIVO);

        when(processoRepository.save(any(Processo.class))).thenReturn(processo);

        Processo resultado = processoService.criarProcesso(processo);

        assertNotNull(resultado);
        assertEquals("12345", resultado.getNumero());
        verify(processoRepository, times(1)).save(any(Processo.class));
    }

    @Test
    void deveAtualizarProcesso() {
        Long id = 1L;
        Processo processoExistente = new Processo();
        processoExistente.setId(id);
        processoExistente.setNumero("12345");
        processoExistente.setDescricao("Processo original");
        processoExistente.setDataAbertura(LocalDate.now());
        processoExistente.setStatus(StatusProcesso.ATIVO);

        Processo processoAtualizado = new Processo();
        processoAtualizado.setNumero("12345");
        processoAtualizado.setDescricao("Processo atualizado");
        processoAtualizado.setStatus(StatusProcesso.SUSPENSO);

        when(processoRepository.findById(id)).thenReturn(Optional.of(processoExistente));
        when(processoRepository.save(any(Processo.class))).thenReturn(processoAtualizado);

        Processo resultado = processoService.atualizarProcesso(id, processoAtualizado);

        assertNotNull(resultado);
        assertEquals("Processo atualizado", resultado.getDescricao());
        assertEquals(StatusProcesso.SUSPENSO, resultado.getStatus());
        verify(processoRepository, times(1)).findById(id);
        verify(processoRepository, times(1)).save(any(Processo.class));
    }

    @Test
    void deveBuscarProcessoPorId() {
        Long id = 1L;
        Processo processo = new Processo();
        processo.setId(id);
        processo.setNumero("12345");

        when(processoRepository.findById(id)).thenReturn(Optional.of(processo));

        Optional<Processo> resultado = processoService.buscarProcessoPorId(id);

        assertTrue(resultado.isPresent());
        assertEquals(id, resultado.get().getId());
        verify(processoRepository, times(1)).findById(id);
    }

    @Test
    void deveListarProcessos() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        List<Processo> processos = List.of(new Processo(), new Processo());
        Page<Processo> page = new PageImpl<>(processos, pageRequest, processos.size());

        when(processoRepository.findAll(pageRequest)).thenReturn(page);

        Page<Processo> resultado = processoService.listarProcessos(pageRequest);

        assertNotNull(resultado);
        assertEquals(2, resultado.getTotalElements());
        verify(processoRepository, times(1)).findAll(pageRequest);
    }

    @Test
    void deveArquivarProcesso() {
        Long id = 1L;
        Processo processo = new Processo();
        processo.setId(id);
        processo.setStatus(StatusProcesso.ATIVO);

        when(processoRepository.findById(id)).thenReturn(Optional.of(processo));
        when(processoRepository.save(any(Processo.class))).thenReturn(processo);

        processoService.arquivarProcesso(id);

        assertEquals(StatusProcesso.ARQUIVADO, processo.getStatus());
        verify(processoRepository, times(1)).findById(id);
        verify(processoRepository, times(1)).save(processo);
    }
}