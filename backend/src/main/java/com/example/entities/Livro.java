package com.example.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

@Entity
@Table ( name = "TB_LIVRO")
public class Livro {
    @Id
    @GeneratedValue ( strategy = GenerationType.AUTO)
    private UUID id;
    @Column
    @NotNull(message= "o seu livro tem que ter um ano")
    private int ano;
    @Column
    @NotBlank(message = "Todo livro tem que ter um autor")
    private String autor;
    @Column
    @NotNull(message = "O codigo ISBN é obrigatório")
    private Integer isbn;
    @Column
    @NotBlank(message = "O título do seu livro é obrigatorio")
    private String titulo;
    @Column
    @Enumerated (EnumType.STRING)
    private Status status;
    @ManyToOne
    @JoinColumn( name = "categoria_id")
    private Categoria categoria_id;
    @OneToMany(mappedBy = "livro_id")
    private List<Emprestimo> emprestimos;


    public Livro(String autor, Integer isbn, String titulo, Status status, int ano) {
        this.autor = autor;
        this.isbn = isbn;
        this.titulo = titulo;
        this.status = Status.DISPONIVEL;
        this.ano = ano;
    }

    public Livro() {
    }

    public UUID getId() {
        return id;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public Integer getIsbn() {
        return isbn;
    }

    public void setIsbn(Integer isbn) {
        this.isbn = isbn;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    @Override
    public String toString() {
        return "Livro{" +
                "id=" + id +
                ", ano=" + ano +
                ", autor='" + autor + '\'' +
                ", isbn=" + isbn +
                ", titulo='" + titulo + '\'' +
                ", status=" + status +
                ", categoria_id=" + categoria_id +
                ", emprestimos=" + emprestimos +
                '}';
    }
}
