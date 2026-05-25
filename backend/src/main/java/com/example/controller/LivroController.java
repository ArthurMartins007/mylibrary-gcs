package com.example.controller;

import com.example.entities.Livro;
import com.example.entities.Status;
import com.example.service.LivroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/livros")
@CrossOrigin("*")
public class LivroController {

    @Autowired
    private LivroService livroService;

    @PostMapping
    public ResponseEntity<Livro> criar(@RequestBody Livro libro) {
        Livro novoLivro = livroService.salvar(libro);
        return ResponseEntity.ok(novoLivro);
    }

    @GetMapping
    public ResponseEntity<List<Livro>> listarFiltrado(
            @RequestParam(required = false) Status status,
            @RequestParam(required = false) String busca) {

        if (status != null) {
            return ResponseEntity.ok(livroService.buscarPorStatus(status));
        }
        if (busca != null && !busca.trim().isEmpty()) {
            return ResponseEntity.ok(livroService.buscarPorTituloOuAutor(busca));
        }

        return ResponseEntity.ok(livroService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Livro> buscarPorId(@PathVariable UUID id) {
        Livro livro = livroService.buscarPorId(id);
        return ResponseEntity.ok(livro);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable UUID id) {
        livroService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}