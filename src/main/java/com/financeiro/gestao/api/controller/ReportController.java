package com.financeiro.gestao.api.controller;

import com.financeiro.gestao.api.dto.GastoDTO;
import com.financeiro.gestao.api.dto.LucroDTO;
import com.financeiro.gestao.domain.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/reports")
public class ReportController {

    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/gastos")
    public ResponseEntity<List<GastoDTO>> findGastosByPessoaAndPeriodo(
            @RequestParam LocalDate inicio,
            @RequestParam LocalDate fim) {
        List<GastoDTO> gastos = reportService.findGastosByPessoaAndPeriodo(inicio, fim);
        return ResponseEntity.ok(gastos);
    }

    @GetMapping("/lucros")
    public ResponseEntity<List<LucroDTO>> findLucrosByPessoaAndPeriodo(
            @RequestParam LocalDate inicio,
            @RequestParam LocalDate fim) {
        List<LucroDTO> lucros = reportService.findLucrosByPessoaAndPeriodo(inicio, fim);
        return ResponseEntity.ok(lucros);
    }

    @GetMapping("/saldo")
    public ResponseEntity<BigDecimal> calculateSaldoByPessoaAndPeriodo(
            @RequestParam LocalDate inicio,
            @RequestParam LocalDate fim) {
        BigDecimal saldo = reportService.calculateSaldoByPessoaAndPeriodo(inicio, fim);
        return ResponseEntity.ok(saldo);
    }

    @GetMapping("/gastos/ativo")
    public ResponseEntity<BigDecimal> calculateTotalGastosByOrcamentoAtivo() {
        BigDecimal totalGastos = reportService.calculateTotalGastosByOrcamentoAtivo();
        return ResponseEntity.ok(totalGastos);
    }

    @GetMapping("/lucros/ativo")
    public ResponseEntity<BigDecimal> calculateTotalLucrosByOrcamentoAtivo() {
        BigDecimal totalLucros = reportService.calculateTotalLucrosByOrcamentoAtivo();
        return ResponseEntity.ok(totalLucros);
    }

    @GetMapping("/saldo/ativo")
    public ResponseEntity<BigDecimal> calculateSaldoByOrcamentoAtivo() {
        BigDecimal saldo = reportService.calculateSaldoByOrcamentoAtivo();
        return ResponseEntity.ok(saldo);
    }
}
