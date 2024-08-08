package com.financeiro.gestao.domain.model;

import lombok.*;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "Pessoa")
public class Pessoa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String cpf;
    private String email;
    private String senha;
    private String telefone;
    @Enumerated(EnumType.STRING)
    private Role roles = Role.USER;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "pessoa")
    private List<Gasto> gastos;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "pessoa")
    private List<Lucro> lucros;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "pessoa")
    private List<Orcamento> orcamentos;
}
