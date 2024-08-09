package com.financeiro.gestao.domain.model;

import lombok.*;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "lucro")
public class Lucro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "pessoa_id")
    private Pessoa pessoa;
    private String descricao;
    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;
    private BigDecimal valor;
    private LocalDate data;
}