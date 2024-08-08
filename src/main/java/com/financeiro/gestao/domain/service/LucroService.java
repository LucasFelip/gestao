package com.financeiro.gestao.domain.service;

import com.financeiro.gestao.api.dto.LucroDTO;
import com.financeiro.gestao.domain.exception.BusinessRuleException;
import com.financeiro.gestao.domain.exception.ResourceNotFoundException;
import com.financeiro.gestao.domain.model.Categoria;
import com.financeiro.gestao.domain.model.Lucro;
import com.financeiro.gestao.domain.model.Pessoa;
import com.financeiro.gestao.domain.repository.LucroRepository;
import com.financeiro.gestao.util.EntityToDTOConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LucroService {

    @Autowired
    private LucroRepository lucroRepository;

    @Transactional(readOnly = true)
    public List<LucroDTO> findAll() {
        return lucroRepository.findAll()
                .stream()
                .map(EntityToDTOConverter::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<LucroDTO> findById(Long id) {
        return lucroRepository.findById(id)
                .map(EntityToDTOConverter::convertToDTO);
    }

    @Transactional(readOnly = true)
    public List<LucroDTO> findByPessoa(Pessoa pessoa) {
        return lucroRepository.findByPessoa(pessoa)
                .stream()
                .map(EntityToDTOConverter::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<LucroDTO> findByDataBetween(LocalDate inicio, LocalDate fim) {
        return lucroRepository.findByDataBetween(inicio, fim)
                .stream()
                .map(EntityToDTOConverter::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<LucroDTO> findByCategoria(Categoria categoria) {
        return lucroRepository.findByCategoria(categoria)
                .stream()
                .map(EntityToDTOConverter::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<LucroDTO> findByDescricaoContaining(String descricao) {
        return lucroRepository.findByDescricaoContaining(descricao)
                .stream()
                .map(EntityToDTOConverter::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<LucroDTO> findByValorGreaterThan(BigDecimal valor) {
        return lucroRepository.findByValorGreaterThan(valor)
                .stream()
                .map(EntityToDTOConverter::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<LucroDTO> findByPessoaIdAndCategoriaId(Long pessoaId, Long categoriaId) {
        return lucroRepository.findByPessoaIdAndCategoriaId(pessoaId, categoriaId)
                .stream()
                .map(EntityToDTOConverter::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<LucroDTO> findByDataAfter(LocalDate data) {
        return lucroRepository.findByDataAfter(data)
                .stream()
                .map(EntityToDTOConverter::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<LucroDTO> findByDataBefore(LocalDate data) {
        return lucroRepository.findByDataBefore(data)
                .stream()
                .map(EntityToDTOConverter::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public BigDecimal findSumValorByPessoaAndDataBetween(Pessoa pessoa, LocalDate inicio, LocalDate fim) {
        return lucroRepository.findSumValorByPessoaAndDataBetween(pessoa, inicio, fim);
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
        if (lucro.getDescricao() == null || lucro.getDescricao().trim().isEmpty()) {
            throw new BusinessRuleException("A descrição do lucro não pode estar vazia.");
        }
        if (lucro.getValor() == null || lucro.getValor().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessRuleException("O valor do lucro deve ser maior que zero.");
        }
    }
}
