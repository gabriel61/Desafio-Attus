package com.attus.processojuridico.domain;

import com.attus.processojuridico.model.Processo;
import com.attus.processojuridico.model.StatusProcesso;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ProcessoTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void deveValidarProcessoValido() {
        Processo processo = new Processo();
        processo.setNumero("12345");
        processo.setDescricao("Processo de teste");
        processo.setDataAbertura(LocalDate.now());
        processo.setStatus(StatusProcesso.ATIVO);

        var violations = validator.validate(processo);
        assertTrue(violations.isEmpty());
    }

    @Test
    void deveRejeitarProcessoSemNumero() {
        Processo processo = new Processo();
        processo.setDescricao("Processo de teste");
        processo.setDataAbertura(LocalDate.now());
        processo.setStatus(StatusProcesso.ATIVO);

        var violations = validator.validate(processo);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("O número do processo é obrigatório", violations.iterator().next().getMessage());
    }

    @Test
    void deveRejeitarProcessoSemDataAbertura() {
        Processo processo = new Processo();
        processo.setNumero("12345");
        processo.setDescricao("Processo de teste");
        processo.setStatus(StatusProcesso.ATIVO);

        var violations = validator.validate(processo);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("A data de abertura é obrigatória", violations.iterator().next().getMessage());
    }

    @Test
    void deveRejeitarProcessoSemDescricao() {
        Processo processo = new Processo();
        processo.setNumero("12345");
        processo.setDataAbertura(LocalDate.now());
        processo.setStatus(StatusProcesso.ATIVO);

        var violations = validator.validate(processo);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("A descrição é obrigatória", violations.iterator().next().getMessage());
    }
}