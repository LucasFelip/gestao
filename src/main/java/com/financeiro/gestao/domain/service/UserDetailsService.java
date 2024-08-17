package com.financeiro.gestao.domain.service;

import com.financeiro.gestao.domain.model.Usuario;
import com.financeiro.gestao.domain.repository.UsuarioRepository;
import com.financeiro.gestao.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            Usuario pessoa = usuarioRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("Email não encontrado: " + email));

            return org.springframework.security.core.userdetails.User
                    .withUsername(email)
                    .password(pessoa.getSenha())
                    .authorities(pessoa.getRole().name())
                    .accountExpired(false)
                    .accountLocked(false)
                    .credentialsExpired(false)
                    .disabled(false)
                    .build();
        } catch (UsernameNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao carregar usuário com email: " + email + ". Detalhes: " + e.getMessage());
        }
    }

    public Usuario userConnected() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth.getPrincipal().equals("anonymousUser")) {
            throw new ResourceNotFoundException("Usuário não está autenticado.", auth);
        }
        String email = auth.getName();
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com o email: ", email));
    }

}
