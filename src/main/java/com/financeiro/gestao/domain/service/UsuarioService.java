package com.financeiro.gestao.domain.service;

import com.financeiro.gestao.domain.model.Usuario;
import com.financeiro.gestao.domain.repository.UsuarioRepository;
import com.financeiro.gestao.exception.ResourceNotFoundException;
import com.financeiro.gestao.util.ValidCPF;
import com.financeiro.gestao.util.ValidPassword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Usuario findById(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com o ID: ", id));
    }

    public Usuario findByEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com o email: ", email));
    }

    public List<Usuario> findByNomeContaining(String nome) {
        return usuarioRepository.findByNomeContaining(nome);
    }

    public boolean existsByEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    public boolean existsByCpf(String cpf) {
        return usuarioRepository.existsByCpf(cpf);
    }

    @Transactional
    public Usuario createUsuario(Usuario usuario) {
        validarUsuario(usuario);
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public Usuario updateUsuario(Usuario usuario) {
        validarUsuario(usuario);
        Usuario existingUser = findById(usuario.getId());
        existingUser.setNome(usuario.getNome());
        existingUser.setCpf(usuario.getCpf());
        existingUser.setEmail(usuario.getEmail());
        if (!usuario.getSenha().isEmpty()) {
            existingUser.setSenha(passwordEncoder.encode(usuario.getSenha()));
        }
        existingUser.setTelefone(usuario.getTelefone());
        return usuarioRepository.save(existingUser);
    }

    @Transactional
    public void deleteUsuario(Long id) {
        Usuario usuario = findById(id);
        usuarioRepository.delete(usuario);
    }

    public Usuario getCurrentUser() {
        return userDetailsService.userConnected();
    }

    public String getCurrentUserName() {
        return getCurrentUser().getNome();
    }

    @Transactional
    public Usuario updateCurrentUser(Usuario usuario) {
        Usuario currentUser = getCurrentUser();
        currentUser.setNome(usuario.getNome());
        if (!usuario.getSenha().isEmpty()) {
            currentUser.setSenha(passwordEncoder.encode(usuario.getSenha()));
        }
        currentUser.setTelefone(usuario.getTelefone());
        return usuarioRepository.save(currentUser);
    }

    public Usuario findByUsernameOrEmail(String username, String email) {
        return usuarioRepository.findByNomeOrEmail(username, email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com o username ou email: ", username + " / " + email));
    }

    public void validarUsuario(Usuario usuario) {
        if (existsByEmail(usuario.getEmail())) {
            throw new IllegalArgumentException("Email já está em uso: " + usuario.getEmail());
        }

        if (existsByCpf(usuario.getCpf())) {
            throw new IllegalArgumentException("CPF já está em uso: " + usuario.getCpf());
        }

        if (!ValidCPF.isValidCPF(usuario.getCpf())) {
            throw new IllegalArgumentException("CPF inválido: " + usuario.getCpf());
        }

        if (usuario.getSenha() == null || usuario.getSenha().isEmpty()) {
            throw new IllegalArgumentException("A senha não pode estar vazia.");
        }

        List<String> errosSenha = ValidPassword.validatePassword(usuario.getSenha());
        if (!errosSenha.isEmpty()) {
            String mensagemErros = String.join(", ", errosSenha);
            throw new IllegalArgumentException("A senha não atende aos seguintes requisitos: " + mensagemErros);
        }
    }

}
