package com.financeiro.gestao.domain.model;

import com.financeiro.gestao.domain.model.enums.TipoCategoria;
import lombok.*;
import jakarta.persistence.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "categoria")
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    @Enumerated(EnumType.STRING)
    private TipoCategoria tipoCategoria;
}