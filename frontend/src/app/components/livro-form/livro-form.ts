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
    // 1. Validação hiper-rigorosa (barra o null puro, undefined e a palavra 'null')
    if (!this.livro.titulo || !this.livro.autor || !this.livro.categoria.id || this.livro.categoria.id === 'null' as any) {
      alert('Por favor, preencha todos os campos e selecione uma categoria válida!');
      return;
    }

    // 2. Monta o pacote garantindo que o ID vai como NÚMERO (Number) para o Java
    const payloadParaJava = {
      titulo: this.livro.titulo,
      autor: this.livro.autor,
      categoria: {
        id: Number(this.livro.categoria.id)
      },
      status: this.livro.status
    };

    // 3. Envia para o backend
    this.api.criarLivro(payloadParaJava).subscribe({
      next: () => {
        alert('Livro cadastrado com sucesso!');
        this.router.navigate(['/livros']); // Volta para a lista
      },
      error: (err) => {
        console.error('Erro do backend:', err);
        alert('Erro ao salvar o livro! Verifique o console.');
      }
    });
  }
}
