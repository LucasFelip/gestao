package com.financeiro.gestao.api.dto;

import com.financeiro.gestao.domain.model.enums.TipoCategoria;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaDTO {
    private Long id;
    private String nome;
    private String tipoCategoria;
}
