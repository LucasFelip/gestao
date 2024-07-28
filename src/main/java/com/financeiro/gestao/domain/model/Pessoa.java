package com.financeiro.gestao.domain.model;

import java.util.List;
import lombok.*;
import jakarta.persistence.*;

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
    private String endereco;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "pessoa")
    private List<Gasto> gastos;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "pessoa")
    private List<Lucro> lucros;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "pessoa")
    private List<Orcamento> orcamentos;
}
