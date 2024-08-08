package com.financeiro.gestao.api.controller;

import com.financeiro.gestao.api.dto.LucroDTO;
import com.financeiro.gestao.domain.model.Categoria;
import com.financeiro.gestao.domain.model.Lucro;
import com.financeiro.gestao.domain.model.Pessoa;
import com.financeiro.gestao.domain.service.LucroService;
import com.financeiro.gestao.util.EntityToDTOConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
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
    public ResponseEntity<List<LucroDTO>> findAll() {
        List<LucroDTO> lucros = lucroService.findAll();
        return ResponseEntity.ok(lucros);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LucroDTO> findById(@PathVariable Long id) {
        return lucroService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/pessoa/{pessoaId}")
    public ResponseEntity<List<LucroDTO>> findByPessoaId(@PathVariable Pessoa pessoa) {
        List<LucroDTO> lucros = lucroService.findByPessoa(pessoa);
        return ResponseEntity.ok(lucros);
    }

    @GetMapping("/data")
    public ResponseEntity<List<LucroDTO>> findByDataBetween(@RequestParam LocalDate inicio, @RequestParam LocalDate fim) {
        List<LucroDTO> lucros = lucroService.findByDataBetween(inicio, fim);
        return ResponseEntity.ok(lucros);
    }

    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<List<LucroDTO>> findByCategoriaId(@PathVariable Categoria categoria) {
        List<LucroDTO> lucros = lucroService.findByCategoria(categoria);
        return ResponseEntity.ok(lucros);
    }

    @GetMapping("/descricao")
    public ResponseEntity<List<LucroDTO>> findByDescricaoContaining(@RequestParam String descricao) {
        List<LucroDTO> lucros = lucroService.findByDescricaoContaining(descricao);
        return ResponseEntity.ok(lucros);
    }

    @GetMapping("/valor")
    public ResponseEntity<List<LucroDTO>> findByValorGreaterThan(@RequestParam BigDecimal valor) {
        List<LucroDTO> lucros = lucroService.findByValorGreaterThan(valor);
        return ResponseEntity.ok(lucros);
    }

    @GetMapping("/pessoa/{pessoaId}/categoria/{categoriaId}")
    public ResponseEntity<List<LucroDTO>> findByPessoaIdAndCategoriaId(@PathVariable Long pessoaId, @PathVariable Long categoriaId) {
        List<LucroDTO> lucros = lucroService.findByPessoaIdAndCategoriaId(pessoaId, categoriaId);
        return ResponseEntity.ok(lucros);
    }

    @GetMapping("/data/after")
    public ResponseEntity<List<LucroDTO>> findByDataAfter(@RequestParam LocalDate data) {
        List<LucroDTO> lucros = lucroService.findByDataAfter(data);
        return ResponseEntity.ok(lucros);
    }

    @GetMapping("/data/before")
    public ResponseEntity<List<LucroDTO>> findByDataBefore(@RequestParam LocalDate data) {
        List<LucroDTO> lucros = lucroService.findByDataBefore(data);
        return ResponseEntity.ok(lucros);
    }

    @GetMapping("/sum")
    public ResponseEntity<BigDecimal> sumByPessoaAndDataBetween(@RequestParam Pessoa pessoa, @RequestParam LocalDate inicio, @RequestParam LocalDate fim) {
        BigDecimal total = lucroService.sumByPessoaAndDataBetween(pessoa, inicio, fim);
        return ResponseEntity.ok(total);
    }

    @PostMapping
    public ResponseEntity<LucroDTO> save(@RequestBody Lucro lucro) {
        Lucro savedLucro = lucroService.save(lucro);
        LucroDTO lucroDTO = EntityToDTOConverter.convertToDTO(savedLucro);
        return ResponseEntity.status(HttpStatus.CREATED).body(lucroDTO);
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<LucroDTO> update(@PathVariable Long id, @RequestBody Lucro lucroAtualizado) {
        Lucro updatedLucro = lucroService.update(id, lucroAtualizado);
        LucroDTO lucroDTO = EntityToDTOConverter.convertToDTO(updatedLucro);
        return ResponseEntity.ok(lucroDTO);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        lucroService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
