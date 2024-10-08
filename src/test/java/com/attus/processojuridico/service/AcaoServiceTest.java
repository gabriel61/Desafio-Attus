package com.attus.processojuridico.service;

import com.attus.processojuridico.exception.NotFoundException;
import com.attus.processojuridico.model.Acao;
import com.attus.processojuridico.model.Processo;
import com.attus.processojuridico.model.TipoAcao;
import com.attus.processojuridico.repository.AcaoRepository;
import com.attus.processojuridico.repository.ProcessoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AcaoServiceTest {

    @Mock
    private AcaoRepository acaoRepository;

    @Mock
    private ProcessoRepository processoRepository;

    @InjectMocks
    private AcaoServiceImpl acaoService;

    private Processo processoExemplo;
    private Acao acaoExemplo;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        processoExemplo = new Processo();
        processoExemplo.setId(1L);
        processoExemplo.setNumero("12345");

        acaoExemplo = new Acao();
        acaoExemplo.setTipo(TipoAcao.PETICAO);
        acaoExemplo.setDescricao("Descrição da Ação");
        acaoExemplo.setDataRegistro(LocalDate.now());
        acaoExemplo.setProcesso(processoExemplo);
    }

    @Test
    void deveRegistrarAcao() {
        when(processoRepository.findById(1L)).thenReturn(Optional.of(processoExemplo));
        when(acaoRepository.save(any(Acao.class))).thenReturn(acaoExemplo);

        Acao acaoRegistrada = acaoService.registrarAcao(1L, acaoExemplo);

        assertNotNull(acaoRegistrada);
        assertEquals("Descrição da Ação", acaoRegistrada.getDescricao());
        verify(acaoRepository, times(1)).save(any(Acao.class));
    }

    @Test
    void deveLancarExceptionAoRegistrarAcaoComProcessoInexistente() {
        when(processoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            acaoService.registrarAcao(1L, acaoExemplo);
        });
    }

    @Test
    void deveAtualizarAcao() {
        acaoExemplo.setId(1L);
        when(acaoRepository.findById(1L)).thenReturn(Optional.of(acaoExemplo));
        when(acaoRepository.save(any(Acao.class))).thenReturn(acaoExemplo);

        Acao acaoAtualizada = acaoService.atualizarAcao(1L, acaoExemplo);

        assertNotNull(acaoAtualizada);
        assertEquals("Descrição da Ação", acaoAtualizada.getDescricao());
        verify(acaoRepository, times(1)).save(any(Acao.class));
    }

    @Test
    void deveLancarExceptionAoAtualizarAcaoInexistente() {
        when(acaoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            acaoService.atualizarAcao(1L, acaoExemplo);
        });
    }

    @Test
    void deveBuscarAcaoPorId() {
        acaoExemplo.setId(1L);
        when(acaoRepository.findById(1L)).thenReturn(Optional.of(acaoExemplo));

        Acao acaoEncontrada = acaoService.buscarAcaoPorId(1L);

        assertNotNull(acaoEncontrada);
        assertEquals(1L, acaoEncontrada.getId());
    }

    @Test
    void deveLancarExceptionAoBuscarAcaoInexistente() {
        when(acaoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            acaoService.buscarAcaoPorId(1L);
        });
    }

    @Test
    void deveListarAcoesPorProcesso() {
        acaoExemplo.setId(1L);
        processoExemplo.setAcoes(Arrays.asList(acaoExemplo));

        when(processoRepository.findById(1L)).thenReturn(Optional.of(processoExemplo));
        when(acaoRepository.findByProcessoId(1L)).thenReturn(Arrays.asList(acaoExemplo));

        var acoes = acaoService.listarAcoesPorProcesso(1L);

        assertFalse(acoes.isEmpty());
        assertEquals(1, acoes.size());
        assertEquals(acaoExemplo, acoes.get(0));
    }

    @Test
    void deveLancarExceptionAoListarAcoesDeProcessoInexistente() {
        when(processoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            acaoService.listarAcoesPorProcesso(1L);
        });
    }

    @Test
    void deveRemoverAcao() {
        acaoExemplo.setId(1L);
        when(acaoRepository.findById(1L)).thenReturn(Optional.of(acaoExemplo));

        acaoService.removerAcao(1L);

        verify(acaoRepository, times(1)).delete(acaoExemplo);
    }

    @Test
    void deveLancarExceptionAoRemoverAcaoInexistente() {
        when(acaoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            acaoService.removerAcao(1L);
        });
    }
}