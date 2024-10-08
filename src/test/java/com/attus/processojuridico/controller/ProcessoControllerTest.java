package com.attus.processojuridico.controller;

import com.attus.processojuridico.model.Processo;
import com.attus.processojuridico.model.StatusProcesso;
import com.attus.processojuridico.service.ProcessoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProcessoController.class)
class ProcessoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProcessoService processoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deveCriarProcesso() throws Exception {
        Processo processo = new Processo();
        processo.setNumero("12345");
        processo.setDescricao("Processo de teste");
        processo.setDataAbertura(LocalDate.now());
        processo.setStatus(StatusProcesso.ATIVO);

        when(processoService.criarProcesso(any(Processo.class))).thenReturn(processo);

        mockMvc.perform(post("/api/processos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(processo)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.numero").value("12345"));
    }

    @Test
    void deveBuscarProcesso() throws Exception {
        Processo processo = new Processo();
        processo.setId(1L);
        processo.setNumero("12345");
        processo.setDescricao("Processo de teste");
        processo.setDataAbertura(LocalDate.now());
        processo.setStatus(StatusProcesso.ATIVO);

        when(processoService.buscarProcessoPorId(1L)).thenReturn(Optional.of(processo));

        mockMvc.perform(get("/api/processos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numero").value("12345"));
    }

    @Test
    void deveAtualizarProcesso() throws Exception {
        Processo processo = new Processo();
        processo.setId(1L);
        processo.setNumero("12345");
        processo.setDescricao("Processo atualizado");
        processo.setDataAbertura(LocalDate.now());
        processo.setStatus(StatusProcesso.SUSPENSO);

        when(processoService.atualizarProcesso(any(Long.class), any(Processo.class))).thenReturn(processo);

        mockMvc.perform(put("/api/processos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(processo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.descricao").value("Processo atualizado"))
                .andExpect(jsonPath("$.status").value("SUSPENSO"));
    }

    @Test
    void deveArquivarProcesso() throws Exception {
        mockMvc.perform(patch("/api/processos/1/arquivar"))
                .andExpect(status().isNoContent());
    }
}