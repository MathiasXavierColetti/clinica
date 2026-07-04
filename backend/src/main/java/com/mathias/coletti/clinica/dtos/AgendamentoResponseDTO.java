package com.mathias.coletti.clinica.dtos;

import java.time.LocalDateTime;

public record AgendamentoResponseDTO(Long id,
                                     String pacienteNome,
                                     String medicoNome, String especialidadeNome,
                                     LocalDateTime dataHora, LocalDateTime dataHoraFim, // <-- Adicione esta linha aqui
                                     String observacoes) {
}
