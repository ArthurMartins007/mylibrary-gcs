import { Routes } from '@angular/router';
import { LivroListComponent } from './components/livro-list/livro-list';
import { LivroFormComponent } from './components/livro-form/livro-form';
import { EmprestimoFormComponent } from './components/emprestimo-form/emprestimo-form';
import { CategoriaListComponent } from './components/categoria-list/categoria-list';

export const routes: Routes = [
  { path: '', redirectTo: 'livros', pathMatch: 'full' },
  { path: 'livros', component: LivroListComponent },
  { path: 'livros/novo', component: LivroFormComponent },
  { path: 'emprestimos/novo', component: EmprestimoFormComponent },
  { path: 'categorias', component: CategoriaListComponent } // <-- Rota nova!
];
