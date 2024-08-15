package com.financeiro.gestao.domain.model;

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

    public void validarTransacao(TransacaoFinanceira transacao) {
        if (transacao.getValor().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor da transação deve ser positivo.");
        }
        if (transacao.getData().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("A data da transação não pode ser no futuro.");
        }
    }
}
