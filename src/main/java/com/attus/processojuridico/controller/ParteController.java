package com.attus.processojuridico.controller;

import com.attus.processojuridico.exception.NotFoundException;
import com.attus.processojuridico.model.Parte;
import com.attus.processojuridico.service.ParteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/processos/{processoId}/partes")
@Tag(name = "Partes", description = "Operações relacionadas as partes de um processo jurídico")
public class ParteController {

    private final ParteService parteService;

    @Autowired
    public ParteController(ParteService parteService) {
        this.parteService = parteService;
    }

    @Operation(summary = "Criar parte", description = "Cria uma nova parte para um processo")
    @ApiResponse(responseCode = "201", description = "Parte criada com sucesso",
            content = @Content(schema = @Schema(implementation = Parte.class)))
    @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    @ApiResponse(responseCode = "404", description = "Processo não encontrado")
    @PostMapping
    public ResponseEntity<Parte> criarParte(@Parameter(description = "ID do processo", required = true)
                                                @PathVariable Long processoId, @Valid @RequestBody Parte parte) {
        Parte novaParte = parteService.criarParte(processoId, parte);
        return new ResponseEntity<>(novaParte, HttpStatus.CREATED);
    }

    @Operation(summary = "Atualizar parte", description = "Atualiza uma parte existente")
    @ApiResponse(responseCode = "200", description = "Parte atualizada com sucesso",
            content = @Content(schema = @Schema(implementation = Parte.class)))
    @ApiResponse(responseCode = "404", description = "Parte não encontrada")
    @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    @PutMapping("/{id}")
    public ResponseEntity<Parte> atualizarParte(@Parameter(description = "ID da parte", required = true)
                                                    @PathVariable Long id, @Valid @RequestBody Parte parte) {
        Parte parteAtualizada = parteService.atualizarParte(id, parte);
        return ResponseEntity.ok(parteAtualizada);
    }

    @Operation(summary = "Buscar parte", description = "Busca uma parte pelo ID")
    @ApiResponse(responseCode = "200", description = "Parte encontrada",
            content = @Content(schema = @Schema(implementation = Parte.class)))
    @ApiResponse(responseCode = "404", description = "Parte não encontrada")
    @GetMapping("/{id}")
    public ResponseEntity<Object> buscarParte(
            @Parameter(description = "ID do processo", required = true) @PathVariable Long processoId,
            @Parameter(description = "ID da parte", required = true) @PathVariable Long id) {
        try {
            Parte parte = parteService.buscarPartePorIdEProcesso(id, processoId);
            return ResponseEntity.ok(parte);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @Operation(summary = "Listar partes", description = "Lista todas as partes de um processo")
    @ApiResponse(responseCode = "200", description = "Partes encontradas",
            content = @Content(schema = @Schema(implementation = Parte.class)))
    @ApiResponse(responseCode = "404", description = "Processo não encontrado")
    @GetMapping
    public ResponseEntity<List<Parte>> listarPartes(@Parameter(description = "ID do processo", required = true)
                                                    @PathVariable Long processoId) {
        List<Parte> partes = parteService.listarPartesPorProcesso(processoId);
        return ResponseEntity.ok(partes);
    }

    @Operation(summary = "Remover parte", description = "Remove uma parte existente")
    @ApiResponse(responseCode = "204", description = "Parte removida com sucesso")
    @ApiResponse(responseCode = "404", description = "Parte não encontrada")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> removerParte(@Parameter(description = "ID do processo", required = true)
                                               @PathVariable Long processoId,
                                               @Parameter(description = "ID da parte", required = true)
                                               @PathVariable Long id) {
        boolean isRemoved = parteService.removerParte(processoId, id);
        if (!isRemoved) {
            throw new NotFoundException("Parte não encontrada");
        }
        return ResponseEntity.ok("Parte removida com sucesso");
    }
}