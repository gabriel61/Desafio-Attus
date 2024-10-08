package com.attus.processojuridico.controller;

import com.attus.processojuridico.exception.NotFoundException;
import com.attus.processojuridico.model.Processo;
import com.attus.processojuridico.service.ProcessoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/processos")
@Tag(name = "Processos", description = "Operações relacionadas a processos jurídicos")
public class ProcessoController {

    @Autowired
    private ProcessoService processoService;

    @Operation(summary = "Criar processo", description = "Cria um novo processo jurídico")
    @ApiResponse(responseCode = "201", description = "Processo criado com sucesso",
            content = @Content(schema = @Schema(implementation = Processo.class)))
    @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    @PostMapping
    public ResponseEntity<Processo> criarProcesso(@Valid @RequestBody Processo processo) {
        Processo novoProcesso = processoService.criarProcesso(processo);
        return new ResponseEntity<>(novoProcesso, HttpStatus.CREATED);
    }

    @Operation(summary = "Atualizar processo", description = "Atualiza um processo existente")
    @ApiResponse(responseCode = "200", description = "Processo atualizado com sucesso",
            content = @Content(schema = @Schema(implementation = Processo.class)))
    @ApiResponse(responseCode = "404", description = "Processo não encontrado")
    @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    @PutMapping("/{id}")
    public ResponseEntity<Processo> atualizarProcesso(@Parameter(description = "ID do processo", required = true)
                                                          @PathVariable Long id, @Valid @RequestBody Processo processo) {
        Processo processoAtualizado = processoService.atualizarProcesso(id, processo);
        return ResponseEntity.ok(processoAtualizado);
    }

    @Operation(summary = "Buscar processo", description = "Busca um processo pelo ID")
    @ApiResponse(responseCode = "200", description = "Processo encontrado",
            content = @Content(schema = @Schema(implementation = Processo.class)))
    @ApiResponse(responseCode = "404", description = "Processo não encontrado")
    @GetMapping("/{id}")
    public ResponseEntity<Processo> buscarProcesso(@Parameter(description = "ID do processo", required = true)
                                                       @PathVariable Long id) {
        Optional<Processo> processoOpt = processoService.buscarProcessoPorId(id);
        return processoOpt.map(ResponseEntity::ok)
                .orElseThrow(() -> new NotFoundException("Processo não encontrado"));
    }

    @Operation(summary = "Listar processos", description = "Retorna uma lista paginada de processos")
    @ApiResponse(responseCode = "200", description = "Processos encontrados com sucesso",
            content = @Content(schema = @Schema(implementation = Page.class)))
    @GetMapping
    public ResponseEntity<Page<Processo>> listarProcessos(
            @Parameter(description = "Número da página (começando em 0)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamanho da página") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Campo para ordenação") @RequestParam(defaultValue = "id") String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        Page<Processo> processos = processoService.listarProcessos(pageable);
        return ResponseEntity.ok(processos);
    }

    @Operation(summary = "Arquivar processo", description = "Arquiva um processo existente")
    @ApiResponse(responseCode = "204", description = "Processo arquivado com sucesso")
    @ApiResponse(responseCode = "404", description = "Processo não encontrado")
    @PatchMapping("/{id}/arquivar")
    public ResponseEntity<Void> arquivarProcesso(@Parameter(description = "ID do processo", required = true)
                                                     @PathVariable Long id) {
        processoService.arquivarProcesso(id);
        return ResponseEntity.noContent().build();
    }
}