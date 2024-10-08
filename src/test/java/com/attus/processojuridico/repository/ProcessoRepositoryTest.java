package com.attus.processojuridico.repository;

import com.attus.processojuridico.model.Processo;
import com.attus.processojuridico.model.StatusProcesso;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class ProcessoRepositoryTest {

    @Autowired
    private ProcessoRepository processoRepository;

    private Processo processoExemplo;

    @BeforeEach
    void configurar() {
        processoRepository.deleteAll();

        processoExemplo = new Processo();
        processoExemplo.setNumero("12345");
        processoExemplo.setDescricao("Processo de Teste");
        processoExemplo.setDataAbertura(LocalDate.now());
        processoExemplo.setStatus(StatusProcesso.ATIVO);
    }

    @Test
    void devePermitirSalvarProcesso() {
        Processo processoSalvo = processoRepository.save(processoExemplo);

        assertNotNull(processoSalvo.getId());
        assertEquals("12345", processoSalvo.getNumero());
        assertEquals(StatusProcesso.ATIVO, processoSalvo.getStatus());
    }

    @Test
    void deveBuscarProcessoPorId() {
        Processo processoSalvo = processoRepository.save(processoExemplo);

        Optional<Processo> processoEncontrado = processoRepository.findById(processoSalvo.getId());

        assertTrue(processoEncontrado.isPresent());
        assertEquals("12345", processoEncontrado.get().getNumero());
    }

    @Test
    void deveRetornarVazioAoBuscarProcessoInexistente() {
        Optional<Processo> processoEncontrado = processoRepository.findById(999L);
        assertFalse(processoEncontrado.isPresent());
    }

    @Test
    void deveListarProcessosPaginados() {
        for (int i = 0; i < 10; i++) {
            Processo processo = new Processo();
            processo.setNumero("1234" + i);
            processo.setDescricao("Teste " + i);
            processo.setDataAbertura(LocalDate.now());
            processo.setStatus(StatusProcesso.ATIVO);
            processoRepository.save(processo);
        }

        Page<Processo> paginaProcessos = processoRepository.findAll(PageRequest.of(0, 5));

        assertEquals(5, paginaProcessos.getContent().size());
        assertEquals(10, paginaProcessos.getTotalElements());
    }

    @Test
    void deveRetornarPaginaVaziaQuandoNaoHaProcessos() {
        Page<Processo> paginaProcessos = processoRepository.findAll(PageRequest.of(0, 5));
        assertTrue(paginaProcessos.getContent().isEmpty());
    }

    @Test
    void deveBuscarProcessosPorStatus() {
        processoRepository.save(processoExemplo);

        Processo processoArquivado = new Processo();
        processoArquivado.setNumero("67890");
        processoArquivado.setDescricao("Processo Arquivado");
        processoArquivado.setDataAbertura(LocalDate.now());
        processoArquivado.setStatus(StatusProcesso.ARQUIVADO);
        processoRepository.save(processoArquivado);

        List<Processo> processosAtivos = processoRepository.findByStatus(StatusProcesso.ATIVO);

        assertEquals(1, processosAtivos.size());
        assertEquals("12345", processosAtivos.get(0).getNumero());
    }

    @Test
    void deveBuscarProcessosPorPeriodoDeAbertura() {
        processoExemplo.setDataAbertura(LocalDate.of(2024, 1, 1));
        processoRepository.save(processoExemplo);

        Processo processoRecente = new Processo();
        processoRecente.setNumero("67890");
        processoRecente.setDescricao("Processo Recente");
        processoRecente.setDataAbertura(LocalDate.of(2024, 3, 1));
        processoRecente.setStatus(StatusProcesso.SUSPENSO);
        processoRepository.save(processoRecente);

        List<Processo> processos = processoRepository.findByDataAberturaBetween(
                LocalDate.of(2023, 12, 1), LocalDate.of(2024, 2, 1));

        assertEquals(1, processos.size());
        assertEquals("12345", processos.get(0).getNumero());
    }

    @Test
    void deveBuscarProcessoPorNumero() {
        processoRepository.save(processoExemplo);

        Processo processoEncontrado = processoRepository.findByNumero("12345");

        assertNotNull(processoEncontrado);
        assertEquals("12345", processoEncontrado.getNumero());
    }

    @Test
    void deveRetornarNuloAoBuscarProcessoPorNumeroInexistente() {
        Processo processoEncontrado = processoRepository.findByNumero("99999");
        assertNull(processoEncontrado);
    }
}