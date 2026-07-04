package com.mathias.coletti.clinica.models;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "agendamento")
@Getter
@Setter
public class AgendamentoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "paciente_id", nullable = false)
    private PacienteModel paciente;

    @ManyToOne
    @JoinColumn(name = "medico_id", nullable = false)
    private MedicoModel medico;
    private LocalDateTime dataHora; // Data e hora da consulta
    @Column(name = "data_hora_fim") // Nome da coluna no banco
    private LocalDateTime dataHoraFim;
    private String javaObservacoes;
}
