package com.financeiro.gestao.repository;

import com.financeiro.gestao.model.Gasto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface GastoRepository extends JpaRepository<Gasto, Long> {
    List<Gasto> findByPessoaId(Long pessoaId);
    List<Gasto> findByDataBetween(Date inicio, Date fim);
    List<Gasto> findByCategoriaId(Long categoriaId);
    List<Gasto> findByDescricaoContaining(String descricao);
    List<Gasto> findByValorGreaterThan(float valor);
    List<Gasto> findByPessoaIdAndCategoriaId(Long pessoaId, Long categoriaId);
}
