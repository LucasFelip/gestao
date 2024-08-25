package com.financeiro.gestao.domain.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class OrcamentoTest {

    @Test
    public void testOrcamentoCreation() {
        Categoria categoria = Categoria.builder()
                .id(1L)
                .nome("Alimentação")
                .build();

        PlanoOrcamentario planoOrcamentario = new PlanoOrcamentario();
        planoOrcamentario.setId(1L);

        Orcamento orcamento = Orcamento.builder()
                .id(1L)
                .valorPrevisto(new BigDecimal("1000.00"))
                .categoria(categoria)
                .planoOrcamentario(planoOrcamentario)
                .build();

        assertThat(orcamento.getId()).isEqualTo(1L);
        assertThat(orcamento.getValorPrevisto()).isEqualByComparingTo(new BigDecimal("1000.00"));
        assertThat(orcamento.isAtivo()).isTrue();
        assertThat(orcamento.getCategoria()).isEqualTo(categoria);
        assertThat(orcamento.getPlanoOrcamentario()).isEqualTo(planoOrcamentario);
    }

    @Test
    public void testOrcamentoDesativar() {
        Orcamento orcamento = Orcamento.builder()
                .id(1L)
                .valorPrevisto(new BigDecimal("1000.00"))
                .ativo(true)
                .build();

        orcamento.desativar();

        assertThat(orcamento.isAtivo()).isFalse();
    }

    @Test
    public void testOrcamentoValidarOrcamento() {
        Categoria categoria = Categoria.builder()
                .id(1L)
                .nome("Alimentação")
                .build();

        Orcamento orcamento = Orcamento.builder()
                .id(1L)
                .valorPrevisto(new BigDecimal("1000.00"))
                .categoria(categoria)
                .build();

        orcamento.validarOrcamento();  // Não deve lançar exceção

        orcamento.setValorPrevisto(new BigDecimal("0.00"));
        assertThatThrownBy(orcamento::validarOrcamento)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("O valor previsto deve ser positivo.");

        orcamento.setValorPrevisto(new BigDecimal("1000.00"));
        orcamento.setCategoria(null);
        assertThatThrownBy(orcamento::validarOrcamento)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("A categoria do orçamento não pode ser nula.");
    }

    @Test
    public void testOrcamentoEqualsAndHashCode() {
        Categoria categoria = Categoria.builder()
                .id(1L)
                .nome("Alimentação")
                .build();

        Orcamento orcamento1 = Orcamento.builder()
                .id(1L)
                .valorPrevisto(new BigDecimal("1000.00"))
                .categoria(categoria)
                .build();

        Orcamento orcamento2 = Orcamento.builder()
                .id(1L)
                .valorPrevisto(new BigDecimal("1000.00"))
                .categoria(categoria)
                .build();

        assertThat(orcamento1).isEqualTo(orcamento2);
        assertThat(orcamento1.hashCode()).isEqualTo(orcamento2.hashCode());
    }

    @Test
    public void testOrcamentoNotEqual() {
        Categoria categoria = Categoria.builder()
                .id(1L)
                .nome("Alimentação")
                .build();

        Orcamento orcamento1 = Orcamento.builder()
                .id(1L)
                .valorPrevisto(new BigDecimal("1000.00"))
                .categoria(categoria)
                .build();

        Orcamento orcamento2 = Orcamento.builder()
                .id(2L)
                .valorPrevisto(new BigDecimal("1500.00"))
                .categoria(categoria)
                .build();

        assertThat(orcamento1).isNotEqualTo(orcamento2);
    }
}

