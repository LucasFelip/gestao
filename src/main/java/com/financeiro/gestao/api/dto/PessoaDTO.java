package com.financeiro.gestao.api.dto;

import com.financeiro.gestao.domain.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PessoaDTO {
    private Long id;
    private String nome;
    private String cpf;
    private String email;
    private String telefone;
    private String role;
}
