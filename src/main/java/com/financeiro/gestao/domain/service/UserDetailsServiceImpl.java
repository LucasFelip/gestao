package com.financeiro.gestao.domain.service;

import com.financeiro.gestao.domain.exception.ResourceNotFoundException;
import com.financeiro.gestao.domain.model.Pessoa;
import com.financeiro.gestao.domain.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            Pessoa pessoa = pessoaRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("Email não encontrado: " + email));

            return org.springframework.security.core.userdetails.User
                    .withUsername(email)
                    .password(pessoa.getSenha())
                    .authorities(pessoa.getRoles().name())
                    .accountExpired(false)
                    .accountLocked(false)
                    .credentialsExpired(false)
                    .disabled(false)
                    .build();
        } catch (UsernameNotFoundException e) {
            throw e; // Re-lança a exceção de email não encontrado
        } catch (Exception e) {
            throw new RuntimeException("Erro ao carregar usuário com email: " + email + ". Detalhes: " + e.getMessage());
        }
    }

    public Pessoa userConnected() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String email = auth.getName();
            return pessoaRepository.findByEmail(email)
                    .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com o email: " + email));
        } catch (ResourceNotFoundException e) {
            throw e; // Re-lança a exceção de usuário não encontrado
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar usuário conectado com email: " + e.getMessage());
        }
    }
}
