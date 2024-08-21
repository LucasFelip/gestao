package com.financeiro.gestao.domain.service;

import com.financeiro.gestao.domain.model.Categoria;
import com.financeiro.gestao.domain.model.TransacaoFinanceira;
import com.financeiro.gestao.domain.model.Usuario;
import com.financeiro.gestao.domain.repository.TransacaoFinanceiraRepository;
import com.financeiro.gestao.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class TransacaoFinanceiraService {

    @Autowired
    private TransacaoFinanceiraRepository transacaoFinanceiraRepository;

    @Autowired
    private PlanoOrcamentarioService planoOrcamentarioService;

    @Autowired
    private UserDetailsService userDetailsService;

    public TransacaoFinanceira findById(Long id) {
        Usuario currentUser = userDetailsService.userConnected();
        return transacaoFinanceiraRepository.findById(id)
                .filter(transacao -> transacao.getUsuario().equals(currentUser))
                .orElseThrow(() -> new ResourceNotFoundException("Transação não encontrada com o ID: ", id));
    }

    public List<TransacaoFinanceira> findAllByCurrentUser() {
        Usuario currentUser= userDetailsService.userConnected();
        return transacaoFinanceiraRepository.findByUsuario(currentUser);
    }

    public List<TransacaoFinanceira> findByCategoria(Categoria categoria) {
        Usuario currentUser = userDetailsService.userConnected();
        return transacaoFinanceiraRepository.findByUsuarioAndCategoria(currentUser, categoria);
    }

    public List<TransacaoFinanceira> findByDateRange(LocalDate startDate, LocalDate endDate) {
        Usuario currentUser = userDetailsService.userConnected();
        return transacaoFinanceiraRepository.findByUsuarioAndDataBetween(currentUser, startDate, endDate);
    }

    public BigDecimal sumByCategoriaAndDateRange(Categoria categoria, LocalDate startDate, LocalDate endDate) {
        Usuario currentUser= userDetailsService.userConnected();
        return transacaoFinanceiraRepository.sumByUsuarioAndCategoriaAndDataBetween(currentUser, categoria, startDate, endDate);
    }

    public TransacaoFinanceira createTransacao(TransacaoFinanceira transacao) {
        Usuario currentUser = userDetailsService.userConnected();
        transacao.setUsuario(currentUser);
        if(transacao.getData() == null) {
            transacao.setData(LocalDate.now());
        }
        transacao.validarTransacao();
        planoOrcamentarioService.calculaValores(transacao.getPlanoOrcamentario().getId());
        return transacaoFinanceiraRepository.save(transacao);
    }

    public Page<TransacaoFinanceira> findByUsuarioPageable(int page, int size) {
        Usuario currentUser = userDetailsService.userConnected();
        Pageable pageable = PageRequest.of(page, size, Sort.by("descricao").ascending());
        return transacaoFinanceiraRepository.findByUsuario(currentUser, pageable);
    }

    public List<TransacaoFinanceira> findTransacaoComValorMaiorQue(BigDecimal valor) {
        Usuario currentUser = userDetailsService.userConnected();
        return transacaoFinanceiraRepository.findTransacoesComValorMaiorQue(currentUser, valor);
    }

    @Transactional
    public TransacaoFinanceira updateTransacao(TransacaoFinanceira transacao) {
        TransacaoFinanceira existingTransacao= findById(transacao.getId());
        existingTransacao.setDescricao(transacao.getDescricao());
        existingTransacao.setCategoria(transacao.getCategoria());
        existingTransacao.setValor(transacao.getValor());
        existingTransacao.setData(transacao.getData());
        existingTransacao.validarTransacao();
        planoOrcamentarioService.calculaValores(existingTransacao.getPlanoOrcamentario().getId());
        return transacaoFinanceiraRepository.save(existingTransacao);
    }

    @Transactional
    public void deleteTransacao(Long id) {
        TransacaoFinanceira transacao = findById(id);
        transacaoFinanceiraRepository.delete(transacao);
        planoOrcamentarioService.calculaValores(transacao.getPlanoOrcamentario().getId());
    }
}
