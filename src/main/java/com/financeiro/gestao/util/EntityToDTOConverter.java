package com.financeiro.gestao.util;

import com.financeiro.gestao.api.dto.*;
import com.financeiro.gestao.domain.model.*;

public class EntityToDTOConverter {

    public static LucroDTO convertToDTO(Lucro lucro) {
        return LucroDTO.builder()
                .id(lucro.getId())
                .pessoa(convertToDTO(lucro.getPessoa()))
                .descricao(lucro.getDescricao())
                .valor(lucro.getValor())
                .data(lucro.getData().toString())
                .categoria(convertToDTO(lucro.getCategoria()))
                .build();
    }

    public static GastoDTO convertToDTO(Gasto gasto) {
        return GastoDTO.builder()
                .id(gasto.getId())
                .pessoa(convertToDTO(gasto.getPessoa()))
                .descricao(gasto.getDescricao())
                .valor(gasto.getValor())
                .data(gasto.getData().toString())
                .categoria(convertToDTO(gasto.getCategoria()))
                .build();
    }

    public static CategoriaDTO convertToDTO(Categoria categoria) {
        return CategoriaDTO.builder()
                .id(categoria.getId())
                .nome(categoria.getNome())
                .build();
    }

    public static PessoaDTO convertToDTO(Pessoa pessoa) {
        return PessoaDTO.builder()
                .id(pessoa.getId())
                .nome(pessoa.getNome())
                .cpf(pessoa.getCpf())
                .email(pessoa.getEmail())
                .telefone(pessoa.getTelefone())
                .build();
    }

    public static OrcamentoDTO convertToDTO(Orcamento orcamento) {
        return OrcamentoDTO.builder()
                .id(orcamento.getId())
                .dataInicio(orcamento.getDataInicio().toString())
                .dataFim(orcamento.getDataFim().toString())
                .limite(orcamento.getLimite())
                .build();
    }
}
