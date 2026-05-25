package com.example.controller;

import com.example.entities.Emprestimo;
import com.example.entities.Status;
import com.example.service.EmprestimoService;
import com.example.service.LivroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin("*")
public class DashboardController {

    @Autowired
    private LivroService livroService;

    @Autowired
    private EmprestimoService emprestimoService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> obterDadosDashboard() {
        Map<String, Object> dados = new HashMap<>();

        // Total de livros cadastrados
        long totalLivros = livroService.listarTodos().size();

        // Total de livros disponíveis
        long totalDisponiveis = livroService.buscarPorStatus(Status.DISPONIVEL).size();

        // Total de livros emprestados
        long totalEmprestados = livroService.buscarPorStatus(Status.EMPRESTADO).size();

        // Total de empréstimos ativos
        List<Emprestimo> todosEmprestimos = emprestimoService.listarTodos();
        long emprestimosAtivos = todosEmprestimos.stream()
                .filter(e -> e.getDataDevolvido() == null)
                .count();

        // 5 últimos empréstimos realizados
        List<Emprestimo> ultimosCinco = todosEmprestimos.stream()
                .sorted((e1, e2) -> e2.getDataEmprestimo().compareTo(e1.getDataEmprestimo()))
                .limit(5)
                .toList();

        dados.put("totalLivros", totalLivros);
        dados.put("totalDisponiveis", totalDisponiveis);
        dados.put("totalEmprestados", totalEmprestados);
        dados.put("totalEmprestimosAtivos", emprestimosAtivos);
        dados.put("ultimosEmprestimos", ultimosCinco);

        return ResponseEntity.ok(dados);
    }
}