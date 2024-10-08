package com.attus.processojuridico.repository;

import com.attus.processojuridico.model.Acao;
import com.attus.processojuridico.model.Processo;
import com.attus.processojuridico.model.TipoAcao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class AcaoRepositoryTest {

    @Autowired
    private AcaoRepository acaoRepository;

    @Autowired
    private ProcessoRepository processoRepository;

    private Processo processoExemplo;

    @BeforeEach
    void setup() {
        processoExemplo = new Processo();
        processoExemplo.setNumero("12345");
        processoExemplo.setDescricao("Descrição do Processo");
        processoExemplo.setDataAbertura(LocalDate.now());
        processoRepository.save(processoExemplo);
    }

    @Test
    void deveSalvarAcao() {
        Acao acao = new Acao();
        acao.setDescricao("Descrição da Ação");
        acao.setTipo(TipoAcao.PETICAO);
        acao.setDataRegistro(LocalDate.now());
        acao.setProcesso(processoExemplo);

        Acao acaoSalva = acaoRepository.save(acao);

        assertNotNull(acaoSalva.getId());
        assertEquals("Descrição da Ação", acaoSalva.getDescricao());
    }

    @Test
    void deveBuscarAcoesPorProcessoId() {
        Acao acao = new Acao();
        acao.setDescricao("Descrição da Ação");
        acao.setTipo(TipoAcao.PETICAO);
        acao.setDataRegistro(LocalDate.now());
        acao.setProcesso(processoExemplo);
        acaoRepository.save(acao);

        List<Acao> acoes = acaoRepository.findByProcessoId(processoExemplo.getId());

        assertEquals(1, acoes.size());
        assertEquals("Descrição da Ação", acoes.get(0).getDescricao());
    }

    @Test
    void deveRetornarListaVaziaAoBuscarPorProcessoInexistente() {
        List<Acao> acoes = acaoRepository.findByProcessoId(999L);
        assertTrue(acoes.isEmpty());
    }
}