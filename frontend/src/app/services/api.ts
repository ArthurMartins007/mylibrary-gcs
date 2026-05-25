import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  private baseUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) { }

  // --- Endpoints de Livros ---
  getLivros(): Observable<any> {
    return this.http.get(`${this.baseUrl}/livros`);
  }

  getLivrosPorCategoria(categoriaId: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/livros?categoria=${categoriaId}`);
  }

  criarLivro(livro: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/livros`, livro);
  }

  excluirLivro(id: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/livros/${id}`);
  }

  // --- Endpoints de Categorias  ---
  getCategorias(): Observable<any> {
    return this.http.get(`${this.baseUrl}/categorias`);
  }
  // --- Endpoints de Empréstimos ---
  emprestarLivro(emprestimo: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/emprestimos`, emprestimo);
  }

  devolverLivro(livroId: number): Observable<any> {
    // Supondo que o seu backend aceite a devolução pelo ID do livro
    return this.http.put(`${this.baseUrl}/emprestimos/devolver/${livroId}`, {});
  }
  // --- Endpoints de Categorias (Gerenciamento) ---
  criarCategoria(categoria: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/categorias`, categoria);
  }

  excluirCategoria(id: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/categorias/${id}`);
  }
}
