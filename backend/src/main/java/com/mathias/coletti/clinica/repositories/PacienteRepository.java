package com.mathias.coletti.clinica.repositories;

import com.mathias.coletti.clinica.models.PacienteModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PacienteRepository extends JpaRepository<PacienteModel, Long> {

    // Utilizado na service para validação de regras de negócio (evita CPFs duplicados)
    boolean existsByCpf(String cpf);

    // Caso precise buscar um paciente diretamente pelo CPF no futuro
    Optional<PacienteModel> findByCpf(String cpf);
}