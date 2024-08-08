package com.financeiro.gestao.api.controller;

import com.financeiro.gestao.api.dto.CategoriaDTO;
import com.financeiro.gestao.domain.model.Categoria;
import com.financeiro.gestao.domain.service.CategoriaService;
import com.financeiro.gestao.util.EntityToDTOConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    @Autowired
    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> findAll() {
        List<CategoriaDTO> categorias = categoriaService.findAll();
        return ResponseEntity.ok(categorias);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDTO> findById(@PathVariable Long id) {
        return categoriaService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/nome")
    public ResponseEntity<CategoriaDTO> findByNome(@RequestParam String nome) {
        return categoriaService.findByNome(nome)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/nome/containing")
    public ResponseEntity<List<CategoriaDTO>> findByNomeContaining(@RequestParam String nome) {
        List<CategoriaDTO> categorias = categoriaService.findByNomeContaining(nome);
        return ResponseEntity.ok(categorias);
    }

    @PostMapping
    public ResponseEntity<CategoriaDTO> save(@RequestBody Categoria categoria) {
        Categoria savedCategoria = categoriaService.save(categoria);
        CategoriaDTO categoriaDTO = EntityToDTOConverter.convertToDTO(savedCategoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaDTO);
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<CategoriaDTO> update(@PathVariable Long id, @RequestBody Categoria categoriaAtualizada) {
        Categoria updatedCategoria = categoriaService.update(id, categoriaAtualizada);
        CategoriaDTO categoriaDTO = EntityToDTOConverter.convertToDTO(updatedCategoria);
        return ResponseEntity.ok(categoriaDTO);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categoriaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
