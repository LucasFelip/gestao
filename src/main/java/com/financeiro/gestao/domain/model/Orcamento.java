package com.financeiro.gestao.domain.model;

import lombok.*;
import jakarta.persistence.*;
import java.util.Date;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "orcamento")
public class Orcamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "pessoa_id")
    private Pessoa pessoa;
    private Date dataInicio;
    private Date dataFim;
    private Double limite;
}