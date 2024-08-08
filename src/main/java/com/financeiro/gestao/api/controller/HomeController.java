package com.financeiro.gestao.api.controller;

import com.financeiro.gestao.domain.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private PessoaService pessoaService;

    @GetMapping
    public ModelAndView home(Model model) {
        String nome = pessoaService.getNomeUsuarioLogado();
        model.addAttribute("nome", nome);
        return new ModelAndView("home");
    }
}
