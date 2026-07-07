import { Component, OnInit, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { EspecialidadeService } from '../../services/especialidades/especialidade.service';
import { EspecialidadeModel } from '../class/especialidadeModel.model';

@Component({
  selector: 'app-especialidade',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './especialidade-component.html',
  styleUrls: ['./especialidade-component.css'],
})
export class EspecialidadeComponent implements OnInit {
  private readonly service = inject(EspecialidadeService);

  // Usando Signals para melhor performance e reatividade
  especialidades = signal<EspecialidadeModel[]>([]);
  exibirFormulario = signal(false);
  modoEdicao = signal(false);

  // Objeto inicial para resetar o form
  private readonly objetoVazio: EspecialidadeModel = { id: null, nome: '', descricao: '' };
  especialidadeAtual = signal<EspecialidadeModel>({ ...this.objetoVazio });

  ngOnInit(): void {
    this.carregarDados();
  }

  carregarDados(): void {
    this.service.listar().subscribe({
      next: (data) => this.especialidades.set(data),
      error: (err) => console.error('Erro ao buscar dados:', err),
    });
  }

  abrirFormulario(item?: EspecialidadeModel): void {
    this.modoEdicao.set(!!item);
    this.especialidadeAtual.set(item ? { ...item } : { ...this.objetoVazio });
    this.exibirFormulario.set(true);
  }

  fecharFormulario(): void {
    this.exibirFormulario.set(false);
    this.especialidadeAtual.set({ ...this.objetoVazio });
  }

  salvar(): void {
    const data = this.especialidadeAtual();
    this.service.salvar(data).subscribe({
      next: () => {
        this.carregarDados();
        this.fecharFormulario();
      },
      error: (err) => alert('Erro ao salvar: ' + (err.error?.message || err.message)),
    });
  }

  excluir(id: number | null): void {
    if (id && confirm('Deseja realmente excluir esta especialidade?')) {
      this.service.excluir(id).subscribe(() => this.carregarDados());
    }
  }
}
