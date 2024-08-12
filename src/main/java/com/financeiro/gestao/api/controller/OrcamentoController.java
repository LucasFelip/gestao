package com.financeiro.gestao.api.controller;

import com.financeiro.gestao.api.dto.OrcamentoDTO;
import com.financeiro.gestao.domain.model.Orcamento;
import com.financeiro.gestao.domain.model.Pessoa;
import com.financeiro.gestao.domain.service.OrcamentoService;
import com.financeiro.gestao.util.EntityToDTOConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/orcamentos")
public class OrcamentoController {

    private final OrcamentoService orcamentoService;

    @Autowired
    public OrcamentoController(OrcamentoService orcamentoService) {
        this.orcamentoService = orcamentoService;
    }

    @GetMapping
    public ResponseEntity<List<OrcamentoDTO>> findAll() {
        List<OrcamentoDTO> orcamentos = orcamentoService.findAll();
        return ResponseEntity.ok(orcamentos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrcamentoDTO> findById(@PathVariable Long id) {
        return orcamentoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/pessoa")
    public ResponseEntity<List<OrcamentoDTO>> findByPessoaId() {
        List<OrcamentoDTO> orcamentos = orcamentoService.findByPessoa();
        return ResponseEntity.ok(orcamentos);
    }

    @GetMapping("/data")
    public ResponseEntity<List<OrcamentoDTO>> findByDataInicioBetweenOrDataFimBetween(
            @RequestParam LocalDate inicioStart,
            @RequestParam LocalDate inicioEnd,
            @RequestParam LocalDate fimStart,
            @RequestParam LocalDate fimEnd) {
        List<OrcamentoDTO> orcamentos = orcamentoService.findByDataInicioBetweenOrDataFimBetween(inicioStart, inicioEnd, fimStart, fimEnd);
        return ResponseEntity.ok(orcamentos);
    }

    @GetMapping("/limite")
    public ResponseEntity<List<OrcamentoDTO>> findByLimiteGreaterThan(@RequestParam BigDecimal limite) {
        List<OrcamentoDTO> orcamentos = orcamentoService.findByLimiteGreaterThan(limite);
        return ResponseEntity.ok(orcamentos);
    }

    @GetMapping("/pessoa/{pessoaId}/data-fim")
    public ResponseEntity<List<OrcamentoDTO>> findByPessoaAndDataFimAfter(@PathVariable Pessoa pessoa, @RequestParam LocalDate hoje) {
        List<OrcamentoDTO> orcamentos = orcamentoService.findByPessoaAndDataFimAfter(pessoa, hoje);
        return ResponseEntity.ok(orcamentos);
    }

    @GetMapping("/pessoa/{pessoaId}/data-inicio-fim")
    public ResponseEntity<List<OrcamentoDTO>> findByPessoaAndDataInicioBeforeAndDataFimAfter(@PathVariable Pessoa pessoa, @RequestParam LocalDate inicio, @RequestParam LocalDate fim) {
        List<OrcamentoDTO> orcamentos = orcamentoService.findByPessoaAndDataInicioBeforeAndDataFimAfter(pessoa, inicio, fim);
        return ResponseEntity.ok(orcamentos);
    }

    @PostMapping("/register")
    public ResponseEntity<OrcamentoDTO> save(@RequestBody Orcamento orcamento) {
        Orcamento savedOrcamento = orcamentoService.save(orcamento);
        OrcamentoDTO orcamentoDTO = EntityToDTOConverter.convertToDTO(savedOrcamento);
        return ResponseEntity.status(HttpStatus.CREATED).body(orcamentoDTO);
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<OrcamentoDTO> update(@PathVariable Long id, @RequestBody Orcamento orcamentoAtualizado) {
        Orcamento updatedOrcamento = orcamentoService.update(id, orcamentoAtualizado);
        OrcamentoDTO orcamentoDTO = EntityToDTOConverter.convertToDTO(updatedOrcamento);
        return ResponseEntity.ok(orcamentoDTO);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        orcamentoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
