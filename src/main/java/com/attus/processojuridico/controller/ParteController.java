package com.attus.processojuridico.controller;

import com.attus.processojuridico.model.Parte;
import com.attus.processojuridico.service.ParteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/processos/{processoId}/partes")
public class ParteController {

    @Autowired
    private ParteService parteService;

    @PostMapping
    public ResponseEntity<Parte> criarParte(@PathVariable Long processoId, @Valid @RequestBody Parte parte) {
        Parte novaParte = parteService.criarParte(processoId, parte);
        return new ResponseEntity<>(novaParte, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Parte> atualizarParte(@PathVariable Long id, @Valid @RequestBody Parte parte) {
        Parte parteAtualizada = parteService.atualizarParte(id, parte);
        return ResponseEntity.ok(parteAtualizada);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Parte> buscarParte(@PathVariable Long id) {
        Parte parte = parteService.buscarPartePorId(id);
        return ResponseEntity.ok(parte);
    }

    @GetMapping
    public ResponseEntity<List<Parte>> listarPartes(@PathVariable Long processoId) {
        List<Parte> partes = parteService.listarPartesPorProcesso(processoId);
        return ResponseEntity.ok(partes);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerParte(@PathVariable Long id) {
        parteService.removerParte(id);
        return ResponseEntity.noContent().build();
    }
}