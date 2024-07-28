package com.financeiro.gestao.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrcamentoDTO {
    private Long id;
    private String dataInicio;
    private String dataFim;
    private Double limite;
}
