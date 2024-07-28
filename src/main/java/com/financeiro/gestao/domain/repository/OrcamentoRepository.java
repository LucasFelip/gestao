package com.financeiro.gestao.domain.repository;

import com.financeiro.gestao.domain.model.Orcamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface OrcamentoRepository extends JpaRepository<Orcamento, Long> {
    List<Orcamento> findByPessoaId(Long pessoaId);
    List<Orcamento> findByDataInicioBetweenOrDataFimBetween(Date inicioStart, Date inicioEnd, Date fimStart, Date fimEnd);
    List<Orcamento> findByLimiteGreaterThan(float limite);
}
