package com.attus.processojuridico.controller;

import com.attus.processojuridico.model.Processo;
import com.attus.processojuridico.service.ProcessoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/processos")
public class ProcessoController {

    @Autowired
    private ProcessoService processoService;

    @PostMapping
    public ResponseEntity<Processo> criarProcesso(@Valid @RequestBody Processo processo) {
        Processo novoProcesso = processoService.criarProcesso(processo);
        return new ResponseEntity<>(novoProcesso, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Processo> atualizarProcesso(@PathVariable Long id, @Valid @RequestBody Processo processo) {
        Processo processoAtualizado = processoService.atualizarProcesso(id, processo);
        return ResponseEntity.ok(processoAtualizado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Processo> buscarProcesso(@PathVariable Long id) {
        Processo processo = processoService.buscarProcessoPorId(id);
        return ResponseEntity.ok(processo);
    }

    @GetMapping
    public ResponseEntity<List<Processo>> listarProcessos() {
        List<Processo> processos = processoService.listarProcessos();
        return ResponseEntity.ok(processos);
    }

    @PatchMapping("/{id}/arquivar")
    public ResponseEntity<Void> arquivarProcesso(@PathVariable Long id) {
        processoService.arquivarProcesso(id);
        return ResponseEntity.noContent().build();
    }
}