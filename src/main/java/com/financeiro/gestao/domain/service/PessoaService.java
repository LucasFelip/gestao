package com.financeiro.gestao.domain.service;

import com.financeiro.gestao.api.dto.PessoaDTO;
import com.financeiro.gestao.domain.exception.BusinessRuleException;
import com.financeiro.gestao.domain.exception.ResourceNotFoundException;
import com.financeiro.gestao.domain.model.Pessoa;
import com.financeiro.gestao.domain.repository.PessoaRepository;
import com.financeiro.gestao.util.EntityToDTOConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PessoaService {

    private final PessoaRepository pessoaRepository;

    @Autowired
    public PessoaService(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    @Transactional(readOnly = true)
    public List<PessoaDTO> findAll() {
        List<Pessoa> pessoas = pessoaRepository.findAll();
        return pessoas.stream()
                .map(EntityToDTOConverter::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<PessoaDTO> findById(Long id) {
        Pessoa pessoa = pessoaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pessoa não encontrada com o ID: " + id));
        return Optional.of(EntityToDTOConverter.convertToDTO(pessoa));
    }

    @Transactional(readOnly = true)
    public Optional<PessoaDTO> findByCpf(String cpf) {
        Pessoa pessoa = pessoaRepository.findByCpf(cpf)
                .orElseThrow(() -> new ResourceNotFoundException("Pessoa não encontrada com o CPF: " + cpf));
        return Optional.of(EntityToDTOConverter.convertToDTO(pessoa));
    }

    @Transactional(readOnly = true)
    public Optional<PessoaDTO> findByEmail(String email) {
        Pessoa pessoa = pessoaRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Pessoa não encontrada com o email: " + email));
        return Optional.of(EntityToDTOConverter.convertToDTO(pessoa));
    }

    @Transactional(readOnly = true)
    public List<PessoaDTO> findByNomeContaining(String nome) {
        List<Pessoa> pessoas = pessoaRepository.findByNomeContaining(nome);
        return pessoas.stream()
                .map(EntityToDTOConverter::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<PessoaDTO> findByNomeAndEmail(String nome, String email) {
        Pessoa pessoa = pessoaRepository.findByNomeAndEmail(nome, email)
                .orElseThrow(() -> new ResourceNotFoundException("Pessoa não encontrada com o nome e email fornecidos."));
        return Optional.of(EntityToDTOConverter.convertToDTO(pessoa));
    }

    @Transactional(readOnly = true)
    public boolean existsByCpf(String cpf) {
        return pessoaRepository.existsByCpf(cpf);
    }

    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return pessoaRepository.existsByEmail(email);
    }

    @Transactional(readOnly = true)
    public Optional<PessoaDTO> findByEmailAndSenha(String email, String senha) {
        Pessoa pessoa = pessoaRepository.findByEmailAndSenha(email, senha)
                .orElseThrow(() -> new ResourceNotFoundException("Pessoa não encontrada com o email e senha fornecidos."));
        return Optional.of(EntityToDTOConverter.convertToDTO(pessoa));
    }

    @Transactional
    public Pessoa save(Pessoa pessoa) {
        validarPessoa(pessoa);
        return pessoaRepository.save(pessoa);
    }

    @Transactional
    public Pessoa update(Long id, Pessoa pessoaAtualizada) {
        Pessoa pessoa = pessoaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pessoa não encontrada com o ID: " + id));

        pessoa.setNome(pessoaAtualizada.getNome());
        pessoa.setCpf(pessoaAtualizada.getCpf());
        pessoa.setEmail(pessoaAtualizada.getEmail());
        pessoa.setSenha(pessoaAtualizada.getSenha());
        pessoa.setTelefone(pessoaAtualizada.getTelefone());
        pessoa.setEndereco(pessoaAtualizada.getEndereco());

        validarPessoa(pessoa);

        return pessoaRepository.save(pessoa);
    }

    @Transactional
    public void delete(Long id) {
        if (!pessoaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Pessoa não encontrada com o ID: " + id);
        }
        pessoaRepository.deleteById(id);
    }

    private void validarPessoa(Pessoa pessoa) {
        if (pessoa.getCpf() == null || pessoa.getCpf().trim().isEmpty()) {
            throw new BusinessRuleException("O CPF da pessoa não pode estar vazio.");
        }

        if (pessoaRepository.existsByCpf(pessoa.getCpf())) {
            throw new BusinessRuleException("Já existe uma pessoa cadastrada com este CPF.");
        }

        if (pessoa.getEmail() == null || pessoa.getEmail().trim().isEmpty()) {
            throw new BusinessRuleException("O email da pessoa não pode estar vazio.");
        }

        if (pessoaRepository.existsByEmail(pessoa.getEmail())) {
            throw new BusinessRuleException("Já existe uma pessoa cadastrada com este email.");
        }
    }
}
