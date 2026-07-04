package com.mathias.coletti.clinica.repositories;


import com.mathias.coletti.clinica.models.MedicoModel;
import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Registered
public interface MedicoRepository extends JpaRepository<MedicoModel, Long> {
    boolean existsByCrm(String crm);
    boolean existsByCpf(String cpf); // <-- Adicionado para a nossa validação
    Optional<MedicoModel> findByCpf(String cpf);
}
