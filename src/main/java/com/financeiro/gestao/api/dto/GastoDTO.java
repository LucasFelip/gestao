package com.financeiro.gestao.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GastoDTO {
    private Long id;
    private PessoaDTO pessoa;
    private String descricao;
    private Double valor;
    private String data;
    private CategoriaDTO categoria;
}
