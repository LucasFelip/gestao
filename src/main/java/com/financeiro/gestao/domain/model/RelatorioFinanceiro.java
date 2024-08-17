package com.financeiro.gestao.domain.model;

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
@Table(name = "relatorio_financeiro")
public class RelatorioFinanceiro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private LocalDate dataInicio;
    private LocalDate dataFim;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "transacao_financeira_id")
    private List<TransacaoFinanceira> transacoes;

    private BigDecimal totalDespesa;
    private BigDecimal totalReceita;
    private BigDecimal saldoFinal;

    @ManyToOne
    @JoinColumn(name = "plano_orcamentario_id")
    private PlanoOrcamentario planoOrcamentario;

    @PrePersist
    @PreUpdate
    private void calcularTotais() {
        totalDespesa = transacoes.stream()
                .filter(transacao -> transacao.getTipoTransacao() == TipoTransacao.DESPESA)
                .map(TransacaoFinanceira::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        totalReceita = transacoes.stream()
                .filter(transacao -> transacao.getTipoTransacao() == TipoTransacao.RECEITA)
                .map(TransacaoFinanceira::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        saldoFinal = totalReceita.subtract(totalDespesa);
    }
}
