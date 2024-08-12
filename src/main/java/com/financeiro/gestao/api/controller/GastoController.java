package com.financeiro.gestao.api.controller;

import com.financeiro.gestao.api.dto.GastoDTO;
import com.financeiro.gestao.domain.model.Categoria;
import com.financeiro.gestao.domain.model.Gasto;
import com.financeiro.gestao.domain.model.Pessoa;
import com.financeiro.gestao.domain.service.GastoService;
import com.financeiro.gestao.domain.service.LucroGastoService;
import com.financeiro.gestao.util.EntityToDTOConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/gastos")
public class GastoController {

    private final GastoService gastoService;
    private final LucroGastoService lucroGastoService;

    @Autowired
    public GastoController(GastoService gastoService, LucroGastoService lucroGastoService) {
        this.gastoService = gastoService;
        this.lucroGastoService = lucroGastoService;
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
    public ResponseEntity<List<GastoDTO>> findByPessoaId(@PathVariable Pessoa pessoa) {
        List<GastoDTO> gastos = gastoService.findByPessoa(pessoa);
        return ResponseEntity.ok(gastos);
    }

    @GetMapping("/data")
    public ResponseEntity<List<GastoDTO>> findByDataBetween(@RequestParam LocalDate inicio, @RequestParam LocalDate fim) {
        List<GastoDTO> gastos = gastoService.findByDataBetween(inicio, fim);
        return ResponseEntity.ok(gastos);
    }

    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<List<GastoDTO>> findByCategoriaId(@PathVariable Categoria categoria) {
        List<GastoDTO> gastos = gastoService.findByCategoria(categoria);
        return ResponseEntity.ok(gastos);
    }

    @GetMapping("/descricao")
    public ResponseEntity<List<GastoDTO>> findByDescricaoContaining(@RequestParam String descricao) {
        List<GastoDTO> gastos = gastoService.findByDescricaoContaining(descricao);
        return ResponseEntity.ok(gastos);
    }

    @GetMapping("/valor")
    public ResponseEntity<List<GastoDTO>> findByValorGreaterThan(@RequestParam BigDecimal valor) {
        List<GastoDTO> gastos = gastoService.findByValorGreaterThan(valor);
        return ResponseEntity.ok(gastos);
    }

    @GetMapping("/pessoa/{pessoaId}/categoria/{categoriaId}")
    public ResponseEntity<List<GastoDTO>> findByPessoaIdAndCategoriaId(@PathVariable Pessoa pessoa, @PathVariable Categoria categoria) {
        List<GastoDTO> gastos = gastoService.findByPessoaAndCategoria(pessoa, categoria);
        return ResponseEntity.ok(gastos);
    }

    @GetMapping("/data/after")
    public ResponseEntity<List<GastoDTO>> findByDataAfter(@RequestParam LocalDate data) {
        List<GastoDTO> gastos = gastoService.findByDataAfter(data);
        return ResponseEntity.ok(gastos);
    }

    @GetMapping("/data/before")
    public ResponseEntity<List<GastoDTO>> findByDataBefore(@RequestParam LocalDate data) {
        List<GastoDTO> gastos = gastoService.findByDataBefore(data);
        return ResponseEntity.ok(gastos);
    }

    @GetMapping("/sum")
    public ResponseEntity<BigDecimal> findSumValorByPessoaAndDataBetween(@RequestParam LocalDate inicio, @RequestParam LocalDate fim) {
        BigDecimal total = gastoService.findSumValorByPessoaAndDataBetween(inicio, fim);
        return ResponseEntity.ok(total);
    }

    @PostMapping("/register")
    public ResponseEntity<GastoDTO> save(@RequestBody Gasto gasto) {
        Gasto savedGasto = lucroGastoService.salvarGasto(gasto);
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
