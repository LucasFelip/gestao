package com.financeiro.gestao.service;

import com.financeiro.gestao.model.Lucro;
import com.financeiro.gestao.repository.CategoriaRepository;
import com.financeiro.gestao.repository.LucroRepository;
import com.financeiro.gestao.exception.ResourceNotFoundException;
import com.financeiro.gestao.exception.BusinessRuleException;
import com.financeiro.gestao.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class LucroService {

    private final LucroRepository lucroRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    public LucroService(LucroRepository lucroRepository) {
        this.lucroRepository = lucroRepository;
    }

    @Transactional(readOnly = true)
    public List<Lucro> findAll() {
        return lucroRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Lucro> findById(Long id) {
        return Optional.ofNullable(lucroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lucro não encontrado com o ID: " + id)));
    }

    @Transactional
    public Lucro save(Lucro lucro) {
        validarLucro(lucro);
        return lucroRepository.save(lucro);
    }

    @Transactional
    public Lucro update(Long id, Lucro lucroAtualizado) {
        Lucro lucro = lucroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lucro não encontrado com o ID: " + id));

        lucro.setDescricao(lucroAtualizado.getDescricao());
        lucro.setValor(lucroAtualizado.getValor());
        lucro.setData(lucroAtualizado.getData());
        lucro.setCategoria(lucroAtualizado.getCategoria());
        lucro.setPessoa(lucroAtualizado.getPessoa());

        validarLucro(lucro);

        return lucroRepository.save(lucro);
    }

    @Transactional
    public void delete(Long id) {
        if (!lucroRepository.existsById(id)) {
            throw new ResourceNotFoundException("Lucro não encontrado com o ID: " + id);
        }
        lucroRepository.deleteById(id);
    }

    private void validarLucro(Lucro lucro) {
        // Verificação do valor do lucro
        if (lucro.getValor() <= 0) {
            throw new BusinessRuleException("O valor do lucro deve ser maior que zero.");
        }

        // Verificação da existência da categoria
        if (!categoriaRepository.existsById(lucro.getCategoria().getId())) {
            throw new BusinessRuleException("A categoria de lucro especificada não existe.");
        }

        // Verificação da data do lucro (exemplo: não permitir datas futuras)
        if (lucro.getData().after(new Date())) {
            throw new BusinessRuleException("A data do lucro não pode ser uma data futura.");
        }

        // Verificar se a descrição não está vazia
        if (lucro.getDescricao() == null || lucro.getDescricao().trim().isEmpty()) {
            throw new BusinessRuleException("A descrição do lucro não pode estar vazia.");
        }
    }
}
