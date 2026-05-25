import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { ApiService } from '../../services/api';

@Component({
  selector: 'app-livro-list',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './livro-list.html',
  styleUrl: './livro-list.css'
})
export class LivroListComponent implements OnInit {
  livros: any[] = [];
  livrosFiltrados: any[] = [];
  categorias: any[] = [];

  // Variáveis para os filtros
  termoBusca: string = '';
  categoriaSelecionada: string = '';
  statusSelecionado: string = '';

  constructor(private api: ApiService) {}

  ngOnInit(): void {
    this.carregarCategorias();
    this.carregarLivros();
  }

  carregarCategorias(): void {
    this.api.getCategorias().subscribe(data => {
      this.categorias = data;
    });
  }

  carregarLivros(): void {
    this.api.getLivros().subscribe(data => {
      this.livros = data;
      this.livrosFiltrados = data;
    });
  }

  filtrar(): void {
    this.livrosFiltrados = this.livros.filter(livro => {
      const matchBusca = this.termoBusca ?
        (livro.titulo.toLowerCase().includes(this.termoBusca.toLowerCase()) ||
          livro.autor.toLowerCase().includes(this.termoBusca.toLowerCase())) : true;

      const matchCategoria = this.categoriaSelecionada ?
        livro.categoria.id.toString() === this.categoriaSelecionada : true;

      const matchStatus = this.statusSelecionado ?
        livro.status === this.statusSelecionado : true;

      return matchBusca && matchCategoria && matchStatus;
    });
  }

  excluir(livro: any): void {
    if (livro.status !== 'DISPONIVEL') {
      alert('Apenas livros com status DISPONIVEL podem ser excluídos!');
      return;
    }

    if (confirm(`Deseja excluir o livro "${livro.titulo}"?`)) {
      this.api.excluirLivro(livro.id).subscribe(() => {
        alert('Livro excluído com sucesso!');
        this.carregarLivros();
      });
    }
  }
  devolver(livro: any): void {
    if (confirm(`Deseja devolver o livro "${livro.titulo}"?`)) {
      this.api.devolverLivro(livro.id).subscribe(() => {
        alert('Livro devolvido com sucesso!');
        this.carregarLivros(); // Atualiza a tabela automaticamente
      });
    }
  }
}
