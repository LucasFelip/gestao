package com.financeiro.gestao.domain.repository;

import com.financeiro.gestao.domain.model.Orcamento;
import com.financeiro.gestao.domain.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrcamentoRepository extends JpaRepository<Orcamento, Long> {
    List<Orcamento> findByPessoa(Pessoa pessoa);
    List<Orcamento> findByPessoaAndDataInicioBetweenOrDataFimBetween(Pessoa pessoa, LocalDate inicioStart, LocalDate inicioEnd, LocalDate fimStart, LocalDate fimEnd);
    List<Orcamento> findByPessoaAndLimiteGreaterThan(Pessoa pessoa, BigDecimal limite);
    List<Orcamento> findByPessoaAndDataFimAfter(Pessoa pessoa, LocalDate hoje);
    List<Orcamento> findByPessoaAndDataInicioBeforeAndDataFimAfter(Pessoa pessoa, LocalDate inicio, LocalDate fim);
}
