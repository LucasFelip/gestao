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

    public static boolean isValidCPF(String cpf) {
        if (cpf == null || cpf.length() != 11 || cpf.matches(cpf.charAt(0)+"{11}")) return false;

        int[] weightsFirstDigit = {10, 9, 8, 7, 6, 5, 4, 3, 2};
        int[] weightsSecondDigit = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};

        try {
            Long.parseLong(cpf); // Ensures CPF contains only numeric digits
        } catch (NumberFormatException e) {
            return false; // Non-numeric character found
        }

        if (!checkDigit(cpf, weightsFirstDigit, 9)) return false;
        return checkDigit(cpf, weightsSecondDigit, 10);
    }

    private static boolean checkDigit(String cpf, int[] weights, int length) {
        int sum = 0;
        for (int i = 0; i < length - 1; i++) {
            int digit = Integer.parseInt(cpf.substring(i, i+1));
            sum += digit * weights[i];
        }
        int remainder = (sum * 10) % 11;
        if (remainder == 10) remainder = 0;
        return remainder == Integer.parseInt(cpf.substring(length - 1, length));
    }
}
