package com.financeiro.gestao.domain.repository;

import com.financeiro.gestao.domain.model.Orcamento;
import com.financeiro.gestao.domain.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface OrcamentoRepository extends JpaRepository<Orcamento, Long> {
    List<Orcamento> findByPessoa(Pessoa pessoa);
    List<Orcamento> findByDataInicioBetweenOrDataFimBetween(LocalDate inicioStart, LocalDate inicioEnd, LocalDate fimStart, LocalDate fimEnd);
    List<Orcamento> findByLimiteGreaterThan(Double limite);
}
