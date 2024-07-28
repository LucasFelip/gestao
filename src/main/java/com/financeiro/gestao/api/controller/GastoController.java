package com.financeiro.gestao.api.controller;

import com.financeiro.gestao.api.dto.GastoDTO;
import com.financeiro.gestao.domain.model.Gasto;
import com.financeiro.gestao.domain.service.GastoService;
import com.financeiro.gestao.util.EntityToDTOConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/gastos")
public class GastoController {

    private final GastoService gastoService;

    @Autowired
    public GastoController(GastoService gastoService) {
        this.gastoService = gastoService;
    }

    @GetMapping
    public ResponseEntity<List<GastoDTO>> findAll() {
        List<GastoDTO> gastos = gastoService.findAll();
        return ResponseEntity.ok(gastos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GastoDTO> findById(@PathVariable Long id) {
        return gastoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/pessoa/{pessoaId}")
    public ResponseEntity<List<GastoDTO>> findByPessoaId(@PathVariable Long pessoaId) {
        List<GastoDTO> gastos = gastoService.findByPessoaId(pessoaId);
        return ResponseEntity.ok(gastos);
    }

    @GetMapping("/data")
    public ResponseEntity<List<GastoDTO>> findByDataBetween(@RequestParam Date inicio, @RequestParam Date fim) {
        List<GastoDTO> gastos = gastoService.findByDataBetween(inicio, fim);
        return ResponseEntity.ok(gastos);
    }

    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<List<GastoDTO>> findByCategoriaId(@PathVariable Long categoriaId) {
        List<GastoDTO> gastos = gastoService.findByCategoriaId(categoriaId);
        return ResponseEntity.ok(gastos);
    }

    @GetMapping("/descricao")
    public ResponseEntity<List<GastoDTO>> findByDescricaoContaining(@RequestParam String descricao) {
        List<GastoDTO> gastos = gastoService.findByDescricaoContaining(descricao);
        return ResponseEntity.ok(gastos);
    }

    @GetMapping("/valor")
    public ResponseEntity<List<GastoDTO>> findByValorGreaterThan(@RequestParam float valor) {
        List<GastoDTO> gastos = gastoService.findByValorGreaterThan(valor);
        return ResponseEntity.ok(gastos);
    }

    @GetMapping("/pessoa/{pessoaId}/categoria/{categoriaId}")
    public ResponseEntity<List<GastoDTO>> findByPessoaIdAndCategoriaId(@PathVariable Long pessoaId, @PathVariable Long categoriaId) {
        List<GastoDTO> gastos = gastoService.findByPessoaIdAndCategoriaId(pessoaId, categoriaId);
        return ResponseEntity.ok(gastos);
    }

    @PostMapping
    public ResponseEntity<GastoDTO> save(@RequestBody Gasto gasto) {
        Gasto savedGasto = gastoService.save(gasto);
        GastoDTO gastoDTO = EntityToDTOConverter.convertToDTO(savedGasto);
        return ResponseEntity.status(HttpStatus.CREATED).body(gastoDTO);
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<GastoDTO> update(@PathVariable Long id, @RequestBody Gasto gastoAtualizado) {
        Gasto updatedGasto = gastoService.update(id, gastoAtualizado);
        GastoDTO gastoDTO = EntityToDTOConverter.convertToDTO(updatedGasto);
        return ResponseEntity.ok(gastoDTO);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        gastoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
