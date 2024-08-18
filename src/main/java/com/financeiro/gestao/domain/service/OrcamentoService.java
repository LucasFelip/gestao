package com.financeiro.gestao.domain.service;

import com.financeiro.gestao.domain.model.Categoria;
import com.financeiro.gestao.domain.model.Orcamento;
import com.financeiro.gestao.domain.model.PlanoOrcamentario;
import com.financeiro.gestao.domain.model.Usuario;
import com.financeiro.gestao.domain.repository.OrcamentoRepository;
import com.financeiro.gestao.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrcamentoService {

    @Autowired
    private OrcamentoRepository orcamentoRepository;

    @Autowired
    private PlanoOrcamentarioService planoOrcamentarioService;

    @Autowired
    private UserDetailsService userDetailsService;

    public Orcamento findById(Long id) {
        Usuario currentUser = userDetailsService.userConnected();
        return orcamentoRepository.findById(id)
                .filter(orcamento -> orcamento.getPlanoOrcamentario().getUsuario().equals(currentUser))
                .orElseThrow(() -> new ResourceNotFoundException("Orçamento não encontrado com o ID: ", id));
    }

    public List<Orcamento> findAllByPlanoOrcamentario(Long planoOrcamentarioId) {
        Usuario currentUser = userDetailsService.userConnected();
        PlanoOrcamentario planoOrcamentario = planoOrcamentarioService.findById(planoOrcamentarioId);
        if (!planoOrcamentario.getUsuario().equals(currentUser)) {
            throw new ResourceNotFoundException("Plano orçamentário não encontrado ou não pertence ao usuário logado.", planoOrcamentario + " / " + currentUser);
        }
        return orcamentoRepository.findByPlanoOrcamentario(planoOrcamentario);
    }

    public List<Orcamento> findByPlanoOrcamentarioAndAtivoTrue(Long planoOrcamentarioId) {
        Usuario currentUser = userDetailsService.userConnected();
        PlanoOrcamentario planoOrcamentario = planoOrcamentarioService.findById(planoOrcamentarioId);
        if (!planoOrcamentario.getUsuario().equals(currentUser)) {
            throw new ResourceNotFoundException("Plano orçamentário não encontrado ou não pertence ao usuário logado.", planoOrcamentario + " / " + currentUser);
        }
        return orcamentoRepository.findByPlanoOrcamentarioAndAtivoTrue(planoOrcamentario);
    }

    public List<Orcamento> findByCategoria(Categoria categoria) {
        return orcamentoRepository.findByCategoria(categoria);
    }

    public List<Orcamento> findByCategoriaAndAtivoTrue(Categoria categoria) {
        return orcamentoRepository.findByCategoriaAndAtivoTrue(categoria);
    }

    @Transactional
    public Orcamento createOrcamento(Long planoOrcamentarioId, Orcamento orcamento) {
        Usuario currentUser = userDetailsService.userConnected();
        PlanoOrcamentario planoOrcamentario = planoOrcamentarioService.findById(planoOrcamentarioId);
        if (!planoOrcamentario.getUsuario().equals(currentUser)) {
            throw new ResourceNotFoundException("Plano orçamentário não encontrado ou não pertence ao usuário logado.", planoOrcamentario + " / " + currentUser);
        }
        orcamento.setPlanoOrcamentario(planoOrcamentario);

        List<Orcamento> orcamentosAtivos = orcamentoRepository.findByPlanoOrcamentarioAndCategoriaAndAtivoTrue(planoOrcamentario, orcamento.getCategoria());
        if (!orcamentosAtivos.isEmpty()) {
            throw new IllegalArgumentException("Já existe um orçamento ativo para esta categoria no plano orçamentário.");
        }

        orcamento.validarOrcamento();
        return orcamentoRepository.save(orcamento);
    }

    @Transactional
    public String desativarOrcamento(Long id) {
        Orcamento orcamento = findById(id);
        if (orcamento.isAtivo()) {
            orcamento.desativar();
            return "Orçamento desativo com sucesso";
        }
        return "Erro ao desativar orçamento, verifique se o mesmo esta ativo";
    }


    @Transactional
    public Orcamento updateOrcamento(Long id, Orcamento orcamento) {
        Orcamento existingOrcamento = findById(id);
        existingOrcamento.setCategoria(orcamento.getCategoria());
        existingOrcamento.setValorPrevisto(orcamento.getValorPrevisto());
        existingOrcamento.setAtivo(orcamento.isAtivo());
        orcamento.validarOrcamento();
        return orcamentoRepository.save(existingOrcamento);
    }
}
