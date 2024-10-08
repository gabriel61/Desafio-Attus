package com.attus.processojuridico.domain;

import com.attus.processojuridico.model.Acao;
import com.attus.processojuridico.model.TipoAcao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class AcaoTest {

    private Validator validator;

    @BeforeEach
    void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void deveValidarAcaoComDadosCorretos() {
        Acao acao = new Acao();
        acao.setTipo(TipoAcao.PETICAO);
        acao.setDescricao("Descrição válida");
        acao.setDataRegistro(LocalDate.now());

        var violations = validator.validate(acao);
        assertTrue(violations.isEmpty());
    }

    @Test
    void deveInvalidarAcaoSemDescricao() {
        Acao acao = new Acao();
        acao.setTipo(TipoAcao.PETICAO);
        acao.setDataRegistro(LocalDate.now());

        var violations = validator.validate(acao);
        assertFalse(violations.isEmpty());
    }

    @Test
    void deveInvalidarAcaoSemDataRegistro() {
        Acao acao = new Acao();
        acao.setTipo(TipoAcao.PETICAO);
        acao.setDescricao("Descrição válida");

        var violations = validator.validate(acao);
        assertFalse(violations.isEmpty());
    }
}