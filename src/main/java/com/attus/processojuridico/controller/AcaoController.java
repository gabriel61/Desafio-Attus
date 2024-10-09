package com.attus.processojuridico.controller;

import com.attus.processojuridico.exception.NotFoundException;
import com.attus.processojuridico.model.Acao;
import com.attus.processojuridico.service.AcaoService;
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
@RequestMapping("/api/processos/{processoId}/acoes")
@Tag(name = "Ações", description = "Operações relacionadas as ações de um processo jurídico")
public class AcaoController {

    private final AcaoService acaoService;

    @Autowired
    public AcaoController(AcaoService acaoService) {
        this.acaoService = acaoService;
    }

    @Operation(summary = "Registrar ação", description = "Registra uma nova ação para um processo")
    @ApiResponse(responseCode = "201", description = "Ação registrada com sucesso",
            content = @Content(schema = @Schema(implementation = Acao.class)))
    @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    @ApiResponse(responseCode = "404", description = "Processo não encontrado")
    @PostMapping
    public ResponseEntity<Acao> registrarAcao(@Parameter(description = "ID do processo", required = true)
                                                  @PathVariable Long processoId, @Valid @RequestBody Acao acao) {
        Acao novaAcao = acaoService.registrarAcao(processoId, acao);
        return new ResponseEntity<>(novaAcao, HttpStatus.CREATED);
    }

    @Operation(summary = "Atualizar ação", description = "Atualiza uma ação existente")
    @ApiResponse(responseCode = "200", description = "Ação atualizada com sucesso",
            content = @Content(schema = @Schema(implementation = Acao.class)))
    @ApiResponse(responseCode = "404", description = "Ação não encontrada")
    @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    @PutMapping("/{id}")
    public ResponseEntity<Acao> atualizarAcao(@Parameter(description = "ID da ação", required = true)
                                                  @PathVariable Long id, @Valid @RequestBody Acao acao) {
        Acao acaoAtualizada = acaoService.atualizarAcao(id, acao);
        return ResponseEntity.ok(acaoAtualizada);
    }

    @Operation(summary = "Buscar ação", description = "Busca uma ação pelo ID")
    @ApiResponse(responseCode = "200", description = "Ação encontrada",
            content = @Content(schema = @Schema(implementation = Acao.class)))
    @ApiResponse(responseCode = "404", description = "Ação não encontrada")
    @GetMapping("/{id}")
    public ResponseEntity<Acao> buscarAcao(@Parameter(description = "ID da ação", required = true)
                                               @PathVariable Long id) {
        Acao acao = acaoService.buscarAcaoPorId(id);
        return ResponseEntity.ok(acao);
    }

    @Operation(summary = "Listar ações", description = "Lista todas as ações de um processo")
    @ApiResponse(responseCode = "200", description = "Ações encontradas",
            content = @Content(schema = @Schema(implementation = List.class)))
    @GetMapping
    public ResponseEntity<List<Acao>> listarAcoes(@Parameter(description = "ID do processo", required = true)
                                                      @PathVariable Long processoId) {
        List<Acao> acoes = acaoService.listarAcoesPorProcesso(processoId);
        return ResponseEntity.ok(acoes);
    }

    @Operation(summary = "Remover ação", description = "Remove uma ação existente")
    @ApiResponse(responseCode = "200", description = "Ação removida com sucesso")
    @ApiResponse(responseCode = "404", description = "Ação não encontrada")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> removerAcao(@Parameter(description = "ID da ação", required = true)
                                              @PathVariable Long id) {
        try {
            boolean isRemoved = acaoService.removerAcao(id);
            if (isRemoved) {
                return ResponseEntity.ok("Ação removida com sucesso");
            }
            throw new NotFoundException("Ação não encontrada");
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao remover a ação");
        }
    }
}