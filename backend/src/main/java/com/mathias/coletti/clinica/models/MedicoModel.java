package com.mathias.coletti.clinica.models;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "medicos")
@Getter
@Setter
public class MedicoModel extends PessoaModel{

    @Column(unique = true, nullable = false)
    private String crm;

    @ManyToOne
    @JoinColumn(name = "especialidade_id", nullable = false)
    private EspecialidadeModel especialidade;
}
