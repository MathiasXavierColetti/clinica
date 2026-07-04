package com.mathias.coletti.clinica.models;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pacientes")
@Getter
@Setter
public class PacienteModel extends PessoaModel{

    private String telefone;
}
