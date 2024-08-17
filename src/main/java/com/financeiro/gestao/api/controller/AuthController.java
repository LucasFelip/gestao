package com.financeiro.gestao.api.controller;

import com.financeiro.gestao.domain.model.Usuario;
import com.financeiro.gestao.domain.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
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
    private UsuarioService usuarioService;

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
    public ModelAndView registerUser(Usuario usuario, Model model) {
        if (usuarioService.existsByEmail(usuario.getEmail())) {
            ModelAndView modelAndView = new ModelAndView("register");
            modelAndView.addObject("error", "Email já está em uso");
            return modelAndView;
        }
        usuarioService.createUsuario(usuario);
        return new ModelAndView("redirect:/auth/login");
    }

    @GetMapping("/username")
    public String nameUserConnected(){
        return usuarioService.getCurrentUserName();
    }
}
