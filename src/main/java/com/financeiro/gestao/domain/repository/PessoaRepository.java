package com.financeiro.gestao.domain.repository;

import com.financeiro.gestao.domain.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
    Optional<Pessoa> findByCpf(String cpf);
    Optional<Pessoa> findByEmail(String email);
    List<Pessoa> findByNomeContaining(String nome);
    Optional<Pessoa> findByNomeAndEmail(String nome, String email);
    boolean existsByCpf(String cpf);
    boolean existsByEmail(String email);
    Optional<Pessoa> findByEmailAndSenha(String email, String senha);
}
