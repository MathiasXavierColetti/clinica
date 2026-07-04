package com.mathias.coletti.clinica.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "especialidades")
@Getter
@Setter
public class EspecialidadeModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome; // Ex: "Cardiologia", "Pediatria", "Dermatologia"
    private String descricao;


}
