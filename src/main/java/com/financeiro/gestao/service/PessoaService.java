package com.financeiro.gestao.service;

import com.financeiro.gestao.model.Pessoa;
import com.financeiro.gestao.repository.PessoaRepository;
import com.financeiro.gestao.exception.ResourceNotFoundException;
import com.financeiro.gestao.exception.BusinessRuleException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.financeiro.gestao.utils.ValidCPF.isValidCPF;

@Service
public class PessoaService {

    private final PessoaRepository pessoaRepository;

    @Autowired
    public PessoaService(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    @Transactional(readOnly = true)
    public List<Pessoa> findAll() {
        return pessoaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Pessoa> findById(Long id) {
        return Optional.ofNullable(pessoaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pessoa não encontrada com o ID: " + id)));
    }

    @Transactional(readOnly = true)
    public Optional<Pessoa> findByEmail(String email) {
        return pessoaRepository.findByEmail(email);
    }

    @Transactional
    public Pessoa save(Pessoa pessoa) {
        validarDadosPessoa(pessoa, null);
        return pessoaRepository.save(pessoa);
    }

    @Transactional
    public Pessoa update(Long id, Pessoa pessoaAtualizada) {
        Pessoa pessoa = pessoaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pessoa não encontrada com o ID: " + id));

        validarDadosPessoa(pessoaAtualizada, id);

        pessoa.setNome(pessoaAtualizada.getNome());
        pessoa.setCpf(pessoaAtualizada.getCpf());
        pessoa.setEmail(pessoaAtualizada.getEmail());
        pessoa.setSenha(pessoaAtualizada.getSenha());
        pessoa.setEndereco(pessoaAtualizada.getEndereco());

        return pessoaRepository.save(pessoa);
    }

    @Transactional
    public void delete(Long id) {
        if (!pessoaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Pessoa não encontrada com o ID: " + id);
        }
        pessoaRepository.deleteById(id);
    }

    private void validarDadosPessoa(Pessoa pessoa, Long idAtualizacao) {
        if (!isValidCPF(pessoa.getCpf())) {
            throw new BusinessRuleException("CPF inválido.");
        }

        if (idAtualizacao == null) {
            // Para novos registros, verificar se CPF ou Email já existem
            if (pessoaRepository.existsByCpf(pessoa.getCpf())) {
                throw new BusinessRuleException("CPF já cadastrado.");
            }
            if (pessoaRepository.existsByEmail(pessoa.getEmail())) {
                throw new BusinessRuleException("Email já cadastrado.");
            }
        } else {
            // Para atualização, verificar se CPF ou Email pertencem a outra pessoa
            pessoaRepository.findByCpf(pessoa.getCpf())
                    .ifPresent(p -> {
                        if (!p.getId().equals(idAtualizacao)) {
                            throw new BusinessRuleException("CPF já cadastrado por outra pessoa.");
                        }
                    });
            pessoaRepository.findByEmail(pessoa.getEmail())
                    .ifPresent(p -> {
                        if (!p.getId().equals(idAtualizacao)) {
                            throw new BusinessRuleException("Email já cadastrado por outra pessoa.");
                        }
                    });
        }
    }
}
