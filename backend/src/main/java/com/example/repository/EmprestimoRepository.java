package com.example.repository;

import com.example.entities.Emprestimo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface EmprestimoRepository extends JpaRepository <Emprestimo,UUID> {
    List<Emprestimo> findByDataDevolucaoFuturaBeforeAndDataDevolvidoIsNull(LocalDateTime dataAtual);
}
