package com.financeiro.gestao.domain.repository;

import com.financeiro.gestao.domain.model.Categoria;
import com.financeiro.gestao.domain.model.Lucro;
import com.financeiro.gestao.domain.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface LucroRepository extends JpaRepository<Lucro, Long> {
    List<Lucro> findByPessoa(Pessoa pessoa);
    List<Lucro> findByPessoaAndDataBetween(Pessoa pessoa, LocalDate inicio, LocalDate fim);
    List<Lucro> findByPessoaAndCategoria(Pessoa pessoa, Categoria categoria);
    List<Lucro> findByPessoaAndDescricaoContaining(Pessoa pessoa, String descricao);
    List<Lucro> findByPessoaAndValorGreaterThan(Pessoa pessoa, BigDecimal valor);
    List<Lucro> findByPessoaAndDataAfter(Pessoa pessoa, LocalDate data);
    List<Lucro> findByPessoaAndDataBefore(Pessoa pessoa, LocalDate data);
    @Query("SELECT SUM(l.valor) FROM Lucro l WHERE l.pessoa = :pessoa AND l.data BETWEEN :inicio AND :fim")
    BigDecimal findSumValorByPessoaAndDataBetween(@Param("pessoa") Pessoa pessoa, @Param("inicio") LocalDate inicio, @Param("fim") LocalDate fim);
}
