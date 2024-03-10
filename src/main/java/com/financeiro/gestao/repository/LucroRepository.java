package com.financeiro.gestao.repository;

import com.financeiro.gestao.model.Lucro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface LucroRepository extends JpaRepository<Lucro, Long> {
    List<Lucro> findByPessoaId(Long pessoaId);
    List<Lucro> findByDataBetween(Date inicio, Date fim);
    List<Lucro> findByCategoriaId(Long categoriaId);
    List<Lucro> findByDescricaoContaining(String descricao);
    List<Lucro> findByValorGreaterThan(float valor);
    List<Lucro> findByPessoaIdAndCategoriaId(Long pessoaId, Long categoriaId);
}
