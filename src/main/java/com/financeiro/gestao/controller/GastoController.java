package com.financeiro.gestao.controller;

import com.financeiro.gestao.model.Gasto;
import com.financeiro.gestao.service.GastoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/gastos")
public class GastoController {

    private final GastoService gastoService;

    @Autowired
    public GastoController(GastoService gastoService) {
        this.gastoService = gastoService;
    }

    @GetMapping
    public ResponseEntity<List<Gasto>> findAll() {
        List<Gasto> gastos = gastoService.findAll();
        return ResponseEntity.ok(gastos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Gasto> findById(@PathVariable Long id) {
        return gastoService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/pessoa/{pessoaId}")
    public ResponseEntity<List<Gasto>> findByPessoaId(@PathVariable Long pessoaId) {
        List<Gasto> gastos = gastoService.findByPessoaId(pessoaId);
        return ResponseEntity.ok(gastos);
    }

    @GetMapping("/data")
    public ResponseEntity<List<Gasto>> findByDataBetween(@RequestParam Date inicio, @RequestParam Date fim) {
        List<Gasto> gastos = gastoService.findByDataBetween(inicio, fim);
        return ResponseEntity.ok(gastos);
    }

    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<List<Gasto>> findByCategoriaId(@PathVariable Long categoriaId) {
        List<Gasto> gastos = gastoService.findByCategoriaId(categoriaId);
        return ResponseEntity.ok(gastos);
    }

    @GetMapping("/descricao")
    public ResponseEntity<List<Gasto>> findByDescricaoContaining(@RequestParam String descricao) {
        List<Gasto> gastos = gastoService.findByDescricaoContaining(descricao);
        return ResponseEntity.ok(gastos);
    }

    @GetMapping("/valor")
    public ResponseEntity<List<Gasto>> findByValorGreaterThan(@RequestParam float valor) {
        List<Gasto> gastos = gastoService.findByValorGreaterThan(valor);
        return ResponseEntity.ok(gastos);
    }

    @GetMapping("/pessoa/{pessoaId}/categoria/{categoriaId}")
    public ResponseEntity<List<Gasto>> findByPessoaIdAndCategoriaId(@PathVariable Long pessoaId, @PathVariable Long categoriaId) {
        List<Gasto> gastos = gastoService.findByPessoaIdAndCategoriaId(pessoaId, categoriaId);
        return ResponseEntity.ok(gastos);
    }

    @PostMapping
    public ResponseEntity<Gasto> save(@RequestBody Gasto gasto) {
        Gasto savedGasto = gastoService.save(gasto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedGasto);
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Gasto> update(@PathVariable Long id, @RequestBody Gasto gastoAtualizado) {
        Gasto updatedGasto = gastoService.update(id, gastoAtualizado);
        return ResponseEntity.ok(updatedGasto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        gastoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
