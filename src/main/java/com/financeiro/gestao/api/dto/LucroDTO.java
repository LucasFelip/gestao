package com.financeiro.gestao.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LucroDTO {
    private Long id;
    private PessoaDTO pessoa;
    private String descricao;
    private BigDecimal valor;
    private String data;
    private CategoriaDTO categoria;
}
