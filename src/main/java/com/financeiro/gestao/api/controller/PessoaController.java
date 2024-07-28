package com.financeiro.gestao.api.controller;

import com.financeiro.gestao.api.dto.PessoaDTO;
import com.financeiro.gestao.domain.model.Pessoa;
import com.financeiro.gestao.domain.service.PessoaService;
import com.financeiro.gestao.util.EntityToDTOConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pessoas")
public class PessoaController {

    private final PessoaService pessoaService;

    @Autowired
    public PessoaController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    @GetMapping
    public ResponseEntity<List<PessoaDTO>> findAll() {
        List<PessoaDTO> pessoas = pessoaService.findAll();
        return ResponseEntity.ok(pessoas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PessoaDTO> findById(@PathVariable Long id) {
        return pessoaService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/cpf")
    public ResponseEntity<PessoaDTO> findByCpf(@RequestParam String cpf) {
        return pessoaService.findByCpf(cpf)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/email")
    public ResponseEntity<PessoaDTO> findByEmail(@RequestParam String email) {
        return pessoaService.findByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/nome")
    public ResponseEntity<List<PessoaDTO>> findByNomeContaining(@RequestParam String nome) {
        List<PessoaDTO> pessoas = pessoaService.findByNomeContaining(nome);
        return ResponseEntity.ok(pessoas);
    }

    @GetMapping("/nome-email")
    public ResponseEntity<PessoaDTO> findByNomeAndEmail(@RequestParam String nome, @RequestParam String email) {
        return pessoaService.findByNomeAndEmail(nome, email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/exists/cpf")
    public ResponseEntity<Boolean> existsByCpf(@RequestParam String cpf) {
        return ResponseEntity.ok(pessoaService.existsByCpf(cpf));
    }

    @GetMapping("/exists/email")
    public ResponseEntity<Boolean> existsByEmail(@RequestParam String email) {
        return ResponseEntity.ok(pessoaService.existsByEmail(email));
    }

    @GetMapping("/email-senha")
    public ResponseEntity<PessoaDTO> findByEmailAndSenha(@RequestParam String email, @RequestParam String senha) {
        return pessoaService.findByEmailAndSenha(email, senha)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PessoaDTO> save(@RequestBody Pessoa pessoa) {
        Pessoa savedPessoa = pessoaService.save(pessoa);
        PessoaDTO pessoaDTO = EntityToDTOConverter.convertToDTO(savedPessoa);
        return ResponseEntity.status(HttpStatus.CREATED).body(pessoaDTO);
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<PessoaDTO> update(@PathVariable Long id, @RequestBody Pessoa pessoaAtualizada) {
        Pessoa updatedPessoa = pessoaService.update(id, pessoaAtualizada);
        PessoaDTO pessoaDTO = EntityToDTOConverter.convertToDTO(updatedPessoa);
        return ResponseEntity.ok(pessoaDTO);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        pessoaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
