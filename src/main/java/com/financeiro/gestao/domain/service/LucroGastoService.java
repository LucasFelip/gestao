package com.financeiro.gestao.domain.service;

import com.financeiro.gestao.domain.exception.BusinessRuleException;
import com.financeiro.gestao.domain.exception.ResourceNotFoundException;
import com.financeiro.gestao.domain.model.Categoria;
import com.financeiro.gestao.domain.model.Gasto;
import com.financeiro.gestao.domain.model.Lucro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LucroGastoService {

    @Autowired
    private LucroService lucroService;
    @Autowired
    private GastoService gastoService;
    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;
    @Autowired
    private CategoriaService categoriaService;

    @Transactional
    public Lucro salvarLucro(Lucro lucro) {
        try {
            Categoria categoria = categoriaService.findCategoriaById(lucro.getCategoria().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada com o ID: " + lucro.getCategoria().getId()));
            lucro.setPessoa(userDetailsServiceImpl.userConnected());
            lucro.setCategoria(categoria);
            return lucroService.save(lucro);
        } catch (ResourceNotFoundException e) {
            throw e; // Re-lança a exceção de recurso não encontrado
        } catch (Exception e) {
            throw new BusinessRuleException("Erro ao salvar o lucro: " + e.getMessage());
        }
    }

    @Transactional
    public Gasto salvarGasto(Gasto gasto) {
        try {
            Categoria categoria = categoriaService.findCategoriaById(gasto.getCategoria().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada com o ID: " + gasto.getCategoria().getId()));
            gasto.setPessoa(userDetailsServiceImpl.userConnected());
            gasto.setCategoria(categoria);
            return gastoService.save(gasto);
        } catch (ResourceNotFoundException e) {
            throw e; // Re-lança a exceção de recurso não encontrado
        } catch (Exception e) {
            throw new BusinessRuleException("Erro ao salvar o gasto: " + e.getMessage());
        }
    }
}
