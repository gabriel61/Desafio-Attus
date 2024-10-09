package com.attus.processojuridico.repository;

import com.attus.processojuridico.model.Parte;
import com.attus.processojuridico.model.Processo;
import com.attus.processojuridico.model.TipoParte;
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
class ParteRepositoryTest {

    @Autowired
    private ParteRepository parteRepository;

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
    void deveSalvarParte() {
        Parte parte = new Parte();
        parte.setNome("Parte Exemplo");
        parte.setCpfCnpj("12345678901");
        parte.setTipo(TipoParte.AUTOR);
        parte.setProcesso(processoExemplo);

        Parte parteSalva = parteRepository.save(parte);

        assertNotNull(parteSalva.getId());
        assertEquals("Parte Exemplo", parteSalva.getNome());
    }

    @Test
    void deveBuscarPartePorProcessoId() {
        Parte parte = new Parte();
        parte.setNome("Parte Exemplo");
        parte.setCpfCnpj("12345678901");
        parte.setTipo(TipoParte.AUTOR);
        parte.setProcesso(processoExemplo);
        parteRepository.save(parte);

        List<Parte> partes = parteRepository.findByProcessoId(processoExemplo.getId());

        assertEquals(1, partes.size());
        assertEquals("Parte Exemplo", partes.get(0).getNome());
    }

    @Test
    void deveBuscarPartePorCpfCnpj() {
        Parte parte = new Parte();
        parte.setNome("Parte Exemplo");
        parte.setCpfCnpj("12345678901");
        parte.setTipo(TipoParte.AUTOR);
        parte.setProcesso(processoExemplo);
        parteRepository.save(parte);

        Parte parteEncontrada = parteRepository.findByCpfCnpj("12345678901");

        assertNotNull(parteEncontrada);
        assertEquals("Parte Exemplo", parteEncontrada.getNome());
    }

    @Test
    void deveRetornarNullAoBuscarParteComCpfCnpjInexistente() {
        Parte parte = parteRepository.findByCpfCnpj("00000000000");
        assertNull(parte);
    }

    @Test
    void deveVerificarExistenciaDePartePorCpfCnpj() {
        Parte parte = new Parte();
        parte.setNome("Parte Exemplo");
        parte.setCpfCnpj("12345678901");
        parte.setTipo(TipoParte.AUTOR);
        parte.setProcesso(processoExemplo);
        parteRepository.save(parte);

        assertTrue(parteRepository.existsByCpfCnpj("12345678901"));
        assertFalse(parteRepository.existsByCpfCnpj("00000000000"));
    }
}