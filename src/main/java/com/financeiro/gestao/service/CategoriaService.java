package com.financeiro.gestao.service;

import com.financeiro.gestao.model.Categoria;
import com.financeiro.gestao.repository.CategoriaRepository;
import com.financeiro.gestao.exception.ResourceNotFoundException;
import com.financeiro.gestao.exception.BusinessRuleException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    @Autowired
    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @Transactional(readOnly = true)
    public List<Categoria> findAll() {
        return categoriaRepository.findAll();
    }

    @Transactional
    public Categoria save(Categoria categoria) {
        validarCategoria(categoria);
        return categoriaRepository.save(categoria);
    }

    private void validarCategoria(Categoria categoria) {
        if (categoria.getNome() == null || categoria.getNome().trim().isEmpty()) {
            throw new BusinessRuleException("O nome da categoria não pode estar vazio.");
        }

        // Verificar se o nome da categoria já existe
        if (categoriaRepository.existsByNome(categoria.getNome())) {
            throw new BusinessRuleException("Já existe uma categoria com esse nome.");
        }

        // Validação de caracteres especiais no nome
        if (!categoria.getNome().matches("[a-zA-Z\\s]+")) {
            throw new BusinessRuleException("O nome da categoria não deve conter caracteres especiais ou números.");
        }

        // Limitação do comprimento do nome e descrição
        if (categoria.getNome().length() > 50) {
            throw new BusinessRuleException("O nome da categoria não pode exceder 50 caracteres.");
        }
    }

    @Transactional(readOnly = true)
    public List<Categoria> buscarCategoria(String nomeCategoria) {
        if (nomeCategoria == null || nomeCategoria.trim().isEmpty()) {
            throw new BusinessRuleException("O nome da categoria para busca não pode estar vazio.");
        }

        List<Categoria> categorias = categoriaRepository.findByNomeContaining(nomeCategoria);
        if (categorias.isEmpty()) {
            throw new BusinessRuleException("Nenhuma categoria encontrada com o nome fornecido.");
        }

        return categorias;
    }
}
