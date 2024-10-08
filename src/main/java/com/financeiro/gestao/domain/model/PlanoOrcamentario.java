package com.financeiro.gestao.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.financeiro.gestao.domain.model.enums.TipoTransacao;
import lombok.*;
import jakarta.persistence.*;

import java.math.BigDecimal;
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
    @JsonIgnore
    private Usuario usuario;

    private LocalDate dataInicio;
    private LocalDate dataFim;

    private BigDecimal valorPrevisto;
    private BigDecimal valorDespesa;
    private BigDecimal valorReceita;
    private BigDecimal valorRealizado;

    @Builder.Default
    private boolean ativo = true;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "planoOrcamentario")
    @JsonIgnore
    private List<Orcamento> orcamentos;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "planoOrcamentario")
    @JsonIgnore
    private List<TransacaoFinanceira> transacoes;

    @PrePersist
    @PreUpdate
    public void calcularValores() {
        if (!ativo) return;

        valorPrevisto = orcamentos.stream()
                .map(Orcamento::getValorPrevisto)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        valorDespesa = transacoes.stream()
                .filter(transacao -> transacao.getTipoTransacao() == TipoTransacao.DESPESA &&
                        !transacao.getData().isBefore(dataInicio) && !transacao.getData().isAfter(dataFim))
                .map(TransacaoFinanceira::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        valorReceita = transacoes.stream()
                .filter(transacao -> transacao.getTipoTransacao() == TipoTransacao.RECEITA &&
                        !transacao.getData().isBefore(dataInicio) && !transacao.getData().isAfter(dataFim))
                .map(TransacaoFinanceira::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        valorRealizado = valorReceita.subtract(valorDespesa);
    }

    public BigDecimal calcularImpactoLucro() {
        if (!ativo) return BigDecimal.ZERO;
        return valorRealizado.subtract(valorPrevisto);
    }

    public void desativarPlano() {
        this.ativo = false;
    }
}
