import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule, Router } from '@angular/router';
import { ApiService } from '../../services/api';

@Component({
  selector: 'app-livro-form',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './livro-form.html',
  styleUrl: './livro-form.css'
})
export class LivroFormComponent implements OnInit {
  // Estrutura que o Java espera receber
  livro = {
    titulo: '',
    autor: '',
    categoria: { id: null },
    status: 'DISPONIVEL' // Regra de negócio: todo livro novo nasce disponível
  };

  categorias: any[] = [];

  constructor(private api: ApiService, private router: Router) {}

  ngOnInit(): void {
    // Carrega as categorias assim que a tela abre
    this.api.getCategorias().subscribe(data => {
      this.categorias = data;
    });
  }

  salvar(): void {
    if (!this.livro.titulo || !this.livro.autor || !this.livro.categoria.id) {
      alert('Por favor, preencha todos os campos!');
      return;
    }

    this.api.criarLivro(this.livro).subscribe(() => {
      alert('Livro cadastrado com sucesso!');
      this.router.navigate(['/livros']); // Volta para a lista automaticamente
    });
  }
}
