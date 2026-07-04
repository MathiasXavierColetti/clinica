package com.mathias.coletti.clinica.dtos;

public record MedicoRequestDTO(String nome,
                               String crm,
                               String email,
                               Long especialidadeId,
                               String cpf ) {
}
