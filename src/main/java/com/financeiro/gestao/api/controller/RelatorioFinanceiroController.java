package com.financeiro.gestao.api.controller;

import com.financeiro.gestao.domain.model.Categoria;
import com.financeiro.gestao.domain.model.RelatorioFinanceiro;
import com.financeiro.gestao.domain.service.RelatorioFinanceiroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/api/relatorios-financeiros")
public class RelatorioFinanceiroController {

    @Autowired
    private RelatorioFinanceiroService relatorioFinanceiroService;

    @GetMapping("/{id}")
    public ResponseEntity<RelatorioFinanceiro> getRelatorioFinanceiroById(@PathVariable Long id) {
        RelatorioFinanceiro relatorioFinanceiro = relatorioFinanceiroService.findById(id);
        return ResponseEntity.ok(relatorioFinanceiro);
    }

    @GetMapping
    public ResponseEntity<List<RelatorioFinanceiro>> getAllRelatoriosFinanceirosByCurrentUser() {
        List<RelatorioFinanceiro> relatoriosFinanceiros = relatorioFinanceiroService.findAllByCurrentUser();
        return ResponseEntity.ok(relatoriosFinanceiros);
    }

    @GetMapping("/data-range")
    public ResponseEntity<List<RelatorioFinanceiro>> getRelatoriosFinanceirosByDateRange(
            @RequestParam("startDate") LocalDate startDate,
            @RequestParam("endDate") LocalDate endDate) {
        List<RelatorioFinanceiro> relatoriosFinanceiros = relatorioFinanceiroService.findByDateRange(startDate, endDate);
        return ResponseEntity.ok(relatoriosFinanceiros);
    }

    @GetMapping("/categoria/{categoriaId}/soma")
    public ResponseEntity<BigDecimal> calcularSomaPorCategoria(@PathVariable Categoria categoria) {
        BigDecimal soma = relatorioFinanceiroService.calcularSomaPorCategoria(categoria);
        return ResponseEntity.ok(soma);
    }

    @PostMapping
    public ResponseEntity<RelatorioFinanceiro> createRelatorioFinanceiro(@RequestBody RelatorioFinanceiro relatorioFinanceiro) {
        RelatorioFinanceiro novoRelatorio = relatorioFinanceiroService.createRelatorio(relatorioFinanceiro);
        return ResponseEntity.ok(novoRelatorio);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RelatorioFinanceiro> updateRelatorioFinanceiro(
            @PathVariable Long id,
            @RequestBody RelatorioFinanceiro relatorioFinanceiro) {
        RelatorioFinanceiro relatorioAtualizado = relatorioFinanceiroService.updateRelatorio(id, relatorioFinanceiro);
        return ResponseEntity.ok(relatorioAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRelatorioFinanceiro(@PathVariable Long id) {
        relatorioFinanceiroService.deleteRelatorio(id);
        return ResponseEntity.noContent().build();
    }
}
