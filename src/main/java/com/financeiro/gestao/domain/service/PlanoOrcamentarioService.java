package com.financeiro.gestao.domain.service;

import com.financeiro.gestao.domain.model.PlanoOrcamentario;
import com.financeiro.gestao.domain.model.Usuario;
import com.financeiro.gestao.domain.repository.PlanoOrcamentarioRepository;
import com.financeiro.gestao.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class PlanoOrcamentarioService {

    @Autowired
    private PlanoOrcamentarioRepository planoOrcamentarioRepository;

    @Autowired
    private UserDetailsService userDetailsService;

    public PlanoOrcamentario findById(Long id) {
        Usuario currentUser = userDetailsService.userConnected();
        return planoOrcamentarioRepository.findById(id)
                .filter(plano -> plano.getUsuario().equals(currentUser))
                .orElseThrow(() -> new ResourceNotFoundException("Plano orçamentário não encontrado com o ID: ", id));
    }

    public List<PlanoOrcamentario> findAllByCurrentUser() {
        Usuario currentUser = userDetailsService.userConnected();
        return planoOrcamentarioRepository.findByUsuario(currentUser);
    }


    public List<PlanoOrcamentario> findByNomeContaining(String nome) {
        Usuario currentUser = userDetailsService.userConnected();
        return planoOrcamentarioRepository.findByUsuarioAndNomeContaining(currentUser, nome);
    }

    public List<PlanoOrcamentario> findActiveByDateRange(LocalDate startDate, LocalDate endDate) {
        Usuario currentUser = userDetailsService.userConnected();
        return planoOrcamentarioRepository.findByUsuarioAndAtivoTrueAndDataInicioGreaterThanEqualAndDataFimLessThanEqual(
                currentUser, startDate, endDate);
    }


    public List<PlanoOrcamentario> findByDateRange(LocalDate startDate, LocalDate endDate) {
        Usuario currentUser = userDetailsService.userConnected();
        return planoOrcamentarioRepository.findByUsuarioAndDataInicioGreaterThanEqualAndDataFimLessThanEqual(
                currentUser, startDate, endDate);
    }

    @Transactional
    public PlanoOrcamentario createPlanoOrcamentario(PlanoOrcamentario planoOrcamentario) {
        Usuario currentUser = userDetailsService.userConnected();
        planoOrcamentario.setUsuario(currentUser);
        return planoOrcamentarioRepository.save(planoOrcamentario);
    }

    @Transactional
    public PlanoOrcamentario updatePlanoOrcamentario(Long id, PlanoOrcamentario planoOrcamentario) {
        PlanoOrcamentario existingPlano = findById(id);
        existingPlano.setNome(planoOrcamentario.getNome());
        existingPlano.setDataInicio(planoOrcamentario.getDataInicio());
        existingPlano.setDataFim(planoOrcamentario.getDataFim());
        existingPlano.setValorPrevisto(planoOrcamentario.getValorPrevisto());
        existingPlano.setAtivo(planoOrcamentario.isAtivo());
        return planoOrcamentarioRepository.save(existingPlano);
    }

    @Transactional
    public void deletePlanoOrcamentario(Long id) {
        PlanoOrcamentario planoOrcamentario = findById(id);
        planoOrcamentarioRepository.delete(planoOrcamentario);
    }

    @Transactional
    public PlanoOrcamentario deactivatePlanoOrcamentario(Long id) {
        PlanoOrcamentario planoOrcamentario = findById(id);
        planoOrcamentario.setAtivo(false);
        return planoOrcamentarioRepository.save(planoOrcamentario);
    }

    public List<PlanoOrcamentario> findByUsuarioAndAtivoTrue() {
        Usuario currentUser = userDetailsService.userConnected();
        return planoOrcamentarioRepository.findByUsuarioAndAtivoTrue(currentUser);
    }

    @Transactional
    public BigDecimal calculaImpactoReceita(Long id) {
        PlanoOrcamentario planoOrcamentario = findById(id);
        return planoOrcamentario.calcularImpactoLucro();
    }

    @Transactional
    public PlanoOrcamentario activatePlanoOrcamentario(Long id) {
        PlanoOrcamentario planoOrcamentario = findById(id);
        planoOrcamentario.desativarPlano();
        return planoOrcamentarioRepository.save(planoOrcamentario);
    }
}
