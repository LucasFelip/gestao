package com.financeiro.gestao.domain.model;

import lombok.*;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "plano_orcamentario")
public class PlanoOrcamentario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    private LocalDate dataInicio;
    private LocalDate dataFim;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "planoOrcamentario")
    private List<Orcamento> orcamentos;
}
