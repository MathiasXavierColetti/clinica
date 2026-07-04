package com.mathias.coletti.clinica.dtos;

import com.mathias.coletti.clinica.enums.StatusAgendamento;

import java.time.LocalDateTime;

public record AgendamentoResponseDTO(
        Long id,
        String paciente,
        String medico,
        String especialidade,
        LocalDateTime dataHoraInicio,
        LocalDateTime dataHoraFim,
        String observacoes,
        StatusAgendamento status
) {
}