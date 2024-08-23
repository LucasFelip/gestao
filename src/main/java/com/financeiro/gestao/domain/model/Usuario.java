package com.financeiro.gestao.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.financeiro.gestao.domain.model.enums.Role;
import lombok.*;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String cpf;
    private String email;
    private String senha;
    private String telefone;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Role role = Role.USER;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "usuario")
    @JsonIgnore
    private List<TransacaoFinanceira> transacoes;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "usuario")
    @JsonIgnore
    private List<PlanoOrcamentario> planosOrcamentarios;
}
