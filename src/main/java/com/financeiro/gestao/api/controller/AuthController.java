package com.financeiro.gestao.api.controller;

import com.financeiro.gestao.domain.model.Pessoa;
import com.financeiro.gestao.domain.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("login");
    }

    @GetMapping("/register")
    public ModelAndView register() {
        return new ModelAndView("register");
    }

    @GetMapping("/logout")
    public ModelAndView logout() {
        return new ModelAndView("login");
    }

    @PostMapping("/register")
    public ModelAndView registerUser(Pessoa pessoa, Model model) {
        if (pessoaRepository.existsByEmail(pessoa.getEmail())) {
            ModelAndView modelAndView = new ModelAndView("register");
            modelAndView.addObject("error", "Email já está em uso");
            return modelAndView;
        }

        pessoa.setSenha(passwordEncoder.encode(pessoa.getSenha()));
        pessoaRepository.save(pessoa);
        return new ModelAndView("redirect:/auth/login");
    }
}
