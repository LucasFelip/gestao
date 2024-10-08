package com.financeiro.gestao.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.financeiro.gestao.domain.model.enums.TipoTransacao;
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
@Table(name = "transacao_financeira")
public class TransacaoFinanceira {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    @JsonIgnore
    private Usuario usuario;

    private String descricao;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    private BigDecimal valor;
    private LocalDate data;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoTransacao tipoTransacao;

    @ManyToOne
    @JoinColumn(name = "plano_orcamentario_id")
    @JsonIgnore
    private PlanoOrcamentario planoOrcamentario;

    public void validarTransacao() {
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor da transação deve ser positivo.");
        }
        if (data.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("A data da transação não pode ser no futuro.");
        }
    }
}
