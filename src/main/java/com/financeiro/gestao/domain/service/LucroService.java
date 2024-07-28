package com.financeiro.gestao.domain.service;

import com.financeiro.gestao.api.dto.LucroDTO;
import com.financeiro.gestao.domain.exception.BusinessRuleException;
import com.financeiro.gestao.domain.exception.ResourceNotFoundException;
import com.financeiro.gestao.domain.repository.CategoriaRepository;
import com.financeiro.gestao.domain.repository.LucroRepository;
import com.financeiro.gestao.domain.model.Lucro;
import com.financeiro.gestao.util.EntityToDTOConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public List<LucroDTO> findAll() {
        List<Lucro> lucros = lucroRepository.findAll();
        return lucros.stream()
                .map(EntityToDTOConverter::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<LucroDTO> findById(Long id) {
        Lucro lucro = lucroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lucro não encontrado com o ID: " + id));
        return Optional.of(EntityToDTOConverter.convertToDTO(lucro));
    }

    @Transactional(readOnly = true)
    public List<LucroDTO> findByPessoaId(Long pessoaId) {
        List<Lucro> lucros = lucroRepository.findByPessoaId(pessoaId);
        return lucros.stream()
                .map(EntityToDTOConverter::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<LucroDTO> findByDataBetween(Date inicio, Date fim) {
        List<Lucro> lucros = lucroRepository.findByDataBetween(inicio, fim);
        return lucros.stream()
                .map(EntityToDTOConverter::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<LucroDTO> findByCategoriaId(Long categoriaId) {
        List<Lucro> lucros = lucroRepository.findByCategoriaId(categoriaId);
        return lucros.stream()
                .map(EntityToDTOConverter::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<LucroDTO> findByDescricaoContaining(String descricao) {
        List<Lucro> lucros = lucroRepository.findByDescricaoContaining(descricao);
        return lucros.stream()
                .map(EntityToDTOConverter::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<LucroDTO> findByValorGreaterThan(float valor) {
        List<Lucro> lucros = lucroRepository.findByValorGreaterThan(valor);
        return lucros.stream()
                .map(EntityToDTOConverter::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<LucroDTO> findByPessoaIdAndCategoriaId(Long pessoaId, Long categoriaId) {
        List<Lucro> lucros = lucroRepository.findByPessoaIdAndCategoriaId(pessoaId, categoriaId);
        return lucros.stream()
                .map(EntityToDTOConverter::convertToDTO)
                .collect(Collectors.toList());
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
