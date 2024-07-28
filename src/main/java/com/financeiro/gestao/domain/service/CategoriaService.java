package com.financeiro.gestao.domain.service;

import com.financeiro.gestao.api.dto.CategoriaDTO;
import com.financeiro.gestao.domain.exception.BusinessRuleException;
import com.financeiro.gestao.domain.exception.ResourceNotFoundException;
import com.financeiro.gestao.domain.model.Categoria;
import com.financeiro.gestao.domain.repository.CategoriaRepository;
import com.financeiro.gestao.util.EntityToDTOConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    @Autowired
    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @Transactional(readOnly = true)
    public List<CategoriaDTO> findAll() {
        List<Categoria> categorias = categoriaRepository.findAll();
        return categorias.stream()
                .map(EntityToDTOConverter::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<CategoriaDTO> findById(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada com o ID: " + id));
        return Optional.of(EntityToDTOConverter.convertToDTO(categoria));
    }

    @Transactional(readOnly = true)
    public Optional<CategoriaDTO> findByNome(String nome) {
        Categoria categoria = categoriaRepository.findByNome(nome)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada com o nome: " + nome));
        return Optional.of(EntityToDTOConverter.convertToDTO(categoria));
    }

    @Transactional(readOnly = true)
    public List<CategoriaDTO> findByNomeContaining(String nome) {
        List<Categoria> categorias = categoriaRepository.findByNomeContaining(nome);
        return categorias.stream()
                .map(EntityToDTOConverter::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public boolean existsByNome(String nome) {
        return categoriaRepository.existsByNome(nome);
    }

    @Transactional
    public Categoria save(Categoria categoria) {
        validarCategoria(categoria);
        return categoriaRepository.save(categoria);
    }

    @Transactional
    public Categoria update(Long id, Categoria categoriaAtualizada) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada com o ID: " + id));

        categoria.setNome(categoriaAtualizada.getNome());

        validarCategoria(categoria);

        return categoriaRepository.save(categoria);
    }

    @Transactional
    public void delete(Long id) {
        if (!categoriaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Categoria não encontrada com o ID: " + id);
        }
        categoriaRepository.deleteById(id);
    }

    private void validarCategoria(Categoria categoria) {
        if (categoria.getNome() == null || categoria.getNome().trim().isEmpty()) {
            throw new BusinessRuleException("O nome da categoria não pode estar vazio.");
        }

        if (categoriaRepository.existsByNome(categoria.getNome())) {
            throw new BusinessRuleException("Já existe uma categoria cadastrada com este nome.");
        }
    }
}
