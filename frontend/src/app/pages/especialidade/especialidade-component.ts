import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { Especialidade } from '../../services/especialidade';

interface EspecialidadeModel {
  id: number | null;
  nome: string;
  descricao: string;
}

@Component({
  selector: 'app-especialidade',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './especialidade-component.html',
  styleUrls: ['./especialidade-component.css'],
})
export class EspecialidadeComponent implements OnInit {
  private service = inject(Especialidade);

  especialidades: EspecialidadeModel[] = [];

  exibirFormulario = false;
  modoEdicao = false;

  especialidadeAtual: EspecialidadeModel = {
    id: null,
    nome: '',
    descricao: '',
  };

  constructor() {
    console.log('COMPONENTE CRIADO');
  }

  ngOnInit(): void {
    console.log('NGONINIT');
    this.carregarDados();
  }

  ngOnDestroy(): void {
    console.log('COMPONENTE DESTRUIDO');
  }
  carregarDados(): void {
    this.service.listar().subscribe({
      next: (data) => {
        this.especialidades = [...data];

        setTimeout(() => {
          console.log('ARRAY NO COMPONENTE:', this.especialidades);
          console.log('TAMANHO:', this.especialidades.length);
        }, 2000);
      },
    });
  }

  abrirFormulario(item?: EspecialidadeModel): void {
    this.modoEdicao = !!item;

    this.especialidadeAtual = item
      ? { ...item }
      : {
          id: null,
          nome: '',
          descricao: '',
        };

    this.exibirFormulario = true;
  }

  fecharFormulario(): void {
    this.exibirFormulario = false;

    this.especialidadeAtual = {
      id: null,
      nome: '',
      descricao: '',
    };
  }

  salvar(): void {
    this.service.salvar(this.especialidadeAtual).subscribe({
      next: () => {
        this.carregarDados();
        this.fecharFormulario();
      },
      error: (err) => {
        console.error(err);
        alert('Erro ao salvar especialidade.');
      },
    });
  }

  excluir(id: number): void {
    if (!confirm('Deseja realmente excluir esta especialidade?')) {
      return;
    }

    this.service.excluir(id).subscribe({
      next: () => {
        this.carregarDados();
      },
      error: (err) => {
        console.error(err);
        alert('Erro ao excluir especialidade.');
      },
    });
  }

  trackById(index: number, item: EspecialidadeModel): number | null {
    return item.id;
  }
}
