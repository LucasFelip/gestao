package com.financeiro.gestao.domain.service;

import com.financeiro.gestao.api.dto.GastoDTO;
import com.financeiro.gestao.domain.exception.BusinessRuleException;
import com.financeiro.gestao.domain.exception.ResourceNotFoundException;
import com.financeiro.gestao.domain.model.Categoria;
import com.financeiro.gestao.domain.model.Gasto;
import com.financeiro.gestao.domain.model.Pessoa;
import com.financeiro.gestao.domain.repository.GastoRepository;
import com.financeiro.gestao.util.EntityToDTOConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GastoService {

    private final GastoRepository gastoRepository;

    @Autowired
    public GastoService(GastoRepository gastoRepository) {
        this.gastoRepository = gastoRepository;
    }

    @Transactional(readOnly = true)
    public List<GastoDTO> findAll() {
        List<Gasto> gastos = gastoRepository.findAll();
        return gastos.stream()
                .map(EntityToDTOConverter::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<GastoDTO> findById(Long id) {
        Gasto gasto = gastoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Gasto não encontrado com o ID: " + id));
        return Optional.of(EntityToDTOConverter.convertToDTO(gasto));
    }

    @Transactional(readOnly = true)
    public List<GastoDTO> findByPessoa(Pessoa pessoa) {
        List<Gasto> gastos = gastoRepository.findByPessoa(pessoa);
        return gastos.stream()
                .map(EntityToDTOConverter::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<GastoDTO> findByDataBetween(LocalDate inicio, LocalDate fim) {
        List<Gasto> gastos = gastoRepository.findByDataBetween(inicio, fim);
        return gastos.stream()
                .map(EntityToDTOConverter::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<GastoDTO> findByCategoria(Categoria categoria) {
        List<Gasto> gastos = gastoRepository.findByCategoria(categoria);
        return gastos.stream()
                .map(EntityToDTOConverter::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<GastoDTO> findByDescricaoContaining(String descricao) {
        List<Gasto> gastos = gastoRepository.findByDescricaoContaining(descricao);
        return gastos.stream()
                .map(EntityToDTOConverter::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<GastoDTO> findByValorGreaterThan(BigDecimal valor) {
        List<Gasto> gastos = gastoRepository.findByValorGreaterThan(valor);
        return gastos.stream()
                .map(EntityToDTOConverter::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<GastoDTO> findByPessoaAndCategoria(Pessoa pessoa, Categoria categoria) {
        List<Gasto> gastos = gastoRepository.findByPessoaAndCategoria(pessoa, categoria);
        return gastos.stream()
                .map(EntityToDTOConverter::convertToDTO)
                .collect(Collectors.toList());
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
//        if (gasto.getValor() <= 0) {
//            throw new BusinessRuleException("O valor do gasto deve ser maior que zero.");
//        }

        if (gasto.getDescricao() == null || gasto.getDescricao().trim().isEmpty()) {
            throw new BusinessRuleException("A descrição do gasto não pode estar vazia.");
        }
    }
}
