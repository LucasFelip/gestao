package com.financeiro.gestao.api.controller;

import com.financeiro.gestao.domain.model.Categoria;
import com.financeiro.gestao.domain.model.enums.TipoCategoria;
import com.financeiro.gestao.domain.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/{id}")
    public ResponseEntity<Categoria> getCategoriaById(@PathVariable Long id) {
        Categoria categoria = categoriaService.findById(id);
        return ResponseEntity.ok(categoria);
    }

    @GetMapping
    public ResponseEntity<List<Categoria>> getAllCategorias() {
        List<Categoria> categorias = categoriaService.findAll();
        return ResponseEntity.ok(categorias);
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<Optional<Categoria>> getCategoriaByNome(@PathVariable String nome) {
        Optional<Categoria> categoria = categoriaService.findByNome(nome);
        return ResponseEntity.of(Optional.ofNullable(categoria));
    }

    @GetMapping("/nome/containing/{nome}")
    public ResponseEntity<List<Categoria>> getCategoriasByNomeContaining(@PathVariable String nome) {
        List<Categoria> categorias = categoriaService.findByNomeContaining(nome);
        return ResponseEntity.ok(categorias);
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<Categoria>> getCategoriasByTipo(@PathVariable TipoCategoria tipo) {
        List<Categoria> categorias = categoriaService.findCategoriasByTipoCategoria(tipo);
        return ResponseEntity.ok(categorias);
    }

    @GetMapping("/tipo/paginado")
    public ResponseEntity<Page<Categoria>> getCategoriasByTipoPaginado(
            @RequestParam("tipo") TipoCategoria tipo,
            @RequestParam("page") int page,
            @RequestParam("size") int size) {
        Page<Categoria> categorias = categoriaService.buscarCategoriasPorTipoPaginado(tipo, page, size);
        return ResponseEntity.ok(categorias);
    }

    @PostMapping
    public ResponseEntity<Categoria> createCategoria(@RequestBody Categoria categoria) {
        Categoria novaCategoria = categoriaService.createCategoria(categoria);
        return ResponseEntity.ok(novaCategoria);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Categoria> updateCategoria(
            @PathVariable Long id,
            @RequestBody Categoria categoria) {
        Categoria categoriaAtualizada = categoriaService.updateCategoria(id, categoria);
        return ResponseEntity.ok(categoriaAtualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategoria(@PathVariable Long id) {
        categoriaService.deleteCategoria(id);
        return ResponseEntity.noContent().build();
    }
}
