package com.financeiro.gestao.domain.repository;

import com.financeiro.gestao.domain.model.Gasto;
import com.financeiro.gestao.domain.model.Lucro;
import com.financeiro.gestao.domain.model.Pessoa;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReportRepository {
    List<Gasto> findGastosByPessoaAndPeriodo(Pessoa pessoa, LocalDate inicio, LocalDate fim);
    List<Lucro> findLucrosByPessoaAndPeriodo(Pessoa pessoa, LocalDate inicio, LocalDate fim);
    BigDecimal calculateSaldoByPessoaAndPeriodo(Pessoa pessoa, LocalDate inicio, LocalDate fim);
    BigDecimal calculateTotalGastosByPessoaAndOrcamentoAtivo(Pessoa pessoa);
    BigDecimal calculateTotalLucrosByPessoaAndOrcamentoAtivo(Pessoa pessoa);
    BigDecimal calculateSaldoByPessoaAndOrcamentoAtivo(Pessoa pessoa);
}
