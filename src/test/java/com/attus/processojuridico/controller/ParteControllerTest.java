package com.attus.processojuridico.controller;

import com.attus.processojuridico.model.Parte;
import com.attus.processojuridico.model.TipoParte;
import com.attus.processojuridico.service.ParteService;
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

class ParteControllerTest {

    @Mock
    private ParteService parteService;

    @InjectMocks
    private ParteController parteController;

    private Parte parteExemplo;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        parteExemplo = new Parte();
        parteExemplo.setId(1L);
        parteExemplo.setNome("Parte Exemplo");
        parteExemplo.setCpfCnpj("12345678901");
        parteExemplo.setTipo(TipoParte.AUTOR);
        parteExemplo.setEmail("parte@example.com");
        parteExemplo.setTelefone("123456789");
    }

    @Test
    void deveCriarParte() {
        when(parteService.criarParte(eq(1L), any(Parte.class))).thenReturn(parteExemplo);

        ResponseEntity<Parte> response = parteController.criarParte(1L, parteExemplo);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(parteExemplo, response.getBody());
        verify(parteService, times(1)).criarParte(eq(1L), any(Parte.class));
    }

    @Test
    void deveAtualizarParte() {
        when(parteService.atualizarParte(eq(1L), any(Parte.class))).thenReturn(parteExemplo);

        ResponseEntity<Parte> response = parteController.atualizarParte(1L, parteExemplo);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(parteExemplo, response.getBody());
        verify(parteService, times(1)).atualizarParte(eq(1L), any(Parte.class));
    }

    @Test
    void deveBuscarParte() {
        when(parteService.buscarPartePorId(1L)).thenReturn(parteExemplo);

        ResponseEntity<Parte> response = parteController.buscarParte(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(parteExemplo, response.getBody());
        verify(parteService, times(1)).buscarPartePorId(1L);
    }

    @Test
    void deveListarPartes() {
        when(parteService.listarPartesPorProcesso(1L)).thenReturn(Collections.singletonList(parteExemplo));

        ResponseEntity<List<Parte>> response = parteController.listarPartes(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(response.getBody().isEmpty());
        assertEquals(1, response.getBody().size());
        assertEquals(parteExemplo, response.getBody().get(0));
        verify(parteService, times(1)).listarPartesPorProcesso(1L);
    }

    @Test
    void deveRemoverParte() {
        ResponseEntity<Void> response = parteController.removerParte(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(parteService, times(1)).removerParte(1L);
    }
}