package com.financeiro.gestao.domain.repository;

import com.financeiro.gestao.domain.model.Categoria;
import com.financeiro.gestao.domain.model.enums.TipoCategoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    Categoria findByNome(String nome);
    boolean existsByNome(String nome);
    List<Categoria> findByNomeContaining(String nome);
    List<Categoria> findCategoriasByTipoCategoria(TipoCategoria tipoCategoria);
    Page<Categoria> findCategoriasByTipoCategoria(TipoCategoria tipoCategoria, Pageable pageable);
}

