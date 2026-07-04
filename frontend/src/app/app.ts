import { CommonModule } from '@angular/common';
import { Component, signal } from '@angular/core';
import { RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-root',
imports: [CommonModule, RouterOutlet, RouterLink, RouterLinkActive],  templateUrl: './app.html',
  styleUrl: './app.css',
})
export class App {
  proximasConsultas = [
    {
      id: 1,
      paciente: 'Carlos Silva',
      medico: 'Dr. André Camargo',
      especialidade: 'Cardiologia',
      horario: '09:00',
      status: 'Confirmado',
    },
    {
      id: 2,
      paciente: 'Mariana Costa',
      medico: 'Dra. Juliana Reys',
      especialidade: 'Pediatria',
      horario: '10:30',
      status: 'Pendente',
    },
    {
      id: 3,
      paciente: 'Roberto Souza',
      medico: 'Dr. André Camargo',
      especialidade: 'Cardiologia',
      horario: '14:00',
      status: 'Confirmado',
    },
    {
      id: 4,
      paciente: 'Ana Paula Lima',
      medico: 'Dra. Fernanda Cruz',
      especialidade: 'Dermatologia',
      horario: '15:15',
      status: 'Cancelado',
    },
  ];
  abrirModalAgendamento() {
    alert('Em breve: Aqui vamos abrir a tela para criar um agendamento!');
  }
}
