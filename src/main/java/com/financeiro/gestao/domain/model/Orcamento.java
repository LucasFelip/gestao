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

    @Builder.Default
    private boolean ativo = true;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    public void desativar() {
        this.ativo = false;
    }

    public void validarOrcamento() {
        if (valorPrevisto.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor previsto deve ser positivo.");
        }

        if (categoria == null) {
            throw new IllegalArgumentException("A categoria do orçamento não pode ser nula.");
        }
    }
}
