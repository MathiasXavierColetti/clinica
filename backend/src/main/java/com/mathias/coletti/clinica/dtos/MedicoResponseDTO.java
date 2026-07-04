package com.mathias.coletti.clinica.dtos;

public record MedicoResponseDTO (Long id,
                                String nome,
                                String crm,
                                String email,
                                String especialidadeNome){
}
