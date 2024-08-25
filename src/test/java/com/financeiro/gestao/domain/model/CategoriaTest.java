package com.financeiro.gestao.domain.model;

import com.financeiro.gestao.domain.model.enums.TipoCategoria;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CategoriaTest {

    @Test
    public void testCategoriaCreation() {
        Categoria categoria = Categoria.builder()
                .id(1L)
                .nome("Alimentação")
                .tipoCategoria(TipoCategoria.DESPESA)
                .build();

        assertThat(categoria.getId()).isEqualTo(1L);
        assertThat(categoria.getNome()).isEqualTo("Alimentação");
        assertThat(categoria.getTipoCategoria()).isEqualTo(TipoCategoria.DESPESA);
        assertThat(categoria.getCategoriaPai()).isNull();  // Subcategoria não definida
    }

    @Test
    public void testCategoriaWithParent() {
        Categoria parentCategory = Categoria.builder()
                .id(1L)
                .nome("Despesas Gerais")
                .tipoCategoria(TipoCategoria.DESPESA)
                .build();

        Categoria childCategory = Categoria.builder()
                .id(2L)
                .nome("Alimentação")
                .tipoCategoria(TipoCategoria.DESPESA)
                .categoriaPai(parentCategory)
                .build();

        assertThat(childCategory.getCategoriaPai()).isEqualTo(parentCategory);
        assertThat(parentCategory.getNome()).isEqualTo("Despesas Gerais");
        assertThat(childCategory.getNome()).isEqualTo("Alimentação");
    }

    @Test
    public void testCategoriaEqualsAndHashCode() {
        Categoria categoria1 = Categoria.builder()
                .id(1L)
                .nome("Alimentação")
                .tipoCategoria(TipoCategoria.DESPESA)
                .build();

        Categoria categoria2 = Categoria.builder()
                .id(1L)
                .nome("Alimentação")
                .tipoCategoria(TipoCategoria.DESPESA)
                .build();

        assertThat(categoria1).isEqualTo(categoria2);
        assertThat(categoria1.hashCode()).isEqualTo(categoria2.hashCode());
    }

    @Test
    public void testCategoriaNotEqual() {
        Categoria categoria1 = Categoria.builder()
                .id(1L)
                .nome("Alimentação")
                .tipoCategoria(TipoCategoria.DESPESA)
                .build();

        Categoria categoria2 = Categoria.builder()
                .id(2L)
                .nome("Transporte")
                .tipoCategoria(TipoCategoria.DESPESA)
                .build();

        assertThat(categoria1).isNotEqualTo(categoria2);
    }
}
