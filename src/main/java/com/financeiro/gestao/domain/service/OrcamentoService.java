package com.financeiro.gestao.domain.service;

import com.financeiro.gestao.api.dto.OrcamentoDTO;
import com.financeiro.gestao.domain.exception.BusinessRuleException;
import com.financeiro.gestao.domain.exception.ResourceNotFoundException;
import com.financeiro.gestao.domain.model.Orcamento;
import com.financeiro.gestao.domain.model.Pessoa;
import com.financeiro.gestao.domain.repository.OrcamentoRepository;
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
public class OrcamentoService {

    @Autowired
    private OrcamentoRepository orcamentoRepository;

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    private Pessoa getLoggedPessoa() {
        return userDetailsServiceImpl.userConnected();
    }

    @Transactional(readOnly = true)
    public List<OrcamentoDTO> findAll() {
        try {
            return orcamentoRepository.findAll()
                    .stream()
                    .map(EntityToDTOConverter::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new BusinessRuleException("Erro ao buscar todos os orçamentos: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public Optional<OrcamentoDTO> findById(Long id) {
        try {
            return orcamentoRepository.findById(id)
                    .map(EntityToDTOConverter::convertToDTO);
        } catch (Exception e) {
            throw new BusinessRuleException("Erro ao buscar orçamento com ID: " + id + ". Detalhes: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public List<OrcamentoDTO> findByPessoa() {
        try {
            return orcamentoRepository.findByPessoaAndAtivoTrue(getLoggedPessoa())
                    .stream()
                    .map(EntityToDTOConverter::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new BusinessRuleException("Erro ao buscar orçamentos ativos do usuário logado: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public List<OrcamentoDTO> findByDataInicioBetweenOrDataFimBetween(LocalDate inicioStart, LocalDate inicioEnd, LocalDate fimStart, LocalDate fimEnd) {
        try {
            return orcamentoRepository.findByPessoaAndAtivoTrueAndDataInicioBetweenOrDataFimBetween(getLoggedPessoa(), inicioStart, inicioEnd, fimStart, fimEnd)
                    .stream()
                    .map(EntityToDTOConverter::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new BusinessRuleException("Erro ao buscar orçamentos ativos entre as datas de início ou fim especificadas: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public List<OrcamentoDTO> findByLimiteGreaterThan(BigDecimal limite) {
        try {
            return orcamentoRepository.findByPessoaAndAtivoTrueAndLimiteGreaterThan(getLoggedPessoa(), limite)
                    .stream()
                    .map(EntityToDTOConverter::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new BusinessRuleException("Erro ao buscar orçamentos ativos com limite maior que " + limite + ". Detalhes: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public List<OrcamentoDTO> findByPessoaAndDataFimAfter(LocalDate hoje) {
        try {
            return orcamentoRepository.findByPessoaAndAtivoTrueAndDataFimAfter(getLoggedPessoa(), hoje)
                    .stream()
                    .map(EntityToDTOConverter::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new BusinessRuleException("Erro ao buscar orçamentos ativos com data de fim após " + hoje + ". Detalhes: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public List<OrcamentoDTO> findByPessoaAndDataInicioBeforeAndDataFimAfter(LocalDate inicio, LocalDate fim) {
        try {
            return orcamentoRepository.findByPessoaAndAtivoTrueAndDataInicioBeforeAndDataFimAfter(getLoggedPessoa(), inicio, fim)
                    .stream()
                    .map(EntityToDTOConverter::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new BusinessRuleException("Erro ao buscar orçamentos ativos com data de início antes de " + inicio + " e data de fim depois de " + fim + ". Detalhes: " + e.getMessage());
        }
    }

    @Transactional
    public Orcamento save(Orcamento orcamento) {
        try {
            Pessoa pessoa = getLoggedPessoa();
            orcamento.setPessoa(getLoggedPessoa());

            List<Orcamento> orcamentosAtivos = orcamentoRepository.findByPessoaAndAtivoTrue(pessoa);
            orcamentosAtivos.forEach(o -> o.setAtivo(false));
            orcamentoRepository.saveAll(orcamentosAtivos);

            orcamento.setAtivo(true);
            validarOrcamento(orcamento);
            return orcamentoRepository.save(orcamento);
        } catch (Exception e) {
            throw new BusinessRuleException("Erro ao salvar o orçamento: " + e.getMessage());
        }
    }

    @Transactional
    public Orcamento update(Long id, Orcamento orcamentoAtualizado) {
        try {
            Orcamento orcamento = orcamentoRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Orçamento não encontrado com o ID: " + id));

            List<Orcamento> orcamentosAtivos = orcamentoRepository.findByPessoaAndAtivoTrue(orcamento.getPessoa());
            orcamentosAtivos.forEach(o -> o.setAtivo(false));
            orcamentoRepository.saveAll(orcamentosAtivos);

            orcamento.setDataInicio(orcamentoAtualizado.getDataInicio());
            orcamento.setDataFim(orcamentoAtualizado.getDataFim());
            orcamento.setLimite(orcamentoAtualizado.getLimite());
            orcamento.setPessoa(orcamentoAtualizado.getPessoa());
            orcamento.setAtivo(true);

            validarOrcamento(orcamento);

            return orcamentoRepository.save(orcamento);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessRuleException("Erro ao atualizar o orçamento com ID: " + id + ". Detalhes: " + e.getMessage());
        }
    }

    @Transactional
    public void delete(Long id) {
        try {
            if (!orcamentoRepository.existsById(id)) {
                throw new ResourceNotFoundException("Orçamento não encontrado com o ID: " + id);
            }
            orcamentoRepository.deleteById(id);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessRuleException("Erro ao excluir o orçamento com ID: " + id + ". Detalhes: " + e.getMessage());
        }
    }

    private void validarOrcamento(Orcamento orcamento) {
        if (orcamento.getDataInicio() == null) {
            throw new BusinessRuleException("A data de início do orçamento não pode estar vazia.");
        }
        if (orcamento.getDataFim() == null) {
            throw new BusinessRuleException("A data de fim do orçamento não pode estar vazia.");
        }
        if (orcamento.getLimite() == null || orcamento.getLimite().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessRuleException("O limite do orçamento deve ser maior que zero.");
        }
    }
}
