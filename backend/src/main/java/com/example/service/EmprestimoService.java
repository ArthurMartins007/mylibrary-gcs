package com.example.service;

import com.example.entities.Emprestimo;
import com.example.entities.Livro;
import com.example.entities.Status;
import com.example.repository.EmprestimoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class EmprestimoService {

    @Autowired
    private EmprestimoRepository emprestimoRepository;
    @Autowired
    private LivroService livroService;


    // Registrar Empréstimo
    public Emprestimo emprestar(Emprestimo emprestimo, UUID livroId) {
        Livro livro = livroService.buscarPorId(livroId);

        // Não deixa emprestar livro já emprestado
        if (livro.getStatus() == Status.EMPRESTADO) {
            throw new RuntimeException("Este livro já está emprestado no momento.");
        }
        // vincula o livro ao empréstimo
        emprestimo.setLivro_id(livro);

        // ao emprestar: status do livro muda para EMPRESTADO
        livro.setStatus(Status.EMPRESTADO);

        // registra a data do empréstimo
        emprestimo.setDataEmprestimo(LocalDateTime.now());

        //salva no banco
        livroService.salvar(livro);
        //salva o emprestimo
        return emprestimoRepository.save(emprestimo);
    }

    // Registrar Devolução
    public Emprestimo devolver(UUID emprestimoId) {
        Emprestimo emprestimo = emprestimoRepository.findById(emprestimoId).orElseThrow(() -> new RuntimeException("Empréstimo não encontrado."));

        // nao deixa a devolução de um livro que já foi devolvido
        if (emprestimo.getDataDevolvido() != null) {
            throw new RuntimeException("Este empréstimo já foi finalizado.");
        }

        // Registrar data devolução
        emprestimo.setDataDevolvido(LocalDateTime.now());

        // volta ao status disponivel
        Livro livro = emprestimo.getLivro_id();
        livro.setStatus(Status.DISPONIVEL);

        livroService.salvar(livro);

        return emprestimoRepository.save(emprestimo);
    }

    // Lista todos os empréstimos ativos ou geral
    public List<Emprestimo> listarTodos() {
        return emprestimoRepository.findAll();
    }

    // Relatório de Atrasados
    public List<Emprestimo> listarAtrasados() {
        return emprestimoRepository.findByDataDevolucaoFuturaBeforeAndDataDevolvidoIsNull(LocalDateTime.now());
    }
}