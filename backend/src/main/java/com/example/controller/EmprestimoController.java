package com.example.controller;

import com.example.entities.Emprestimo;
import com.example.service.EmprestimoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/emprestimos")
@CrossOrigin("*")
public class EmprestimoController {

    @Autowired
    private EmprestimoService emprestimoService;

    @PostMapping("/emprestar")
    public ResponseEntity<Emprestimo> emprestar(
            @RequestBody Emprestimo emprestimo,
            @RequestParam UUID livroId) {
        Emprestimo novoEmprestimo = emprestimoService.emprestar(emprestimo, livroId);
        return ResponseEntity.ok(novoEmprestimo);
    }

    @PostMapping("/{id}/devolver")
    public ResponseEntity<Emprestimo> devolver(@PathVariable UUID id) {
        Emprestimo emprestimoDevolvido = emprestimoService.devolver(id);
        return ResponseEntity.ok(emprestimoDevolvido);
    }

    @GetMapping
    public ResponseEntity<List<Emprestimo>> listarTodos() {
        return ResponseEntity.ok(emprestimoService.listarTodos());
    }

    @GetMapping("/ativos")
    public ResponseEntity<List<Emprestimo>> listarAtivos() {
        List<Emprestimo> ativos = emprestimoService.listarTodos().stream()
                .filter(e -> e.getDataDevolvido() == null)
                .toList();
        return ResponseEntity.ok(ativos);
    }

    @GetMapping("/atrasados")
    public ResponseEntity<List<Emprestimo>> listarAtrasados() {
        return ResponseEntity.ok(emprestimoService.listarAtrasados());
    }
}