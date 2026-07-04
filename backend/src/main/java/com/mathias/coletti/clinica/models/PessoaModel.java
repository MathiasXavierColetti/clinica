package com.mathias.coletti.clinica.models;

import jakarta.persistence.*;
import lombok.Data;

@MappedSuperclass
@Data
public class PessoaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String email;

    @Column(unique = true, nullable = false)
    private String cpf;
}
