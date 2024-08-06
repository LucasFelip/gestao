package com.financeiro.gestao.api.controller;

import com.financeiro.gestao.domain.model.Pessoa;
import com.financeiro.gestao.domain.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(Pessoa pessoa, Model model) {
        if (pessoaRepository.existsByEmail(pessoa.getEmail())) {
            model.addAttribute("error", "Email já está em uso");
            return "register";
        }

        pessoa.setSenha(passwordEncoder.encode(pessoa.getSenha()));
        pessoaRepository.save(pessoa);
        return "redirect:/auth/login";
    }
}
