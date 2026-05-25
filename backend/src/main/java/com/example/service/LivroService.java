package com.example.service;

import com.example.entities.Livro;
import com.example.entities.Status;
import com.example.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class LivroService {

    @Autowired
    private LivroRepository livroRepository;

    // Cadastrar Livro
    public Livro salvar(Livro livro) {
        return livroRepository.save(livro);
    }

    // Lista todos
    public List<Livro> listarTodos() {
        return livroRepository.findAll();
    }

    public List<Livro> buscarPorStatus(Status status) {
        return livroRepository.findByStatus(status);
    }

    public List<Livro> buscarPorTituloOuAutor(String termo) {
        return livroRepository.findByTituloContainingIgnoreCaseOrAutorContainingIgnoreCase(termo, termo);
    }

    public Livro buscarPorId(UUID id) {
        return livroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Livro não encontrado."));
    }

    public void excluir(UUID id) {
        Livro livro = buscarPorId(id);

        // Se o livro estiver emprestado não pode exlcuir
        if (livro.getStatus() == Status.EMPRESTADO) {
            throw new RuntimeException("Não é possível excluir um livro que está emprestado.");
        }

        livroRepository.delete(livro);
    }
}