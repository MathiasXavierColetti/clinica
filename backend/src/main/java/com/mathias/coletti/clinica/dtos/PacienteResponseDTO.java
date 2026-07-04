package com.mathias.coletti.clinica.dtos;

public record PacienteResponseDTO(Long id,
                                  String nome,
                                  String cpf,
                                  String email,
                                  String telefone) {
}
