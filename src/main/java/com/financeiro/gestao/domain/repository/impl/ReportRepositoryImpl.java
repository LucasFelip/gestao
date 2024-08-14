package com.financeiro.gestao.domain.repository.impl;

import com.financeiro.gestao.domain.model.Gasto;
import com.financeiro.gestao.domain.model.Lucro;
import com.financeiro.gestao.domain.model.Orcamento;
import com.financeiro.gestao.domain.model.Pessoa;
import com.financeiro.gestao.domain.repository.ReportRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public class ReportRepositoryImpl implements ReportRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Gasto> findGastosByPessoaAndPeriodo(Pessoa pessoa, LocalDate inicio, LocalDate fim) {
        TypedQuery<Gasto> query = entityManager.createQuery(
                "SELECT g FROM Gasto g WHERE g.pessoa = :pessoa AND g.data BETWEEN :inicio AND :fim",
                Gasto.class);
        query.setParameter("pessoa", pessoa);
        query.setParameter("inicio", inicio);
        query.setParameter("fim", fim);
        return query.getResultList();
    }

    @Override
    public List<Lucro> findLucrosByPessoaAndPeriodo(Pessoa pessoa, LocalDate inicio, LocalDate fim) {
        TypedQuery<Lucro> query = entityManager.createQuery(
                "SELECT l FROM Lucro l WHERE l.pessoa = :pessoa AND l.data BETWEEN :inicio AND :fim",
                Lucro.class);
        query.setParameter("pessoa", pessoa);
        query.setParameter("inicio", inicio);
        query.setParameter("fim", fim);
        return query.getResultList();
    }

    @Override
    public BigDecimal calculateSaldoByPessoaAndPeriodo(Pessoa pessoa, LocalDate inicio, LocalDate fim) {
        BigDecimal totalGastos = entityManager.createQuery(
                        "SELECT COALESCE(SUM(g.valor), 0) FROM Gasto g WHERE g.pessoa = :pessoa AND g.data BETWEEN :inicio AND :fim",
                        BigDecimal.class)
                .setParameter("pessoa", pessoa)
                .setParameter("inicio", inicio)
                .setParameter("fim", fim)
                .getSingleResult();

        BigDecimal totalLucros = entityManager.createQuery(
                        "SELECT COALESCE(SUM(l.valor), 0) FROM Lucro l WHERE l.pessoa = :pessoa AND l.data BETWEEN :inicio AND :fim",
                        BigDecimal.class)
                .setParameter("pessoa", pessoa)
                .setParameter("inicio", inicio)
                .setParameter("fim", fim)
                .getSingleResult();

        return totalLucros.subtract(totalGastos);
    }

    @Override
    public BigDecimal calculateTotalGastosByPessoaAndOrcamentoAtivo(Pessoa pessoa) {
        TypedQuery<BigDecimal> query = entityManager.createQuery(
                "SELECT COALESCE(SUM(g.valor), 0) FROM Gasto g WHERE g.pessoa = :pessoa AND g.data BETWEEN :inicio AND :fim",
                BigDecimal.class);

        Orcamento orcamentoAtivo = getOrcamentoAtivo(pessoa);
        query.setParameter("pessoa", pessoa);
        query.setParameter("inicio", orcamentoAtivo.getDataInicio());
        query.setParameter("fim", orcamentoAtivo.getDataFim());

        return query.getSingleResult();
    }

    @Override
    public BigDecimal calculateTotalLucrosByPessoaAndOrcamentoAtivo(Pessoa pessoa) {
        TypedQuery<BigDecimal> query = entityManager.createQuery(
                "SELECT COALESCE(SUM(l.valor), 0) FROM Lucro l WHERE l.pessoa = :pessoa AND l.data BETWEEN :inicio AND :fim",
                BigDecimal.class);

        Orcamento orcamentoAtivo = getOrcamentoAtivo(pessoa);
        query.setParameter("pessoa", pessoa);
        query.setParameter("inicio", orcamentoAtivo.getDataInicio());
        query.setParameter("fim", orcamentoAtivo.getDataFim());

        return query.getSingleResult();
    }

    @Override
    public BigDecimal calculateSaldoByPessoaAndOrcamentoAtivo(Pessoa pessoa) {
        BigDecimal totalGastos = calculateTotalGastosByPessoaAndOrcamentoAtivo(pessoa);
        BigDecimal totalLucros = calculateTotalLucrosByPessoaAndOrcamentoAtivo(pessoa);
        return totalLucros.subtract(totalGastos);
    }

    private Orcamento getOrcamentoAtivo(Pessoa pessoa) {
        TypedQuery<Orcamento> query = entityManager.createQuery(
                "SELECT o FROM Orcamento o WHERE o.pessoa = :pessoa AND o.ativo = true",
                Orcamento.class);
        query.setParameter("pessoa", pessoa);
        return query.getSingleResult();
    }
}
