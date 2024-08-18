package com.financeiro.gestao.api.controller;

import com.financeiro.gestao.domain.model.Categoria;
import com.financeiro.gestao.domain.model.TransacaoFinanceira;
import com.financeiro.gestao.domain.service.TransacaoFinanceiraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/transacoes-financeiras")
public class TransacaoFinanceiraController {

    @Autowired
    private TransacaoFinanceiraService transacaoFinanceiraService;

    @GetMapping("/{id}")
    public ResponseEntity<TransacaoFinanceira> getTransacaoFinanceiraById(@PathVariable Long id) {
        TransacaoFinanceira transacaoFinanceira = transacaoFinanceiraService.findById(id);
        return ResponseEntity.ok(transacaoFinanceira);
    }

    @GetMapping
    public ResponseEntity<List<TransacaoFinanceira>> getAllTransacoesFinanceirasByCurrentUser() {
        List<TransacaoFinanceira> transacoesFinanceiras = transacaoFinanceiraService.findAllByCurrentUser();
        return ResponseEntity.ok(transacoesFinanceiras);
    }

    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<List<TransacaoFinanceira>> getTransacoesFinanceirasByCategoria(@PathVariable Categoria categoria) {
        List<TransacaoFinanceira> transacoesFinanceiras = transacaoFinanceiraService.findByCategoria(categoria);
        return ResponseEntity.ok(transacoesFinanceiras);
    }

    @GetMapping("/data-range")
    public ResponseEntity<List<TransacaoFinanceira>> getTransacoesFinanceirasByDateRange(
            @RequestParam("startDate") LocalDate startDate,
            @RequestParam("endDate") LocalDate endDate) {
        List<TransacaoFinanceira> transacoesFinanceiras = transacaoFinanceiraService.findByDateRange(startDate, endDate);
        return ResponseEntity.ok(transacoesFinanceiras);
    }

    @GetMapping("/categoria/{categoriaId}/soma")
    public ResponseEntity<BigDecimal> getSumByCategoriaAndDateRange(
            @PathVariable Categoria categoria,
            @RequestParam("startDate") LocalDate startDate,
            @RequestParam("endDate") LocalDate endDate) {
        BigDecimal soma = transacaoFinanceiraService.sumByCategoriaAndDateRange(categoria, startDate, endDate);
        return ResponseEntity.ok(soma);
    }

    @GetMapping("/paginado")
    public ResponseEntity<Page<TransacaoFinanceira>> getTransacoesFinanceirasPageable(
            @RequestParam("page") int page,
            @RequestParam("size") int size) {
        Page<TransacaoFinanceira> transacoesFinanceiras = transacaoFinanceiraService.findByUsuarioPageable(page, size);
        return ResponseEntity.ok(transacoesFinanceiras);
    }

    @GetMapping("/valor-maior-que")
    public ResponseEntity<List<TransacaoFinanceira>> getTransacoesComValorMaiorQue(
            @RequestParam("valor") BigDecimal valor) {
        List<TransacaoFinanceira> transacoesFinanceiras = transacaoFinanceiraService.findTransacaoComValorMaiorQue(valor);
        return ResponseEntity.ok(transacoesFinanceiras);
    }

    @PostMapping
    public ResponseEntity<TransacaoFinanceira> createTransacaoFinanceira(@RequestBody TransacaoFinanceira transacaoFinanceira) {
        TransacaoFinanceira novaTransacao = transacaoFinanceiraService.createTransacao(transacaoFinanceira);
        return ResponseEntity.ok(novaTransacao);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransacaoFinanceira> updateTransacaoFinanceira(
            @PathVariable Long id,
            @RequestBody TransacaoFinanceira transacaoFinanceira) {
        TransacaoFinanceira transacaoAtualizada = transacaoFinanceiraService.updateTransacao(transacaoFinanceira);
        return ResponseEntity.ok(transacaoAtualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransacaoFinanceira(@PathVariable Long id) {
        transacaoFinanceiraService.deleteTransacao(id);
        return ResponseEntity.noContent().build();
    }
}
