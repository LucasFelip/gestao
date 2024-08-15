package com.financeiro.gestao.domain.model;

import lombok.*;
import jakarta.persistence.*;

import java.math.BigDecimal;

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
    @JoinColumn(name = "plano_orcamentario_id")
    private PlanoOrcamentario planoOrcamentario;

    private BigDecimal valorPrevisto;
    private BigDecimal valorRealizado;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;
}
