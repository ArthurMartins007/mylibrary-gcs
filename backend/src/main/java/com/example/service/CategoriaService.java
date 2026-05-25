package com.example.service;

import com.example.entities.Categoria;
import com.example.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    // Criar nova Categoria
    public Categoria salvar(Categoria categoria) {
        if (categoriaRepository.existsByNome(categoria.getNome())) {
            throw new RuntimeException("Já existe uma categoria cadastrada com este nome.");
        }

        return categoriaRepository.save(categoria);
    }

    public List<Categoria> listarTodas() {
        return categoriaRepository.findAll();
    }

    public Categoria buscarPorId(UUID id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada."));
    }

    public void excluir(UUID id) {
        Categoria categoria = buscarPorId(id);
        // nao deixa excluir categoria com livros
        if (categoria.getLivros() != null && !categoria.getLivros().isEmpty()) {
            throw new RuntimeException("Não é possível excluir esta categoria pois há livros vinculados nela.");
        }

        categoriaRepository.delete(categoria);
    }
}