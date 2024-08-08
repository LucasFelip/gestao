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

    private final OrcamentoRepository orcamentoRepository;

    @Autowired
    public OrcamentoService(OrcamentoRepository orcamentoRepository) {
        this.orcamentoRepository = orcamentoRepository;
    }

    @Transactional(readOnly = true)
    public List<OrcamentoDTO> findAll() {
        return orcamentoRepository.findAll()
                .stream()
                .map(EntityToDTOConverter::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<OrcamentoDTO> findById(Long id) {
        return orcamentoRepository.findById(id)
                .map(EntityToDTOConverter::convertToDTO);
    }

    @Transactional(readOnly = true)
    public List<OrcamentoDTO> findByPessoa(Pessoa pessoa) {
        return orcamentoRepository.findByPessoa(pessoa)
                .stream()
                .map(EntityToDTOConverter::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<OrcamentoDTO> findByDataInicioBetweenOrDataFimBetween(LocalDate inicioStart, LocalDate inicioEnd, LocalDate fimStart, LocalDate fimEnd) {
        return orcamentoRepository.findByDataInicioBetweenOrDataFimBetween(inicioStart, inicioEnd, fimStart, fimEnd)
                .stream()
                .map(EntityToDTOConverter::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<OrcamentoDTO> findByLimiteGreaterThan(BigDecimal limite) {
        return orcamentoRepository.findByLimiteGreaterThan(limite)
                .stream()
                .map(EntityToDTOConverter::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<OrcamentoDTO> findByPessoaAndDataFimAfter(Pessoa pessoa, LocalDate hoje) {
        return orcamentoRepository.findByPessoaAndDataFimAfter(pessoa, hoje)
                .stream()
                .map(EntityToDTOConverter::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<OrcamentoDTO> findByPessoaAndDataInicioBeforeAndDataFimAfter(Pessoa pessoa, LocalDate inicio, LocalDate fim) {
        return orcamentoRepository.findByPessoaAndDataInicioBeforeAndDataFimAfter(pessoa, inicio, fim)
                .stream()
                .map(EntityToDTOConverter::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public Orcamento save(Orcamento orcamento) {
        validarOrcamento(orcamento);
        return orcamentoRepository.save(orcamento);
    }

    @Transactional
    public Orcamento update(Long id, Orcamento orcamentoAtualizado) {
        Orcamento orcamento = orcamentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Orcamento não encontrado com o ID: " + id));

        orcamento.setDataInicio(orcamentoAtualizado.getDataInicio());
        orcamento.setDataFim(orcamentoAtualizado.getDataFim());
        orcamento.setLimite(orcamentoAtualizado.getLimite());
        orcamento.setPessoa(orcamentoAtualizado.getPessoa());

        validarOrcamento(orcamento);

        return orcamentoRepository.save(orcamento);
    }

    @Transactional
    public void delete(Long id) {
        if (!orcamentoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Orcamento não encontrado com o ID: " + id);
        }
        orcamentoRepository.deleteById(id);
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
