package com.attus.processojuridico.controller;

import com.attus.processojuridico.model.Acao;
import com.attus.processojuridico.service.AcaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/processos/{processoId}/acoes")
public class AcaoController {

    @Autowired
    private AcaoService acaoService;

    @PostMapping
    public ResponseEntity<Acao> registrarAcao(@PathVariable Long processoId, @RequestBody Acao acao) {
        Acao novaAcao = acaoService.registrarAcao(processoId, acao);
        return new ResponseEntity<>(novaAcao, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Acao> atualizarAcao(@PathVariable Long id, @RequestBody Acao acao) {
        Acao acaoAtualizada = acaoService.atualizarAcao(id, acao);
        return ResponseEntity.ok(acaoAtualizada);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Acao> buscarAcao(@PathVariable Long id) {
        Acao acao = acaoService.buscarAcaoPorId(id);
        return ResponseEntity.ok(acao);
    }

    @GetMapping
    public ResponseEntity<List<Acao>> listarAcoes(@PathVariable Long processoId) {
        List<Acao> acoes = acaoService.listarAcoesPorProcesso(processoId);
        return ResponseEntity.ok(acoes);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerAcao(@PathVariable Long id) {
        acaoService.removerAcao(id);
        return ResponseEntity.noContent().build();
    }
}