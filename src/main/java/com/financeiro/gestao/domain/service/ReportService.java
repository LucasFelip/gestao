package com.financeiro.gestao.domain.service;

import com.financeiro.gestao.api.dto.GastoDTO;
import com.financeiro.gestao.api.dto.LucroDTO;
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

    private final ReportRepository reportRepository;

    @Autowired
    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @Transactional(readOnly = true)
    public List<GastoDTO> findGastosByPessoaAndPeriodo(Pessoa pessoa, LocalDate inicio, LocalDate fim) {
        List<Gasto> gastos = reportRepository.findGastosByPessoaAndPeriodo(pessoa, inicio, fim);
        return gastos.stream()
                .map(EntityToDTOConverter::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<LucroDTO> findLucrosByPessoaAndPeriodo(Pessoa pessoa, LocalDate inicio, LocalDate fim) {
        List<Lucro> lucros = reportRepository.findLucrosByPessoaAndPeriodo(pessoa, inicio, fim);
        return lucros.stream()
                .map(EntityToDTOConverter::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public BigDecimal calculateSaldoByPessoaAndPeriodo(Pessoa pessoa, LocalDate inicio, LocalDate fim) {
        return reportRepository.calculateSaldoByPessoaAndPeriodo(pessoa, inicio, fim);
    }
}
