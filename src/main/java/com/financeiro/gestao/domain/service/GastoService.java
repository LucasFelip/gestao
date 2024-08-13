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
    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    private Pessoa getLoggedPessoa() {
        return userDetailsServiceImpl.userConnected();
    }

    @Transactional(readOnly = true)
    public List<GastoDTO> findAll() {
        try {
            return gastoRepository.findAll()
                    .stream()
                    .map(EntityToDTOConverter::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new BusinessRuleException("Erro ao buscar todos os gastos: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public Optional<GastoDTO> findById(Long id) {
        try {
            return gastoRepository.findById(id)
                    .map(EntityToDTOConverter::convertToDTO);
        } catch (Exception e) {
            throw new BusinessRuleException("Erro ao buscar gasto com ID: " + id + ". Detalhes: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public List<GastoDTO> findByPessoa() {
        try {
            return gastoRepository.findByPessoa(getLoggedPessoa())
                    .stream()
                    .map(EntityToDTOConverter::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new BusinessRuleException("Erro ao usuário logado: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public List<GastoDTO> findByDataBetween(LocalDate inicio, LocalDate fim) {
        try {
            return gastoRepository.findByPessoaAndDataBetween(getLoggedPessoa(), inicio, fim)
                    .stream()
                    .map(EntityToDTOConverter::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new BusinessRuleException("Erro ao buscar gastos entre as datas " + inicio + " e " + fim + ": " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public List<GastoDTO> findByDescricaoContaining(String descricao) {
        try {
            return gastoRepository.findByPessoaAndDescricaoContaining(getLoggedPessoa(), descricao)
                    .stream()
                    .map(EntityToDTOConverter::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new BusinessRuleException("Erro ao buscar gastos com descrição contendo: " + descricao + ". Detalhes: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public List<GastoDTO> findByValorGreaterThan(BigDecimal valor) {
        try {
            return gastoRepository.findByPessoaAndValorGreaterThan(getLoggedPessoa(), valor)
                    .stream()
                    .map(EntityToDTOConverter::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new BusinessRuleException("Erro ao buscar gastos com valor maior que " + valor + ". Detalhes: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public List<GastoDTO> findByPessoaAndCategoria(Categoria categoria) {
        try {
            return gastoRepository.findByPessoaAndCategoria(getLoggedPessoa(), categoria)
                    .stream()
                    .map(EntityToDTOConverter::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new BusinessRuleException("Erro ao buscar gastos da categoria: " + categoria.getNome() + ". Detalhes: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public List<GastoDTO> findByDataAfter(LocalDate data) {
        try {
            return gastoRepository.findByPessoaAndDataAfter(getLoggedPessoa(), data)
                    .stream()
                    .map(EntityToDTOConverter::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new BusinessRuleException("Erro ao buscar gastos após a data: " + data + ". Detalhes: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public List<GastoDTO> findByDataBefore(LocalDate data) {
        try {
            return gastoRepository.findByPessoaAndDataBefore(getLoggedPessoa(), data)
                    .stream()
                    .map(EntityToDTOConverter::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new BusinessRuleException("Erro ao buscar gastos antes da data: " + data + ". Detalhes: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public BigDecimal findSumValorByPessoaAndDataBetween(LocalDate inicio, LocalDate fim) {
        try {
            return gastoRepository.findSumValorByPessoaAndDataBetween(getLoggedPessoa(), inicio, fim);
        } catch (Exception e) {
            throw new BusinessRuleException("Erro ao somar os valores dos gastos entre as datas " + inicio + " e " + fim + ". Detalhes: " + e.getMessage());
        }
    }

    @Transactional
    public Gasto save(Gasto gasto) {
        try {
            validarGasto(gasto);
            return gastoRepository.save(gasto);
        } catch (Exception e) {
            throw new BusinessRuleException("Erro ao salvar o gasto: " + e.getMessage());
        }
    }

    @Transactional
    public Gasto update(Long id, Gasto gastoAtualizado) {
        try {
            Gasto gasto = gastoRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Gasto não encontrado com o ID: " + id));

            gasto.setDescricao(gastoAtualizado.getDescricao());
            gasto.setValor(gastoAtualizado.getValor());
            gasto.setData(gastoAtualizado.getData());
            gasto.setCategoria(gastoAtualizado.getCategoria());
            gasto.setPessoa(gastoAtualizado.getPessoa());

            validarGasto(gasto);

            return gastoRepository.save(gasto);
        } catch (ResourceNotFoundException e) {
            throw e; // Re-lança a exceção de recurso não encontrado
        } catch (Exception e) {
            throw new BusinessRuleException("Erro ao atualizar o gasto com ID: " + id + ". Detalhes: " + e.getMessage());
        }
    }

    @Transactional
    public void delete(Long id) {
        try {
            if (!gastoRepository.existsById(id)) {
                throw new ResourceNotFoundException("Gasto não encontrado com o ID: " + id);
            }
            gastoRepository.deleteById(id);
        } catch (ResourceNotFoundException e) {
            throw e; // Re-lança a exceção de recurso não encontrado
        } catch (Exception e) {
            throw new BusinessRuleException("Erro ao excluir o gasto com ID: " + id + ". Detalhes: " + e.getMessage());
        }
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
