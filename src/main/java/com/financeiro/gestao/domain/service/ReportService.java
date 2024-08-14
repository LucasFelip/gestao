package com.financeiro.gestao.domain.service;

import com.financeiro.gestao.api.dto.GastoDTO;
import com.financeiro.gestao.api.dto.LucroDTO;
import com.financeiro.gestao.domain.exception.BusinessRuleException;
import com.financeiro.gestao.domain.model.Gasto;
import com.financeiro.gestao.domain.model.Lucro;
import com.financeiro.gestao.domain.model.Pessoa;
import com.financeiro.gestao.domain.repository.ReportRepository;
import com.financeiro.gestao.util.EntityToDTOConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    private Pessoa getLoggedPessoa() {
        return userDetailsServiceImpl.userConnected();
    }

    @Transactional(readOnly = true)
    public List<GastoDTO> findGastosByPessoaAndPeriodo(LocalDate inicio, LocalDate fim) {
        try {
            List<Gasto> gastos = reportRepository.findGastosByPessoaAndPeriodo(getLoggedPessoa(), inicio, fim);
            return gastos.stream()
                    .map(EntityToDTOConverter::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new BusinessRuleException("Erro ao buscar gastos no período de " + inicio + " a " + fim + ". Detalhes: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public List<LucroDTO> findLucrosByPessoaAndPeriodo(LocalDate inicio, LocalDate fim) {
        try {
            List<Lucro> lucros = reportRepository.findLucrosByPessoaAndPeriodo(getLoggedPessoa(), inicio, fim);
            return lucros.stream()
                    .map(EntityToDTOConverter::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new BusinessRuleException("Erro ao buscar lucros no período de " + inicio + " a " + fim + ". Detalhes: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public BigDecimal calculateSaldoByPessoaAndPeriodo(LocalDate inicio, LocalDate fim) {
        try {
            return reportRepository.calculateSaldoByPessoaAndPeriodo(getLoggedPessoa(), inicio, fim);
        } catch (Exception e) {
            throw new BusinessRuleException("Erro ao calcular saldo no período de " + inicio + " a " + fim + ". Detalhes: " + e.getMessage());
        }
    }

    // Novos métodos para calcular com base no orçamento ativo
    @Transactional(readOnly = true)
    public BigDecimal calculateTotalGastosByOrcamentoAtivo() {
        try {
            return reportRepository.calculateTotalGastosByPessoaAndOrcamentoAtivo(getLoggedPessoa());
        } catch (Exception e) {
            throw new BusinessRuleException("Erro ao calcular total de gastos com base no orçamento ativo. Detalhes: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public BigDecimal calculateTotalLucrosByOrcamentoAtivo() {
        try {
            return reportRepository.calculateTotalLucrosByPessoaAndOrcamentoAtivo(getLoggedPessoa());
        } catch (Exception e) {
            throw new BusinessRuleException("Erro ao calcular total de lucros com base no orçamento ativo. Detalhes: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public BigDecimal calculateSaldoByOrcamentoAtivo() {
        try {
            return reportRepository.calculateSaldoByPessoaAndOrcamentoAtivo(getLoggedPessoa());
        } catch (Exception e) {
            throw new BusinessRuleException("Erro ao calcular saldo com base no orçamento ativo. Detalhes: " + e.getMessage());
        }
    }
}
