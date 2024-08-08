package com.financeiro.gestao.domain.service;

import com.financeiro.gestao.api.dto.PessoaDTO;
import com.financeiro.gestao.domain.exception.BusinessRuleException;
import com.financeiro.gestao.domain.exception.ResourceNotFoundException;
import com.financeiro.gestao.domain.model.Pessoa;
import com.financeiro.gestao.domain.repository.PessoaRepository;
import com.financeiro.gestao.util.EntityToDTOConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
        return pessoaRepository.findById(id)
                .map(EntityToDTOConverter::convertToDTO);
    }

    @Transactional(readOnly = true)
    public Optional<PessoaDTO> findByCpf(String cpf) {
        return pessoaRepository.findByCpf(cpf)
                .map(EntityToDTOConverter::convertToDTO);
    }

    @Transactional(readOnly = true)
    public Optional<PessoaDTO> findByEmail(String email) {
        return pessoaRepository.findByEmail(email)
                .map(EntityToDTOConverter::convertToDTO);
    }

    @Transactional(readOnly = true)
    public String getNomeUsuarioLogado() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        Pessoa pessoa = pessoaRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com o email: " + email));
        return pessoa.getNome();
    }

    @Transactional(readOnly = true)
    public List<PessoaDTO> findByNomeContaining(String nome) {
        return pessoaRepository.findByNomeContaining(nome)
                .stream()
                .map(EntityToDTOConverter::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<PessoaDTO> findByNomeAndEmail(String nome, String email) {
        return pessoaRepository.findByNomeAndEmail(nome, email)
                .map(EntityToDTOConverter::convertToDTO);
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
        return pessoaRepository.findByEmailAndSenha(email, senha)
                .map(EntityToDTOConverter::convertToDTO);
    }

    @Transactional(readOnly = true)
    public List<PessoaDTO> findByNomeStartingWith(String prefixo) {
        return pessoaRepository.findByNomeStartingWith(prefixo)
                .stream()
                .map(EntityToDTOConverter::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PessoaDTO> findByTelefoneContaining(String numero) {
        return pessoaRepository.findByTelefoneContaining(numero)
                .stream()
                .map(EntityToDTOConverter::convertToDTO)
                .collect(Collectors.toList());
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
        pessoa.setRoles(pessoaAtualizada.getRoles());

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
        if (pessoa.getEmail() == null || pessoa.getEmail().trim().isEmpty()) {
            throw new BusinessRuleException("O email da pessoa não pode estar vazio.");
        }
        if (pessoa.getNome() == null || pessoa.getNome().trim().isEmpty()) {
            throw new BusinessRuleException("O nome da pessoa não pode estar vazio.");
        }
        if (pessoaRepository.existsByCpf(pessoa.getCpf())) {
            throw new BusinessRuleException("Já existe uma pessoa com este CPF.");
        }
        if (pessoaRepository.existsByEmail(pessoa.getEmail())) {
            throw new BusinessRuleException("Já existe uma pessoa com este email.");
        }
    }
}
