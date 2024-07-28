package com.financeiro.gestao.api.dto;

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
    private String endereco;
}
