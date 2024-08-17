package com.financeiro.gestao.domain.repository;

import com.financeiro.gestao.domain.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
    boolean existsByCpf(String cpf);
    boolean existsByEmail(String email);
    List<Usuario> findByNomeContaining(String nome);
    Optional<Usuario> findByNomeOrEmail(String username, String email);
}
