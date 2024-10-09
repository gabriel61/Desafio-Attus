package com.attus.processojuridico.service;

import com.attus.processojuridico.exception.NotFoundException;
import com.attus.processojuridico.model.Parte;
import com.attus.processojuridico.model.Processo;
import com.attus.processojuridico.model.TipoParte;
import com.attus.processojuridico.repository.ParteRepository;
import com.attus.processojuridico.repository.ProcessoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ParteServiceTest {

    @Mock
    private ParteRepository parteRepository;

    @Mock
    private ProcessoRepository processoRepository;

    @InjectMocks
    private ParteServiceImpl parteService;

    private Parte parteExemplo;
    private Processo processoExemplo;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        processoExemplo = new Processo();
        processoExemplo.setId(1L);
        processoExemplo.setNumero("12345");
        processoExemplo.setDescricao("Descrição do Processo");
        processoExemplo.setDataAbertura(LocalDate.now());

        parteExemplo = new Parte();
        parteExemplo.setId(1L);
        parteExemplo.setNome("Parte Exemplo");
        parteExemplo.setCpfCnpj("12345678901");
        parteExemplo.setTipo(TipoParte.AUTOR);
        parteExemplo.setProcesso(processoExemplo);
    }

    @Test
    void deveCriarParte() {
        when(parteRepository.existsByCpfCnpj(parteExemplo.getCpfCnpj())).thenReturn(false);
        when(processoRepository.findById(1L)).thenReturn(Optional.of(processoExemplo));
        when(parteRepository.save(any(Parte.class))).thenReturn(parteExemplo);

        Parte novaParte = parteService.criarParte(1L, parteExemplo);

        assertNotNull(novaParte);
        assertEquals(parteExemplo.getNome(), novaParte.getNome());
        verify(parteRepository, times(1)).save(any(Parte.class));
    }

    @Test
    void deveLancarExceptionAoCriarParteComCpfCnpjExistente() {
        when(parteRepository.existsByCpfCnpj(parteExemplo.getCpfCnpj())).thenReturn(true);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            parteService.criarParte(1L, parteExemplo);
        });

        assertEquals("CPF/CNPJ já registrado", exception.getMessage());
    }

    @Test
    void deveAtualizarParte() {
        when(parteRepository.findById(1L)).thenReturn(Optional.of(parteExemplo));
        when(parteRepository.save(any(Parte.class))).thenReturn(parteExemplo);

        Parte parteAtualizada = parteService.atualizarParte(1L, parteExemplo);

        assertNotNull(parteAtualizada);
        assertEquals(parteExemplo.getNome(), parteAtualizada.getNome());
        verify(parteRepository, times(1)).save(any(Parte.class));
    }

    @Test
    void deveLancarExceptionAoAtualizarParteInexistente() {
        when(parteRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            parteService.atualizarParte(1L, parteExemplo);
        });
    }

    @Test
    void deveBuscarPartePorId() {
        when(parteRepository.findById(1L)).thenReturn(Optional.of(parteExemplo));

        Parte parteEncontrada = parteService.buscarPartePorId(1L);

        assertNotNull(parteEncontrada);
        assertEquals(parteExemplo.getNome(), parteEncontrada.getNome());
    }

    @Test
    void deveLancarExceptionAoBuscarParteInexistente() {
        when(parteRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            parteService.buscarPartePorId(1L);
        });
    }

    @Test
    void deveListarPartesPorProcesso() {
        when(processoRepository.findById(1L)).thenReturn(Optional.of(processoExemplo));
        when(parteRepository.findByProcessoId(1L)).thenReturn(Collections.singletonList(parteExemplo));

        processoExemplo.setPartes(Collections.singletonList(parteExemplo));

        var partes = parteService.listarPartesPorProcesso(1L);

        assertFalse(partes.isEmpty());
        assertEquals(1, partes.size());
        assertEquals(parteExemplo, partes.get(0));
    }

    @Test
    void deveLancarExceptionAoListarPartesDeProcessoInexistente() {
        when(processoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            parteService.listarPartesPorProcesso(1L);
        });
    }

    @Test
    void deveRemoverParte() {
        when(parteRepository.findById(1L)).thenReturn(Optional.of(parteExemplo));

        parteService.removerParte(1L);

        verify(parteRepository, times(1)).delete(parteExemplo);
    }

    @Test
    void deveLancarExceptionAoRemoverParteInexistente() {
        when(parteRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            parteService.removerParte(1L);
        });
    }
}