package com.financeiro.gestao.service;

import com.financeiro.gestao.model.Gasto;
import com.financeiro.gestao.repository.GastoRepository;
import com.financeiro.gestao.repository.CategoriaRepository;
import com.financeiro.gestao.exception.ResourceNotFoundException;
import com.financeiro.gestao.exception.BusinessRuleException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class GastoService {

    private final GastoRepository gastoRepository;
    private final CategoriaRepository categoriaRepository;

    @Autowired
    public GastoService(GastoRepository gastoRepository, CategoriaRepository categoriaRepository) {
        this.gastoRepository = gastoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    @Transactional(readOnly = true)
    public List<Gasto> findAll() {
        return gastoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Gasto> findById(Long id) {
        return Optional.ofNullable(gastoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Gasto não encontrado com o ID: " + id)));
    }

    @Transactional(readOnly = true)
    public List<Gasto> findByPessoaId(Long pessoaId) {
        return gastoRepository.findByPessoaId(pessoaId);
    }

    @Transactional(readOnly = true)
    public List<Gasto> findByDataBetween(Date inicio, Date fim) {
        return gastoRepository.findByDataBetween(inicio, fim);
    }

    @Transactional(readOnly = true)
    public List<Gasto> findByCategoriaId(Long categoriaId) {
        return gastoRepository.findByCategoriaId(categoriaId);
    }

    @Transactional(readOnly = true)
    public List<Gasto> findByDescricaoContaining(String descricao) {
        return gastoRepository.findByDescricaoContaining(descricao);
    }

    @Transactional(readOnly = true)
    public List<Gasto> findByValorGreaterThan(float valor) {
        return gastoRepository.findByValorGreaterThan(valor);
    }

    @Transactional(readOnly = true)
    public List<Gasto> findByPessoaIdAndCategoriaId(Long pessoaId, Long categoriaId) {
        return gastoRepository.findByPessoaIdAndCategoriaId(pessoaId, categoriaId);
    }

    @Transactional
    public Gasto save(Gasto gasto) {
        validarGasto(gasto);
        return gastoRepository.save(gasto);
    }

    @Transactional
    public Gasto update(Long id, Gasto gastoAtualizado) {
        Gasto gasto = gastoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Gasto não encontrado com o ID: " + id));

        gasto.setDescricao(gastoAtualizado.getDescricao());
        gasto.setValor(gastoAtualizado.getValor());
        gasto.setData(gastoAtualizado.getData());
        gasto.setCategoria(gastoAtualizado.getCategoria());
        gasto.setPessoa(gastoAtualizado.getPessoa());

        validarGasto(gasto);

        return gastoRepository.save(gasto);
    }

    @Transactional
    public void delete(Long id) {
        if (!gastoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Gasto não encontrado com o ID: " + id);
        }
        gastoRepository.deleteById(id);
    }

    private void validarGasto(Gasto gasto) {
        if (gasto.getValor() <= 0) {
            throw new BusinessRuleException("O valor do gasto deve ser maior que zero.");
        }

        if (!categoriaRepository.existsById(gasto.getCategoria().getId())) {
            throw new BusinessRuleException("A categoria especificada para o gasto não existe.");
        }

        if (gasto.getData().after(new Date())) {
            throw new BusinessRuleException("A data do gasto não pode ser uma data futura.");
        }

        if (gasto.getDescricao() == null || gasto.getDescricao().trim().isEmpty()) {
            throw new BusinessRuleException("A descrição do gasto não pode estar vazia.");
        }
    }
}