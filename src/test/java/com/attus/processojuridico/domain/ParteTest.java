package com.attus.processojuridico.domain;

import com.attus.processojuridico.model.Parte;
import com.attus.processojuridico.model.TipoParte;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ParteTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void deveValidarParteComDadosCorretos() {
        Parte parte = new Parte();
        parte.setNome("Parte Exemplo");
        parte.setCpfCnpj("12345678901");
        parte.setTipo(TipoParte.AUTOR);

        Set<ConstraintViolation<Parte>> violations = validator.validate(parte);

        assertTrue(violations.isEmpty(), "Não deve haver violações de constraint");
    }

    @Test
    void deveInvalidarParteSemNome() {
        Parte parte = new Parte();
        parte.setCpfCnpj("12345678901");
        parte.setTipo(TipoParte.AUTOR);

        Set<ConstraintViolation<Parte>> violations = validator.validate(parte);

        assertEquals(1, violations.size(), "Deve haver uma violação de constraint");
        assertEquals("O nome é obrigatório", violations.iterator().next().getMessage());
    }

    @Test
    void deveInvalidarParteSemCpfCnpj() {
        Parte parte = new Parte();
        parte.setNome("Parte Exemplo");
        parte.setTipo(TipoParte.AUTOR);

        Set<ConstraintViolation<Parte>> violations = validator.validate(parte);

        assertEquals(1, violations.size(), "Deve haver uma violação de constraint");
        assertEquals("O cpf/cnpj é obrigatório", violations.iterator().next().getMessage());
    }
}