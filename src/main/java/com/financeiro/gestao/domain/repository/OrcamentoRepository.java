package com.financeiro.gestao.domain.repository;

import com.financeiro.gestao.domain.model.Categoria;
import com.financeiro.gestao.domain.model.Orcamento;
import com.financeiro.gestao.domain.model.PlanoOrcamentario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrcamentoRepository extends JpaRepository<Orcamento, Long> {
    List<Orcamento> findByPlanoOrcamentario(PlanoOrcamentario planoOrcamentario);
    List<Orcamento> findByPlanoOrcamentarioAndAtivoTrue(PlanoOrcamentario planoOrcamentario);
    List<Orcamento> findByCategoria(Categoria categoria);
    List<Orcamento> findByCategoriaAndAtivoTrue(Categoria categoria);
}
