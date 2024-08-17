package com.financeiro.gestao.api.controller;

import com.financeiro.gestao.domain.model.PlanoOrcamentario;
import com.financeiro.gestao.domain.service.PlanoOrcamentarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/api/planos-orcamentarios")
public class PlanoOrcamentoController {

    @Autowired
    private PlanoOrcamentarioService planoOrcamentarioService;

    @GetMapping("/{id}")
    public ResponseEntity<PlanoOrcamentario> getPlanoOrcamentarioById(@PathVariable Long id) {
        PlanoOrcamentario planoOrcamentario = planoOrcamentarioService.findById(id);
        return ResponseEntity.ok(planoOrcamentario);
    }

    @GetMapping
    public ResponseEntity<List<PlanoOrcamentario>> getAllPlanosOrcamentariosByCurrentUser() {
        List<PlanoOrcamentario> planosOrcamentarios = planoOrcamentarioService.findAllByCurrentUser();
        return ResponseEntity.ok(planosOrcamentarios);
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<PlanoOrcamentario>> getPlanosOrcamentariosByNomeContaining(@PathVariable String nome) {
        List<PlanoOrcamentario> planosOrcamentarios = planoOrcamentarioService.findByNomeContaining(nome);
        return ResponseEntity.ok(planosOrcamentarios);
    }

    @GetMapping("/ativos")
    public ResponseEntity<List<PlanoOrcamentario>> getPlanosOrcamentariosAtivosByCurrentUser() {
        List<PlanoOrcamentario> planosOrcamentarios = planoOrcamentarioService.findByUsuarioAndAtivoTrue();
        return ResponseEntity.ok(planosOrcamentarios);
    }

    @GetMapping("/data-range")
    public ResponseEntity<List<PlanoOrcamentario>> getPlanosOrcamentariosByDateRange(
            @RequestParam("startDate") LocalDate startDate,
            @RequestParam("endDate") LocalDate endDate) {
        List<PlanoOrcamentario> planosOrcamentarios = planoOrcamentarioService.findByDateRange(startDate, endDate);
        return ResponseEntity.ok(planosOrcamentarios);
    }

    @GetMapping("/ativos/data-range")
    public ResponseEntity<List<PlanoOrcamentario>> getPlanosOrcamentariosAtivosByDateRange(
            @RequestParam("startDate") LocalDate startDate,
            @RequestParam("endDate") LocalDate endDate) {
        List<PlanoOrcamentario> planosOrcamentarios = planoOrcamentarioService.findActiveByDateRange(startDate, endDate);
        return ResponseEntity.ok(planosOrcamentarios);
    }

    @PostMapping
    public ResponseEntity<PlanoOrcamentario> createPlanoOrcamentario(@RequestBody PlanoOrcamentario planoOrcamentario) {
        PlanoOrcamentario novoPlanoOrcamentario = planoOrcamentarioService.createPlanoOrcamentario(planoOrcamentario);
        return ResponseEntity.ok(novoPlanoOrcamentario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlanoOrcamentario> updatePlanoOrcamentario(
            @PathVariable Long id,
            @RequestBody PlanoOrcamentario planoOrcamentario) {
        PlanoOrcamentario planoOrcamentarioAtualizado = planoOrcamentarioService.updatePlanoOrcamentario(id, planoOrcamentario);
        return ResponseEntity.ok(planoOrcamentarioAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlanoOrcamentario(@PathVariable Long id) {
        planoOrcamentarioService.deletePlanoOrcamentario(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/deactivate")
    public ResponseEntity<PlanoOrcamentario> deactivatePlanoOrcamentario(@PathVariable Long id) {
        PlanoOrcamentario planoOrcamentario = planoOrcamentarioService.deactivatePlanoOrcamentario(id);
        return ResponseEntity.ok(planoOrcamentario);
    }

    @PostMapping("/{id}/activate")
    public ResponseEntity<PlanoOrcamentario> activatePlanoOrcamentario(@PathVariable Long id) {
        PlanoOrcamentario planoOrcamentario = planoOrcamentarioService.activatePlanoOrcamentario(id);
        return ResponseEntity.ok(planoOrcamentario);
    }

    @GetMapping("/{id}/impacto-receita")
    public ResponseEntity<BigDecimal> calculaImpactoReceita(@PathVariable Long id) {
        BigDecimal impacto = planoOrcamentarioService.calculaImpactoReceita(id);
        return ResponseEntity.ok(impacto);
    }
}
