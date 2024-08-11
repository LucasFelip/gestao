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
    private final UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    public ReportService(ReportRepository reportRepository, UserDetailsServiceImpl userDetailsServiceImpl) {
        this.reportRepository = reportRepository;
        this.userDetailsServiceImpl = userDetailsServiceImpl;
    }

    @Transactional(readOnly = true)
    public List<GastoDTO> findGastosByPessoaAndPeriodo(LocalDate inicio, LocalDate fim) {
        Pessoa pessoa = userDetailsServiceImpl.userConnected();
        List<Gasto> gastos = reportRepository.findGastosByPessoaAndPeriodo(pessoa, inicio, fim);
        return gastos.stream()
                .map(EntityToDTOConverter::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<LucroDTO> findLucrosByPessoaAndPeriodo(LocalDate inicio, LocalDate fim) {
        Pessoa pessoa = userDetailsServiceImpl.userConnected();
        List<Lucro> lucros = reportRepository.findLucrosByPessoaAndPeriodo(pessoa, inicio, fim);
        return lucros.stream()
                .map(EntityToDTOConverter::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public BigDecimal calculateSaldoByPessoaAndPeriodo(LocalDate inicio, LocalDate fim) {
        Pessoa pessoa = userDetailsServiceImpl.userConnected();
        return reportRepository.calculateSaldoByPessoaAndPeriodo(pessoa, inicio, fim);
    }
}
