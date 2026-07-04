package com.mathias.coletti.clinica.dtos;

import java.time.LocalDateTime;

public record AndamentoRequestDTO (Long pacienteId,
                                   Long medicoId,
                                   LocalDateTime dataHora,
                                   String observacoes){
}
