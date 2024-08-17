package com.financeiro.gestao.domain.repository;

import com.financeiro.gestao.domain.model.TransacaoFinanceira;
import com.financeiro.gestao.domain.model.Usuario;
import com.financeiro.gestao.domain.model.Categoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransacaoFinanceiraRepository extends JpaRepository<TransacaoFinanceira, Long> {
    List<TransacaoFinanceira> findByUsuario(Usuario usuario);
    List<TransacaoFinanceira> findByUsuarioAndDataBetween(Usuario usuario, LocalDate startDate, LocalDate endDate);
    List<TransacaoFinanceira> findByUsuarioAndCategoria(Usuario usuario, Categoria categoria);
    @Query("SELECT SUM(t.valor) FROM TransacaoFinanceira t WHERE t.usuario = :usuario AND t.categoria = :categoria AND t.data BETWEEN :startDate AND :endDate")
    BigDecimal sumByUsuarioAndCategoriaAndDataBetween(@Param("usuario") Usuario usuario, @Param("categoria") Categoria categoria, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    Page<TransacaoFinanceira> findByUsuario(Usuario usuario, Pageable pageable);
    @Query("SELECT t FROM TransacaoFinanceira t WHERE t.usuario = :usuario AND t.valor > :valorMinimo")
    List<TransacaoFinanceira> findTransacoesComValorMaiorQue(@Param("usuario") Usuario usuario, @Param("valorMinimo") BigDecimal valorMinimo);

}

