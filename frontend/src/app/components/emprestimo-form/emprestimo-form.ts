import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { ApiService } from '../../services/api';
@Component({
  selector: 'app-emprestimo-form',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './emprestimo-form.html',
  styleUrl: './emprestimo-form.css'
})
export class EmprestimoFormComponent implements OnInit {
  emprestimo = {
    livro: { id: null },
    nomeUsuario: ''
  };
  livrosDisponiveis: any[] = [];

  constructor(private api: ApiService, private router: Router) {}

  ngOnInit(): void {
    this.api.getLivros().subscribe(data => {
      // Filtra para mostrar apenas os livros que podem ser emprestados
      this.livrosDisponiveis = data.filter((l: any) => l.status === 'DISPONIVEL');
    });
  }

  salvar(): void {
    if (!this.emprestimo.livro.id || !this.emprestimo.nomeUsuario) {
      alert('Preencha todos os campos!');
      return;
    }

    this.api.emprestarLivro(this.emprestimo).subscribe(() => {
      alert('Empréstimo realizado com sucesso!');
      this.router.navigate(['/livros']);
    });
  }
}
