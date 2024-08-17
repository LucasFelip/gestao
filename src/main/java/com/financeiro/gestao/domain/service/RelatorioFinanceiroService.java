package com.financeiro.gestao.domain.service;

import com.financeiro.gestao.domain.model.Categoria;
import com.financeiro.gestao.domain.model.RelatorioFinanceiro;
import com.financeiro.gestao.domain.model.Usuario;
import com.financeiro.gestao.domain.repository.RelatorioFinanceiroRepository;
import com.financeiro.gestao.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class RelatorioFinanceiroService {

    @Autowired
    private RelatorioFinanceiroRepository relatorioFinanceiroRepository;

    @Autowired
    private UserDetailsService userDetailsService;

    public RelatorioFinanceiro findById(Long id) {
        Usuario currentUser = userDetailsService.userConnected();
        return relatorioFinanceiroRepository.findById(id)
                .filter(relatorio -> relatorio.getUsuario().equals(currentUser))
                .orElseThrow(() -> new ResourceNotFoundException("Relatório não encontrado com o ID: ", id));
    }

    public List<RelatorioFinanceiro> findAllByCurrentUser() {
        Usuario currentUser = userDetailsService.userConnected();
        return relatorioFinanceiroRepository.findByUsuario(currentUser);
    }

    public List<RelatorioFinanceiro> findByDateRange(LocalDate startDate, LocalDate endDate) {
        Usuario currentUser = userDetailsService.userConnected();
        return relatorioFinanceiroRepository.findByUsuarioAndDataInicioGreaterThanEqualAndDataFimLessThanEqual(currentUser, startDate, endDate);
    }

    public BigDecimal calcularSomaPorCategoria(Categoria categoria) {
        Usuario currentUser = userDetailsService.userConnected();
        return relatorioFinanceiroRepository.somaTransacoesPorCategoria(currentUser, categoria);
    }

    public RelatorioFinanceiro createRelatorio(RelatorioFinanceiro relatorio) {
        Usuario currentUser = userDetailsService.userConnected();
        relatorio.setUsuario(currentUser);
        return relatorioFinanceiroRepository.save(relatorio);
    }

    @Transactional
    public RelatorioFinanceiro updateRelatorio(Long id, RelatorioFinanceiro relatorio) {
        RelatorioFinanceiro existingRelatorio = findById(id);
        existingRelatorio.setTitulo(relatorio.getTitulo());
        existingRelatorio.setDataInicio(relatorio.getDataInicio());
        existingRelatorio.setDataFim(relatorio.getDataFim());
        existingRelatorio.setTransacoes(relatorio.getTransacoes());
        existingRelatorio.setTotalDespesa(relatorio.getTotalDespesa());
        existingRelatorio.setTotalReceita(relatorio.getTotalReceita());
        existingRelatorio.setSaldoFinal(relatorio.getSaldoFinal());
        return relatorioFinanceiroRepository.save(existingRelatorio);
    }

    @Transactional
    public void deleteRelatorio(Long id) {
        RelatorioFinanceiro relatorio = findById(id);
        relatorioFinanceiroRepository.delete(relatorio);
    }
}
