package com.mathias.coletti.clinica.dtos;

public record PacienteRequestDTO(String nome,
                                 String email,
                                 String telefone,
                                 String cpf) {
}
