package com.financeiro.gestao.service;

import com.financeiro.gestao.model.Lucro;
import com.financeiro.gestao.repository.CategoriaRepository;
import com.financeiro.gestao.repository.LucroRepository;
import com.financeiro.gestao.exception.ResourceNotFoundException;
import com.financeiro.gestao.exception.BusinessRuleException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class LucroService {

    private final LucroRepository lucroRepository;
    private final CategoriaRepository categoriaRepository;

    @Autowired
    public LucroService(LucroRepository lucroRepository, CategoriaRepository categoriaRepository) {
        this.lucroRepository = lucroRepository;
        this.categoriaRepository = categoriaRepository;
    }

    @Transactional(readOnly = true)
    public List<Lucro> findAll() {
        return lucroRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Lucro> findById(Long id) {
        return Optional.ofNullable(lucroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lucro não encontrado com o ID: " + id)));
    }

    @Transactional(readOnly = true)
    public List<Lucro> findByPessoaId(Long pessoaId) {
        return lucroRepository.findByPessoaId(pessoaId);
    }

    @Transactional(readOnly = true)
    public List<Lucro> findByDataBetween(Date inicio, Date fim) {
        return lucroRepository.findByDataBetween(inicio, fim);
    }

    @Transactional(readOnly = true)
    public List<Lucro> findByCategoriaId(Long categoriaId) {
        return lucroRepository.findByCategoriaId(categoriaId);
    }

    @Transactional(readOnly = true)
    public List<Lucro> findByDescricaoContaining(String descricao) {
        return lucroRepository.findByDescricaoContaining(descricao);
    }

    @Transactional(readOnly = true)
    public List<Lucro> findByValorGreaterThan(float valor) {
        return lucroRepository.findByValorGreaterThan(valor);
    }

    @Transactional(readOnly = true)
    public List<Lucro> findByPessoaIdAndCategoriaId(Long pessoaId, Long categoriaId) {
        return lucroRepository.findByPessoaIdAndCategoriaId(pessoaId, categoriaId);
    }

    @Transactional
    public Lucro save(Lucro lucro) {
        validarLucro(lucro);
        return lucroRepository.save(lucro);
    }

    @Transactional
    public Lucro update(Long id, Lucro lucroAtualizado) {
        Lucro lucro = lucroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lucro não encontrado com o ID: " + id));

        lucro.setDescricao(lucroAtualizado.getDescricao());
        lucro.setValor(lucroAtualizado.getValor());
        lucro.setData(lucroAtualizado.getData());
        lucro.setCategoria(lucroAtualizado.getCategoria());
        lucro.setPessoa(lucroAtualizado.getPessoa());

        validarLucro(lucro);

        return lucroRepository.save(lucro);
    }

    @Transactional
    public void delete(Long id) {
        if (!lucroRepository.existsById(id)) {
            throw new ResourceNotFoundException("Lucro não encontrado com o ID: " + id);
        }
        lucroRepository.deleteById(id);
    }

    private void validarLucro(Lucro lucro) {
        if (lucro.getValor() <= 0) {
            throw new BusinessRuleException("O valor do lucro deve ser maior que zero.");
        }

        if (!categoriaRepository.existsById(lucro.getCategoria().getId())) {
            throw new BusinessRuleException("A categoria de lucro especificada não existe.");
        }

        if (lucro.getData().after(new Date())) {
            throw new BusinessRuleException("A data do lucro não pode ser uma data futura.");
        }

        if (lucro.getDescricao() == null || lucro.getDescricao().trim().isEmpty()) {
            throw new BusinessRuleException("A descrição do lucro não pode estar vazia.");
        }
    }
}
