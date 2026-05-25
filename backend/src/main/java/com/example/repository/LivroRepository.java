package com.example.repository;

import com.example.entities.Livro;
import com.example.entities.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LivroRepository extends JpaRepository<Livro, UUID> {
    List<Livro> findByStatus(Status status);

    List<Livro> findByTituloContainingIgnoreCaseOrAutorContainingIgnoreCase(String titulo,String autor);
}
