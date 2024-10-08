package com.attus.processojuridico.controller;

import com.attus.processojuridico.model.Acao;
import com.attus.processojuridico.model.TipoAcao;
import com.attus.processojuridico.service.AcaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class AcaoControllerTest {

    @Mock
    private AcaoService acaoService;

    @InjectMocks
    private AcaoController acaoController;

    private Acao acaoExemplo;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        acaoExemplo = new Acao();
        acaoExemplo.setId(1L);
        acaoExemplo.setTipo(TipoAcao.PETICAO);
        acaoExemplo.setDescricao("Descrição da Ação");
        acaoExemplo.setDataRegistro(LocalDate.now());
    }

    @Test
    void deveRegistrarAcao() {
        when(acaoService.registrarAcao(eq(1L), any(Acao.class))).thenReturn(acaoExemplo);

        ResponseEntity<Acao> response = acaoController.registrarAcao(1L, acaoExemplo);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(acaoExemplo, response.getBody());
        verify(acaoService, times(1)).registrarAcao(eq(1L), any(Acao.class));
    }

    @Test
    void deveAtualizarAcao() {
        when(acaoService.atualizarAcao(eq(1L), any(Acao.class))).thenReturn(acaoExemplo);

        ResponseEntity<Acao> response = acaoController.atualizarAcao(1L, acaoExemplo);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(acaoExemplo, response.getBody());
        verify(acaoService, times(1)).atualizarAcao(eq(1L), any(Acao.class));
    }

    @Test
    void deveBuscarAcao() {
        when(acaoService.buscarAcaoPorId(1L)).thenReturn(acaoExemplo);

        ResponseEntity<Acao> response = acaoController.buscarAcao(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(acaoExemplo, response.getBody());
        verify(acaoService, times(1)).buscarAcaoPorId(1L);
    }

    @Test
    void deveListarAcoes() {
        when(acaoService.listarAcoesPorProcesso(1L)).thenReturn(Collections.singletonList(acaoExemplo));

        ResponseEntity<List<Acao>> response = acaoController.listarAcoes(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(response.getBody().isEmpty());
        assertEquals(1, response.getBody().size());
        assertEquals(acaoExemplo, response.getBody().get(0));
        verify(acaoService, times(1)).listarAcoesPorProcesso(1L);
    }

    @Test
    void deveRemoverAcao() {
        ResponseEntity<Void> response = acaoController.removerAcao(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(acaoService, times(1)).removerAcao(1L);
    }
}