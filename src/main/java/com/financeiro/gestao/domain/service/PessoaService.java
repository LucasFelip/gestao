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
    @Autowired
    private PessoaRepository pessoaRepository;

    @Transactional(readOnly = true)
    public List<PessoaDTO> findAll() {
        try {
            List<Pessoa> pessoas = pessoaRepository.findAll();
            return pessoas.stream()
                    .map(EntityToDTOConverter::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new BusinessRuleException("Erro ao buscar todas as pessoas: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public Optional<PessoaDTO> findById(Long id) {
        try {
            return pessoaRepository.findById(id)
                    .map(EntityToDTOConverter::convertToDTO);
        } catch (Exception e) {
            throw new BusinessRuleException("Erro ao buscar pessoa com ID: " + id + ". Detalhes: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public Optional<PessoaDTO> findByCpf(String cpf) {
        try {
            return pessoaRepository.findByCpf(cpf)
                    .map(EntityToDTOConverter::convertToDTO);
        } catch (Exception e) {
            throw new BusinessRuleException("Erro ao buscar pessoa com CPF: " + cpf + ". Detalhes: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public Optional<PessoaDTO> findByEmail(String email) {
        try {
            return pessoaRepository.findByEmail(email)
                    .map(EntityToDTOConverter::convertToDTO);
        } catch (Exception e) {
            throw new BusinessRuleException("Erro ao buscar pessoa com email: " + email + ". Detalhes: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public String getNomeUsuarioLogado() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String email = auth.getName();
            Pessoa pessoa = pessoaRepository.findByEmail(email)
                    .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com o email: " + email));
            return pessoa.getNome();
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessRuleException("Erro ao buscar o nome do usuário logado: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public List<PessoaDTO> findByNomeContaining(String nome) {
        try {
            return pessoaRepository.findByNomeContaining(nome)
                    .stream()
                    .map(EntityToDTOConverter::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new BusinessRuleException("Erro ao buscar pessoas contendo o nome: " + nome + ". Detalhes: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public Optional<PessoaDTO> findByNomeAndEmail(String nome, String email) {
        try {
            return pessoaRepository.findByNomeAndEmail(nome,email)
                    .map(EntityToDTOConverter::convertToDTO);
        } catch (Exception e) {
            throw new BusinessRuleException("Erro ao buscar pessoa com nome: " + nome + " e email: " + email + ". Detalhes: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public boolean existsByCpf(String cpf) {
        try {
            return pessoaRepository.existsByCpf(cpf);
        } catch (Exception e) {
            throw new BusinessRuleException("Erro ao verificar a existência de pessoa com CPF: " + cpf + ". Detalhes: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        try {
            return pessoaRepository.existsByEmail(email);
        } catch (Exception e) {
            throw new BusinessRuleException("Erro ao verificar a existência de pessoa com email: " + email + ". Detalhes: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public Optional<PessoaDTO> findByEmailAndSenha(String email, String senha) {
        try {
            return pessoaRepository.findByEmailAndSenha(email, senha)
                    .map(EntityToDTOConverter::convertToDTO);
        } catch (Exception e) {
            throw new BusinessRuleException("Erro ao buscar pessoa com email: " + email + " e senha fornecida. Detalhes: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public List<PessoaDTO> findByNomeStartingWith(String prefixo) {
        try {
            return pessoaRepository.findByNomeStartingWith(prefixo)
                    .stream()
                    .map(EntityToDTOConverter::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new BusinessRuleException("Erro ao buscar pessoas começando com o prefixo: " + prefixo + ". Detalhes: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public List<PessoaDTO> findByTelefoneContaining(String numero) {
        try {
            return pessoaRepository.findByTelefoneContaining(numero)
                    .stream()
                    .map(EntityToDTOConverter::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new BusinessRuleException("Erro ao buscar pessoas contendo o telefone: " + numero + ". Detalhes: " + e.getMessage());
        }
    }

    @Transactional
    public Pessoa save(Pessoa pessoa) {
        try {
            validarPessoa(pessoa);
            return pessoaRepository.save(pessoa);
        } catch (Exception e) {
            throw new BusinessRuleException("Erro ao salvar a pessoa: " + e.getMessage());
        }
    }

    @Transactional
    public Pessoa update(Long id, Pessoa pessoaAtualizada) {
        try {
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
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessRuleException("Erro ao atualizar a pessoa com ID: " + id + ". Detalhes: " + e.getMessage());
        }
    }

    @Transactional
    public void delete(Long id) {
        try {
            if (!pessoaRepository.existsById(id)) {
                throw new ResourceNotFoundException("Pessoa não encontrada com o ID: " + id);
            }
            pessoaRepository.deleteById(id);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessRuleException("Erro ao excluir a pessoa com ID: " + id + ". Detalhes: " + e.getMessage());
        }
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
