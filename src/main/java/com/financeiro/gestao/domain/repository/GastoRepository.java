package com.financeiro.gestao.domain.repository;

import com.financeiro.gestao.domain.model.Categoria;
import com.financeiro.gestao.domain.model.Gasto;
import com.financeiro.gestao.domain.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface GastoRepository extends JpaRepository<Gasto, Long> {
    List<Gasto> findByPessoa(Pessoa pessoa);
    List<Gasto> findByDataBetween(LocalDate inicio, LocalDate fim);
    List<Gasto> findByCategoria(Categoria categoria);
    List<Gasto> findByDescricaoContaining(String descricao);
    List<Gasto> findByValorGreaterThan(BigDecimal valor);
    List<Gasto> findByPessoaAndCategoria(Pessoa pessoa, Categoria categoria);
    List<Gasto> findByDataAfter(LocalDate data);
    List<Gasto> findByDataBefore(LocalDate data);
    BigDecimal findSumValorByPessoaAndDataBetween(Pessoa pessoa, LocalDate inicio, LocalDate fim);
}

