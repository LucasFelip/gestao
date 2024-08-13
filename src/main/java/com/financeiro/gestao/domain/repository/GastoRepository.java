package com.financeiro.gestao.domain.repository;

import com.financeiro.gestao.domain.model.Categoria;
import com.financeiro.gestao.domain.model.Gasto;
import com.financeiro.gestao.domain.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface GastoRepository extends JpaRepository<Gasto, Long> {
    List<Gasto> findByPessoa(Pessoa pessoa);
    List<Gasto> findByPessoaAndDataBetween(Pessoa pessoa, LocalDate inicio, LocalDate fim);
    List<Gasto> findByPessoaAndDescricaoContaining(Pessoa pessoa, String descricao);
    List<Gasto> findByPessoaAndValorGreaterThan(Pessoa pessoa, BigDecimal valor);
    List<Gasto> findByPessoaAndCategoria(Pessoa pessoa, Categoria categoria);
    List<Gasto> findByPessoaAndDataAfter(Pessoa pessoa, LocalDate data);
    List<Gasto> findByPessoaAndDataBefore(Pessoa pessoa, LocalDate data);
    @Query("SELECT SUM(g.valor) FROM Gasto g WHERE g.pessoa = :pessoa AND g.data BETWEEN :inicio AND :fim")
    BigDecimal findSumValorByPessoaAndDataBetween(@Param("pessoa") Pessoa pessoa, @Param("inicio") LocalDate inicio, @Param("fim") LocalDate fim);
}
