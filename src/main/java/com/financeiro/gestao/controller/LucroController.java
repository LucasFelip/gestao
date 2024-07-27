package com.financeiro.gestao.controller;

import com.financeiro.gestao.model.Lucro;
import com.financeiro.gestao.service.LucroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/lucros")
public class LucroController {

    private final LucroService lucroService;

    @Autowired
    public LucroController(LucroService lucroService) {
        this.lucroService = lucroService;
    }

    @GetMapping
    public ResponseEntity<List<Lucro>> findAll() {
        List<Lucro> lucros = lucroService.findAll();
        return ResponseEntity.ok(lucros);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Lucro> findById(@PathVariable Long id) {
        return lucroService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/pessoa/{pessoaId}")
    public ResponseEntity<List<Lucro>> findByPessoaId(@PathVariable Long pessoaId) {
        List<Lucro> lucros = lucroService.findByPessoaId(pessoaId);
        return ResponseEntity.ok(lucros);
    }

    @GetMapping("/data")
    public ResponseEntity<List<Lucro>> findByDataBetween(@RequestParam Date inicio, @RequestParam Date fim) {
        List<Lucro> lucros = lucroService.findByDataBetween(inicio, fim);
        return ResponseEntity.ok(lucros);
    }

    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<List<Lucro>> findByCategoriaId(@PathVariable Long categoriaId) {
        List<Lucro> lucros = lucroService.findByCategoriaId(categoriaId);
        return ResponseEntity.ok(lucros);
    }

    @GetMapping("/descricao")
    public ResponseEntity<List<Lucro>> findByDescricaoContaining(@RequestParam String descricao) {
        List<Lucro> lucros = lucroService.findByDescricaoContaining(descricao);
        return ResponseEntity.ok(lucros);
    }

    @GetMapping("/valor")
    public ResponseEntity<List<Lucro>> findByValorGreaterThan(@RequestParam float valor) {
        List<Lucro> lucros = lucroService.findByValorGreaterThan(valor);
        return ResponseEntity.ok(lucros);
    }

    @GetMapping("/pessoa/{pessoaId}/categoria/{categoriaId}")
    public ResponseEntity<List<Lucro>> findByPessoaIdAndCategoriaId(@PathVariable Long pessoaId, @PathVariable Long categoriaId) {
        List<Lucro> lucros = lucroService.findByPessoaIdAndCategoriaId(pessoaId, categoriaId);
        return ResponseEntity.ok(lucros);
    }

    @PostMapping
    public ResponseEntity<Lucro> save(@RequestBody Lucro lucro) {
        Lucro savedLucro = lucroService.save(lucro);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedLucro);
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Lucro> update(@PathVariable Long id, @RequestBody Lucro lucroAtualizado) {
        Lucro updatedLucro = lucroService.update(id, lucroAtualizado);
        return ResponseEntity.ok(updatedLucro);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        lucroService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
