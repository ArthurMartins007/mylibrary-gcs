package com.example.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "TB_EMPRESTIMO")
public class Emprestimo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column
    @NotBlank(message = "Coloque o nome da pessoa que você emprestou")
    private String nomePessoa;
    @Column
    private String telefone;
    @Column
    @NotNull
    private LocalDateTime dataEmprestimo;
    @Column
    @NotNull
    private LocalDateTime dataDevolucaoFutura;
    @Column
    private LocalDateTime dataDevolvido;


    @ManyToOne
    @JoinColumn(name = "livro_id")
    private Livro livro_id;

    public Emprestimo(String nomePessoa, String telefone, LocalDateTime dataEmprestimo) {
        this.nomePessoa = nomePessoa;
        this.telefone = telefone;
        this.dataEmprestimo = dataEmprestimo;
    }

    public Emprestimo() {
    }

    public UUID getId() {
        return id;
    }

    public String getNomePessoa() {
        return nomePessoa;
    }

    public void setNomePessoa(String nomePessoa) {
        this.nomePessoa = nomePessoa;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public LocalDateTime getDataEmprestimo() {
        return dataEmprestimo;
    }

    public void setDataEmprestimo(LocalDateTime dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo;
    }

    public LocalDateTime getDataDevolucaoFutura() {
        return dataDevolucaoFutura;
    }

    public void setDataDevolucaoFutura(LocalDateTime dataDevolucaoFutura) {
        this.dataDevolucaoFutura = dataDevolucaoFutura;
    }

    public LocalDateTime getDataDevolvido() {
        return dataDevolvido;
    }

    public void setDataDevolvido(LocalDateTime dataDevolvido) {
        this.dataDevolvido = dataDevolvido;
    }

    public Livro getLivro_id() {
        return livro_id;
    }

    public void setLivro_id(Livro livro_id) {
        this.livro_id = livro_id;
    }

    @Override
    public String toString() {
        return "Emprestimo{" +
                "id=" + id +
                ", nomePessoa='" + nomePessoa + '\'' +
                ", telefone='" + telefone + '\'' +
                ", dataEmprestimo=" + dataEmprestimo +
                ", dataDevolucaoFutura=" + dataDevolucaoFutura +
                ", dataDevolvido=" + dataDevolvido +
                ", livro_id=" + livro_id +
                '}';
    }
}

