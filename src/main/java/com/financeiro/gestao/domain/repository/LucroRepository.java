package com.financeiro.gestao.domain.repository;

import com.financeiro.gestao.domain.model.Categoria;
import com.financeiro.gestao.domain.model.Lucro;
import com.financeiro.gestao.domain.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface LucroRepository extends JpaRepository<Lucro, Long> {
    List<Lucro> findByPessoa(Pessoa pessoa);
    List<Lucro> findByDataBetween(LocalDate inicio, LocalDate fim);
    List<Lucro> findByCategoria(Categoria categoria);
    List<Lucro> findByDescricaoContaining(String descricao);
    List<Lucro> findByValorGreaterThan(BigDecimal valor);
    List<Lucro> findByPessoaIdAndCategoriaId(Long pessoaId, Long categoriaId);
}
