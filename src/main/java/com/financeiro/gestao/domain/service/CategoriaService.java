package com.financeiro.gestao.domain.service;

import com.financeiro.gestao.domain.model.Categoria;
import com.financeiro.gestao.domain.model.enums.TipoCategoria;
import com.financeiro.gestao.domain.repository.CategoriaRepository;
import com.financeiro.gestao.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public Categoria findById(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada com o ID: ", id));
    }

    public List<Categoria> findAll() {
        return categoriaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Categoria> findByNome(String nome) {
        Categoria categoria = categoriaRepository.findByNome(nome);
        return Optional.of(categoria);
    }

    public List<Categoria> findByNomeContaining(String nome) {
        return categoriaRepository.findByNomeContaining(nome);
    }

    public List<Categoria> findCategoriasByTipoCategoria(TipoCategoria tipo) {
        return categoriaRepository.findCategoriasByTipoCategoria(tipo);
    }

    public Page<Categoria> buscarCategoriasPorTipoPaginado(TipoCategoria tipoCategoria, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("nome").ascending());
        return categoriaRepository.findCategoriasByTipoCategoria(tipoCategoria, pageable);
    }


    @Transactional
    public Categoria createCategoria(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    @Transactional
    public Categoria updateCategoria(Long id, Categoria categoria) {
        Categoria existingCategoria = findById(id);
        existingCategoria.setNome(categoria.getNome());
        existingCategoria.setTipoCategoria(categoria.getTipoCategoria());
        return categoriaRepository.save(existingCategoria);
    }

    @Transactional
    public void deleteCategoria(Long id) {
        Categoria categoria = findById(id);
        categoriaRepository.delete(categoria);
    }
}
