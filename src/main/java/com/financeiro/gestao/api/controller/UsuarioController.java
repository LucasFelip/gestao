package com.financeiro.gestao.api.controller;

import com.financeiro.gestao.domain.model.Usuario;
import com.financeiro.gestao.domain.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable Long id) {
        Usuario usuario = usuarioService.findById(id);
        return ResponseEntity.ok(usuario);
    }

    @GetMapping("/email")
    public ResponseEntity<Usuario> getUsuarioByEmail(@RequestParam String email) {
        Usuario usuario = usuarioService.findByEmail(email);
        return ResponseEntity.ok(usuario);
    }

    @GetMapping("/nome")
    public ResponseEntity<List<Usuario>> getUsuariosByNomeContaining(@RequestParam String nome) {
        List<Usuario> usuarios = usuarioService.findByNomeContaining(nome);
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/current")
    public ResponseEntity<Usuario> getCurrentUser() {
        Usuario currentUser = usuarioService.getCurrentUser();
        return ResponseEntity.ok(currentUser);
    }

    @GetMapping("/current/name")
    public ResponseEntity<String> getCurrentUserName() {
        String currentUserName = usuarioService.getCurrentUserName();
        return ResponseEntity.ok(currentUserName);
    }

    @PostMapping
    public ResponseEntity<Usuario> createUsuario(@RequestBody Usuario usuario) {
        Usuario novoUsuario = usuarioService.createUsuario(usuario);
        return ResponseEntity.ok(novoUsuario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> updateUsuario(
            @PathVariable Long id,
            @RequestBody Usuario usuario) {
        usuario.setId(id);
        Usuario usuarioAtualizado = usuarioService.updateUsuario(usuario);
        return ResponseEntity.ok(usuarioAtualizado);
    }

    @PutMapping("/current")
    public ResponseEntity<Usuario> updateCurrentUser(@RequestBody Usuario usuario) {
        Usuario usuarioAtualizado = usuarioService.updateCurrentUser(usuario);
        return ResponseEntity.ok(usuarioAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        usuarioService.deleteUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/exists/email")
    public ResponseEntity<Boolean> existsByEmail(@RequestParam String email) {
        boolean exists = usuarioService.existsByEmail(email);
        return ResponseEntity.ok(exists);
    }

    @GetMapping("/exists/cpf")
    public ResponseEntity<Boolean> existsByCpf(@RequestParam String cpf) {
        boolean exists = usuarioService.existsByCpf(cpf);
        return ResponseEntity.ok(exists);
    }

    @GetMapping("/username-or-email")
    public ResponseEntity<Usuario> getUsuarioByUsernameOrEmail(@RequestParam String username, @RequestParam String email) {
        Usuario usuario = usuarioService.findByUsernameOrEmail(username, email);
        return ResponseEntity.ok(usuario);
    }
}
