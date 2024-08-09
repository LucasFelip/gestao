package com.financeiro.gestao.domain.service;

import com.financeiro.gestao.domain.exception.ResourceNotFoundException;
import com.financeiro.gestao.domain.model.Categoria;
import com.financeiro.gestao.domain.model.Lucro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LucroGastoService {

    private final LucroService lucroService;
    private final GastoService gastoService;
    private final UserDetailsServiceImpl  userDetailsServiceImpl;
    private final CategoriaService categoriaService;

    @Autowired
    public LucroGastoService(LucroService lucroService, GastoService gastoService, UserDetailsServiceImpl userDetailsServiceImpl, CategoriaService categoriaService) {
        this.lucroService = lucroService;
        this.gastoService = gastoService;
        this.userDetailsServiceImpl = userDetailsServiceImpl;
        this.categoriaService = categoriaService;
    }

    public Lucro salvarLucro(Lucro lucro) {
        Categoria categoria = categoriaService.findCategoriaById(lucro.getCategoria().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada com o ID: " + lucro.getCategoria().getId()));
        lucro.setPessoa(userDetailsServiceImpl.userConnected());
        lucro.setCategoria(categoria);
        return lucroService.save(lucro);
    }

}
