package com.financeiro.gestao.domain.repository.impl;

import com.financeiro.gestao.domain.model.Gasto;
import com.financeiro.gestao.domain.model.Lucro;
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
                        "SELECT SUM(g.valor) FROM Gasto g WHERE g.pessoa = :pessoa AND g.data BETWEEN :inicio AND :fim",
                        BigDecimal.class)
                .setParameter("pessoa", pessoa)
                .setParameter("inicio", inicio)
                .setParameter("fim", fim)
                .getSingleResult();

        BigDecimal totalLucros = entityManager.createQuery(
                        "SELECT SUM(l.valor) FROM Lucro l WHERE l.pessoa = :pessoa AND l.data BETWEEN :inicio AND :fim",
                        BigDecimal.class)
                .setParameter("pessoa", pessoa)
                .setParameter("inicio", inicio)
                .setParameter("fim", fim)
                .getSingleResult();

        return totalLucros.subtract(totalGastos);
    }
}
