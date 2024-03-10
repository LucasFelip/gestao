package com.financeiro.gestao.model;

import lombok.*;
import jakarta.persistence.*;
import java.util.Date;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "gasto")
public class Gasto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "pessoa_id")
    private Pessoa pessoa;
    private String descricao;
    @OneToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;
    private float valor;
    private Date data;
}