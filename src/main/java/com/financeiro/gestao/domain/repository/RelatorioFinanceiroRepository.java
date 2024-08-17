package com.financeiro.gestao.domain.repository;

import com.financeiro.gestao.domain.model.Categoria;
import com.financeiro.gestao.domain.model.RelatorioFinanceiro;
import com.financeiro.gestao.domain.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.time.LocalDate;

@Repository
public interface RelatorioFinanceiroRepository extends JpaRepository<RelatorioFinanceiro, Long> {
    List<RelatorioFinanceiro> findByUsuario(Usuario usuario);
    List<RelatorioFinanceiro> findByUsuarioAndDataInicioGreaterThanEqualAndDataFimLessThanEqual(
            Usuario usuario, LocalDate dataInicio, LocalDate dataFim);
    @Query("SELECT SUM(t.valor) FROM TransacaoFinanceira t WHERE t.usuario = :usuario AND t.categoria = :categoria")
    BigDecimal somaTransacoesPorCategoria(@Param("usuario") Usuario usuario, @Param("categoria") Categoria categoria);

}

