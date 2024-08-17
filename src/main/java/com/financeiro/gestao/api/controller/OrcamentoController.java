package com.financeiro.gestao.api.controller;

import com.financeiro.gestao.domain.model.Orcamento;
import com.financeiro.gestao.domain.model.Categoria;
import com.financeiro.gestao.domain.service.OrcamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/orcamentos")
public class OrcamentoController {

    @Autowired
    private OrcamentoService orcamentoService;

    @GetMapping("/{id}")
    public ResponseEntity<Orcamento> getOrcamentoById(@PathVariable Long id) {
        Orcamento orcamento = orcamentoService.findById(id);
        return ResponseEntity.ok(orcamento);
    }

    @GetMapping("/plano/{planoOrcamentarioId}")
    public ResponseEntity<List<Orcamento>> getOrcamentosByPlanoOrcamentario(@PathVariable Long planoOrcamentarioId) {
        List<Orcamento> orcamentos = orcamentoService.findAllByPlanoOrcamentario(planoOrcamentarioId);
        return ResponseEntity.ok(orcamentos);
    }

    @GetMapping("/plano/{planoOrcamentarioId}/ativos")
    public ResponseEntity<List<Orcamento>> getOrcamentosAtivosByPlanoOrcamentario(@PathVariable Long planoOrcamentarioId) {
        List<Orcamento> orcamentos = orcamentoService.findByPlanoOrcamentarioAndAtivoTrue(planoOrcamentarioId);
        return ResponseEntity.ok(orcamentos);
    }

    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<List<Orcamento>> getOrcamentosByCategoria(@PathVariable Categoria categoria) {
        List<Orcamento> orcamentos = orcamentoService.findByCategoria(categoria);
        return ResponseEntity.ok(orcamentos);
    }

    @GetMapping("/categoria/{categoriaId}/ativos")
    public ResponseEntity<List<Orcamento>> getOrcamentosAtivosByCategoria(@PathVariable Categoria categoria) {
        List<Orcamento> orcamentos = orcamentoService.findByCategoriaAndAtivoTrue(categoria);
        return ResponseEntity.ok(orcamentos);
    }

    @PostMapping("/plano/{planoOrcamentarioId}")
    public ResponseEntity<Orcamento> createOrcamento(
            @PathVariable Long planoOrcamentarioId,
            @RequestBody Orcamento orcamento) {
        Orcamento novoOrcamento = orcamentoService.createOrcamento(planoOrcamentarioId, orcamento);
        return ResponseEntity.ok(novoOrcamento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Orcamento> updateOrcamento(
            @PathVariable Long id,
            @RequestBody Orcamento orcamento) {
        Orcamento orcamentoAtualizado = orcamentoService.updateOrcamento(id, orcamento);
        return ResponseEntity.ok(orcamentoAtualizado);
    }
}
