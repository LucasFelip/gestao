package com.financeiro.gestao.domain.service;

import com.financeiro.gestao.api.dto.OrcamentoDTO;
import com.financeiro.gestao.domain.exception.BusinessRuleException;
import com.financeiro.gestao.domain.exception.ResourceNotFoundException;
import com.financeiro.gestao.domain.model.Orcamento;
import com.financeiro.gestao.domain.repository.OrcamentoRepository;
import com.financeiro.gestao.util.EntityToDTOConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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
        List<Orcamento> orcamentos = orcamentoRepository.findAll();
        return orcamentos.stream()
                .map(EntityToDTOConverter::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<OrcamentoDTO> findById(Long id) {
        Orcamento orcamento = orcamentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Orçamento não encontrado com o ID: " + id));
        return Optional.of(EntityToDTOConverter.convertToDTO(orcamento));
    }

    @Transactional(readOnly = true)
    public List<OrcamentoDTO> findByPessoaId(Long pessoaId) {
        List<Orcamento> orcamentos = orcamentoRepository.findByPessoaId(pessoaId);
        return orcamentos.stream()
                .map(EntityToDTOConverter::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<OrcamentoDTO> findByDataInicioBetweenOrDataFimBetween(Date inicioStart, Date inicioEnd, Date fimStart, Date fimEnd) {
        List<Orcamento> orcamentos = orcamentoRepository.findByDataInicioBetweenOrDataFimBetween(inicioStart, inicioEnd, fimStart, fimEnd);
        return orcamentos.stream()
                .map(EntityToDTOConverter::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<OrcamentoDTO> findByLimiteGreaterThan(float limite) {
        List<Orcamento> orcamentos = orcamentoRepository.findByLimiteGreaterThan(limite);
        return orcamentos.stream()
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
                .orElseThrow(() -> new ResourceNotFoundException("Orçamento não encontrado com o ID: " + id));

        orcamento.setPessoa(orcamentoAtualizado.getPessoa());
        orcamento.setDataInicio(orcamentoAtualizado.getDataInicio());
        orcamento.setDataFim(orcamentoAtualizado.getDataFim());
        orcamento.setLimite(orcamentoAtualizado.getLimite());

        validarOrcamento(orcamento);

        return orcamentoRepository.save(orcamento);
    }

    @Transactional
    public void delete(Long id) {
        if (!orcamentoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Orçamento não encontrado com o ID: " + id);
        }
        orcamentoRepository.deleteById(id);
    }

    private void validarOrcamento(Orcamento orcamento) {
        if (orcamento.getDataInicio().after(orcamento.getDataFim())) {
            throw new BusinessRuleException("A data de início do orçamento não pode ser posterior à data de fim.");
        }

        if (orcamento.getLimite() < 0) {
            throw new BusinessRuleException("O limite do orçamento não pode ser negativo.");
        }

        // Verificação de Sobreposição de Orçamento
        List<Orcamento> orcamentosExistentes = orcamentoRepository.findByPessoaId(orcamento.getPessoa().getId());
        for (Orcamento existente : orcamentosExistentes) {
            if (orcamento.getId() == null || !orcamento.getId().equals(existente.getId())) { // Ignora o próprio orçamento em caso de atualização
                if (periodosSobrepoe(orcamento.getDataInicio(), orcamento.getDataFim(), existente.getDataInicio(), existente.getDataFim())) {
                    throw new BusinessRuleException("O orçamento sobrepõe um orçamento existente para a mesma pessoa.");
                }
            }
        }

        // Limite Mínimo de Orçamento
        float limiteMinimo = 1000.00f; // Valor
        if (orcamento.getLimite() < limiteMinimo) {
            throw new BusinessRuleException(String.format("O limite do orçamento deve ser no mínimo R$ %.2f.", limiteMinimo));
        }
    }

    private boolean periodosSobrepoe(Date inicio1, Date fim1, Date inicio2, Date fim2) {
        return !inicio1.after(fim2) && !fim1.before(inicio2);
    }
}
