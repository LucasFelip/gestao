package com.financeiro.gestao.domain.repository;

import com.financeiro.gestao.domain.model.PlanoOrcamentario;
import com.financeiro.gestao.domain.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.time.LocalDate;

@Repository
public interface PlanoOrcamentarioRepository extends JpaRepository<PlanoOrcamentario, Long> {
    List<PlanoOrcamentario> findByUsuario(Usuario usuario);
    Optional<PlanoOrcamentario> findByUsuarioAndId(Usuario usuario, Long id);
    List<PlanoOrcamentario> findByUsuarioAndAtivoTrue(Usuario usuario);
    List<PlanoOrcamentario> findByUsuarioAndDataInicioGreaterThanEqualAndDataFimLessThanEqual(Usuario usuario, LocalDate dataInicio, LocalDate dataFim);
    List<PlanoOrcamentario> findByUsuarioAndAtivoTrueAndDataInicioGreaterThanEqualAndDataFimLessThanEqual(
            Usuario usuario, LocalDate dataInicio, LocalDate dataFim);
    List<PlanoOrcamentario> findByUsuarioAndNomeContaining(Usuario usuario, String nome);
}
