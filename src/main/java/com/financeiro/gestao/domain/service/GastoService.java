package com.financeiro.gestao.domain.service;

import com.financeiro.gestao.api.dto.GastoDTO;
import com.financeiro.gestao.domain.exception.BusinessRuleException;
import com.financeiro.gestao.domain.exception.ResourceNotFoundException;
import com.financeiro.gestao.domain.model.Categoria;
import com.financeiro.gestao.domain.model.Gasto;
import com.financeiro.gestao.domain.model.Pessoa;
import com.financeiro.gestao.domain.repository.GastoRepository;
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
public class GastoService {

    @Autowired
    private GastoRepository gastoRepository;

    @Transactional(readOnly = true)
    public List<GastoDTO> findAll() {
        return gastoRepository.findAll()
                .stream()
                .map(EntityToDTOConverter::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<GastoDTO> findById(Long id) {
        return gastoRepository.findById(id)
                .map(EntityToDTOConverter::convertToDTO);
    }

    @Transactional(readOnly = true)
    public List<GastoDTO> findByPessoa(Pessoa pessoa) {
        return gastoRepository.findByPessoa(pessoa)
                .stream()
                .map(EntityToDTOConverter::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<GastoDTO> findByDataBetween(LocalDate inicio, LocalDate fim) {
        return gastoRepository.findByDataBetween(inicio, fim)
                .stream()
                .map(EntityToDTOConverter::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<GastoDTO> findByCategoria(Categoria categoria) {
        return gastoRepository.findByCategoria(categoria)
                .stream()
                .map(EntityToDTOConverter::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<GastoDTO> findByDescricaoContaining(String descricao) {
        return gastoRepository.findByDescricaoContaining(descricao)
                .stream()
                .map(EntityToDTOConverter::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<GastoDTO> findByValorGreaterThan(BigDecimal valor) {
        return gastoRepository.findByValorGreaterThan(valor)
                .stream()
                .map(EntityToDTOConverter::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<GastoDTO> findByPessoaAndCategoria(Pessoa pessoa, Categoria categoria) {
        return gastoRepository.findByPessoaAndCategoria(pessoa, categoria)
                .stream()
                .map(EntityToDTOConverter::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<GastoDTO> findByDataAfter(LocalDate data) {
        return gastoRepository.findByDataAfter(data)
                .stream()
                .map(EntityToDTOConverter::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<GastoDTO> findByDataBefore(LocalDate data) {
        return gastoRepository.findByDataBefore(data)
                .stream()
                .map(EntityToDTOConverter::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public BigDecimal findSumValorByPessoaAndDataBetween(Pessoa pessoa, LocalDate inicio, LocalDate fim) {
        return gastoRepository.findSumValorByPessoaAndDataBetween(pessoa, inicio, fim);
    }

    @Transactional
    public Gasto save(Gasto gasto) {
        validarGasto(gasto);
        return gastoRepository.save(gasto);
    }

    @Transactional
    public Gasto update(Long id, Gasto gastoAtualizado) {
        Gasto gasto = gastoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Gasto não encontrado com o ID: " + id));

        gasto.setDescricao(gastoAtualizado.getDescricao());
        gasto.setValor(gastoAtualizado.getValor());
        gasto.setData(gastoAtualizado.getData());
        gasto.setCategoria(gastoAtualizado.getCategoria());
        gasto.setPessoa(gastoAtualizado.getPessoa());

        validarGasto(gasto);

        return gastoRepository.save(gasto);
    }

    @Transactional
    public void delete(Long id) {
        if (!gastoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Gasto não encontrado com o ID: " + id);
        }
        gastoRepository.deleteById(id);
    }

    private void validarGasto(Gasto gasto) {
        if (gasto.getDescricao() == null || gasto.getDescricao().trim().isEmpty()) {
            throw new BusinessRuleException("A descrição do gasto não pode estar vazia.");
        }
        if (gasto.getValor() == null || gasto.getValor().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessRuleException("O valor do gasto deve ser maior que zero.");
        }
    }
}
