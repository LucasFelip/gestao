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
    List<Lucro> findByDataBetween(LocalDate inicio, LocalDate fim);
    List<Lucro> findByCategoria(Categoria categoria);
    List<Lucro> findByDescricaoContaining(String descricao);
    List<Lucro> findByValorGreaterThan(BigDecimal valor);
    List<Lucro> findByPessoaIdAndCategoriaId(Long pessoaId, Long categoriaId);
    List<Lucro> findByDataAfter(LocalDate data);
    List<Lucro> findByDataBefore(LocalDate data);
    @Query("SELECT SUM(l.valor) FROM Lucro l WHERE l.pessoa = :pessoa AND l.data BETWEEN :inicio AND :fim")
    BigDecimal findSumValorByPessoaAndDataBetween(@Param("pessoa") Pessoa pessoa, @Param("inicio") LocalDate inicio, @Param("fim") LocalDate fim);
}
