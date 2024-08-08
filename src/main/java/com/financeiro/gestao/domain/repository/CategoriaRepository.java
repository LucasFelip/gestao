package com.financeiro.gestao.domain.repository;

import com.financeiro.gestao.domain.model.Categoria;
import com.financeiro.gestao.domain.model.enums.TipoCategoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    Optional<Categoria> findByNome(String nome);
    List<Categoria> findByNomeContaining(String nome);
    boolean existsByNome(String nome);
    List<Categoria> findCategoriasByTipoCategoria(TipoCategoria tipoCategoria);
}
