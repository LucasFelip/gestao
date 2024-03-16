package com.financeiro.gestao.service;

import com.financeiro.gestao.model.Orcamento;
import com.financeiro.gestao.repository.OrcamentoRepository;
import com.financeiro.gestao.exception.ResourceNotFoundException;
import com.financeiro.gestao.exception.BusinessRuleException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrcamentoService {

    private final OrcamentoRepository orcamentoRepository;

    @Autowired
    public OrcamentoService(OrcamentoRepository orcamentoRepository) {
        this.orcamentoRepository = orcamentoRepository;
    }

    @Transactional(readOnly = true)
    public List<Orcamento> findAll() {
        return orcamentoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Orcamento> findById(Long id) {
        return Optional.ofNullable(orcamentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Orçamento não encontrado com o ID: " + id)));
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
        orcamento.setInicio(orcamentoAtualizado.getInicio());
        orcamento.setFim(orcamentoAtualizado.getFim());
        orcamento.setLimite(orcamentoAtualizado.getLimite());
        orcamento.setCategoria(orcamentoAtualizado.getCategoria());

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
        if (orcamento.getInicio().after(orcamento.getFim())) {
            throw new BusinessRuleException("A data de início do orçamento não pode ser posterior à data de fim.");
        }

        if (orcamento.getLimite() < 0) {
            throw new BusinessRuleException("O limite do orçamento não pode ser negativo.");
        }

        // Verificação de Sobreposição de Orçamento
        List<Orcamento> orcamentosExistentes = orcamentoRepository.findByPessoaIdAndCategoriaId(orcamento.getPessoa().getId(), orcamento.getCategoria().getId());
        for (Orcamento existente : orcamentosExistentes) {
            if (orcamento.getId() == null || !orcamento.getId().equals(existente.getId())) { // Ignora o próprio orçamento em caso de atualização
                if (periodosSobrepoe(orcamento.getInicio(), orcamento.getFim(), existente.getInicio(), existente.getFim())) {
                    throw new BusinessRuleException("O orçamento sobrepõe um orçamento existente para a mesma categoria.");
                }
            }
        }

        // Limite Mínimo de Orçamento
        float limiteMinimo = 100.00f; // Valor
        if (orcamento.getLimite() < limiteMinimo) {
            throw new BusinessRuleException(String.format("O limite do orçamento deve ser no mínimo R$ %.2f.", limiteMinimo));
        }
    }

    private boolean periodosSobrepoe(Date inicio1, Date fim1, Date inicio2, Date fim2) {
        return !inicio1.after(fim2) && !fim1.before(inicio2);
    }
}
