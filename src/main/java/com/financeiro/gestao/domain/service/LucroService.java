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

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    private Pessoa getLoggedPessoa() {
        return userDetailsServiceImpl.userConnected();
    }

    @Transactional(readOnly = true)
    public List<LucroDTO> findAll() {
        try {
            return lucroRepository.findAll()
                    .stream()
                    .map(EntityToDTOConverter::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new BusinessRuleException("Erro ao buscar todos os lucros: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public Optional<LucroDTO> findById(Long id) {
        try {
            return lucroRepository.findById(id)
                    .map(EntityToDTOConverter::convertToDTO);
        } catch (Exception e) {
            throw new BusinessRuleException("Erro ao buscar lucro com ID: " + id + ". Detalhes: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public List<LucroDTO> findByPessoa() {
        try {
            return lucroRepository.findByPessoa(getLoggedPessoa())
                    .stream()
                    .map(EntityToDTOConverter::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new BusinessRuleException("Erro ao buscar lucros do usuário logado: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public List<LucroDTO> findByDataBetween(LocalDate inicio, LocalDate fim) {
        try {
            return lucroRepository.findByPessoaAndDataBetween(getLoggedPessoa(), inicio, fim)
                    .stream()
                    .map(EntityToDTOConverter::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new BusinessRuleException("Erro ao buscar lucros entre as datas " + inicio + " e " + fim + ". Detalhes: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public List<LucroDTO> findByCategoria(Categoria categoria) {
        try {
            return lucroRepository.findByPessoaAndCategoria(getLoggedPessoa(), categoria)
                    .stream()
                    .map(EntityToDTOConverter::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new BusinessRuleException("Erro ao buscar lucros da categoria: " + categoria.getNome() + ". Detalhes: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public List<LucroDTO> findByDescricaoContaining(String descricao) {
        try {
            return lucroRepository.findByPessoaAndDescricaoContaining(getLoggedPessoa(), descricao)
                    .stream()
                    .map(EntityToDTOConverter::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new BusinessRuleException("Erro ao buscar lucros com descrição contendo: " + descricao + ". Detalhes: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public List<LucroDTO> findByValorGreaterThan(BigDecimal valor) {
        try {
            return lucroRepository.findByPessoaAndValorGreaterThan(getLoggedPessoa(), valor)
                    .stream()
                    .map(EntityToDTOConverter::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new BusinessRuleException("Erro ao buscar lucros com valor maior que " + valor + ". Detalhes: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public List<LucroDTO> findByDataAfter(LocalDate data) {
        try {
            return lucroRepository.findByPessoaAndDataAfter(getLoggedPessoa(), data)
                    .stream()
                    .map(EntityToDTOConverter::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new BusinessRuleException("Erro ao buscar lucros após a data: " + data + ". Detalhes: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public List<LucroDTO> findByDataBefore(LocalDate data) {
        try {
            return lucroRepository.findByPessoaAndDataBefore(getLoggedPessoa(), data)
                    .stream()
                    .map(EntityToDTOConverter::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new BusinessRuleException("Erro ao buscar lucros antes da data: " + data + ". Detalhes: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public BigDecimal findSumValorByPessoaAndDataBetween(LocalDate inicio, LocalDate fim) {
        try {
            return lucroRepository.findSumValorByPessoaAndDataBetween(getLoggedPessoa(), inicio, fim);
        } catch (Exception e) {
            throw new BusinessRuleException("Erro ao somar os valores dos lucros entre as datas " + inicio + " e " + fim + ". Detalhes: " + e.getMessage());
        }
    }

    @Transactional
    public Lucro save(Lucro lucro) {
        try {
            validarLucro(lucro);
            return lucroRepository.save(lucro);
        } catch (Exception e) {
            throw new BusinessRuleException("Erro ao salvar o lucro: " + e.getMessage());
        }
    }

    @Transactional
    public Lucro update(Long id, Lucro lucroAtualizado) {
        try {
            Lucro lucro = lucroRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Lucro não encontrado com o ID: " + id));

            lucro.setDescricao(lucroAtualizado.getDescricao());
            lucro.setValor(lucroAtualizado.getValor());
            lucro.setData(lucroAtualizado.getData());
            lucro.setCategoria(lucroAtualizado.getCategoria());
            lucro.setPessoa(lucroAtualizado.getPessoa());

            validarLucro(lucro);

            return lucroRepository.save(lucro);
        } catch (ResourceNotFoundException e) {
            throw e; // Re-lança a exceção de recurso não encontrado
        } catch (Exception e) {
            throw new BusinessRuleException("Erro ao atualizar o lucro com ID: " + id + ". Detalhes: " + e.getMessage());
        }
    }

    @Transactional
    public void delete(Long id) {
        try {
            if (!lucroRepository.existsById(id)) {
                throw new ResourceNotFoundException("Lucro não encontrado com o ID: " + id);
            }
            lucroRepository.deleteById(id);
        } catch (ResourceNotFoundException e) {
            throw e; // Re-lança a exceção de recurso não encontrado
        } catch (Exception e) {
            throw new BusinessRuleException("Erro ao excluir o lucro com ID: " + id + ". Detalhes: " + e.getMessage());
        }
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
