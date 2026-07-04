import { Routes } from '@angular/router';
import { EspecialidadeComponent } from './pages/especialidade/especialidade-component';

export const routes: Routes = [
  { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
  { path: 'especialidades', component: EspecialidadeComponent }, //
];
