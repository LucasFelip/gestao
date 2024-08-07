package com.financeiro.gestao.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrcamentoDTO {
    private Long id;
    private String dataInicio;
    private String dataFim;
    private BigDecimal limite;
}
