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

    @Transactional(readOnly = true)
    public Optional<Categoria> findById(Long id) {
        return Optional.ofNullable(categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada com o ID: " + id)));
    }

    @Transactional
    public Categoria save(Categoria categoria) {
        validarCategoria(categoria, null);
        return categoriaRepository.save(categoria);
    }

    @Transactional
    public Categoria update(Long id, Categoria categoriaAtualizada) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada com o ID: " + id));

        validarCategoria(categoriaAtualizada, id);

        categoria.setNome(categoriaAtualizada.getNome());
        categoria.setDescricao(categoriaAtualizada.getDescricao());

        return categoriaRepository.save(categoria);
    }

    @Transactional
    public void delete(Long id) {
        if (!categoriaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Categoria não encontrada com o ID: " + id);
        }
        categoriaRepository.deleteById(id);
    }

    private void validarCategoria(Categoria categoria, Long idAtualizacao) {
        if (categoria.getNome() == null || categoria.getNome().trim().isEmpty()) {
            throw new BusinessRuleException("O nome da categoria não pode estar vazio.");
        }

        // Verificar se o nome da categoria já existe
        Optional<Categoria> categoriaExistente = categoriaRepository.findByNome(categoria.getNome());
        if (categoriaExistente.isPresent() && (idAtualizacao == null || !categoriaExistente.get().getId().equals(idAtualizacao))) {
            throw new BusinessRuleException("Já existe uma categoria com esse nome.");
        }

        // Validação da descrição da categoria
        if (categoria.getDescricao() != null && categoria.getDescricao().trim().isEmpty()) {
            throw new BusinessRuleException("A descrição da categoria não pode estar vazia se fornecida.");
        }

        // Validação de caracteres especiais no nome
        if (!categoria.getNome().matches("[a-zA-Z\\s]+")) {
            throw new BusinessRuleException("O nome da categoria não deve conter caracteres especiais ou números.");
        }

        // Limitação do comprimento do nome e descrição
        if (categoria.getNome().length() > 50) {
            throw new BusinessRuleException("O nome da categoria não pode exceder 50 caracteres.");
        }
        if (categoria.getDescricao() != null && categoria.getDescricao().length() > 255) {
            throw new BusinessRuleException("A descrição da categoria não pode exceder 255 caracteres.");
        }
    }
}
