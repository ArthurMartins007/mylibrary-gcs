import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ApiService } from '../../services/api';

@Component({
  selector: 'app-categoria-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './categoria-list.html',
  styleUrl: './categoria-list.css'
})
export class CategoriaListComponent implements OnInit {
  categorias: any[] = [];
  novaCategoria = { nome: '' };

  constructor(private api: ApiService) {}

  ngOnInit(): void {
    this.carregarCategorias();
  }

  carregarCategorias(): void {
    this.api.getCategorias().subscribe(data => {
      this.categorias = data;
    });
  }

  salvar(): void {
    if (!this.novaCategoria.nome) {
      alert('Digite um nome para a categoria!');
      return;
    }

    this.api.criarCategoria(this.novaCategoria).subscribe(() => {
      alert('Categoria criada!');
      this.novaCategoria.nome = ''; // Limpa o campo
      this.carregarCategorias(); // Atualiza a lista
    });
  }

  excluir(categoria: any): void {
    if (confirm(`Excluir a categoria "${categoria.nome}"?`)) {
      this.api.excluirCategoria(categoria.id).subscribe({
        next: () => {
          alert('Excluída com sucesso!');
          this.carregarCategorias();
        },
        error: (err) => {
          // Atende a regra RN02 do seu PDF (Não excluir se tiver livros)
          alert('Erro: Esta categoria possui livros vinculados e não pode ser excluída.');
        }
      });
    }
  }
}
