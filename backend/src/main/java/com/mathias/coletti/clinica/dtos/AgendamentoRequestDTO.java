package com.mathias.coletti.clinica.dtos;

import java.time.LocalDateTime;

public record AgendamentoRequestDTO(Long pacienteId,
                                    Long medicoId,
                                    LocalDateTime dataHora,
                                    LocalDateTime dataHoraFim,
                                    String observacoes) {
}
