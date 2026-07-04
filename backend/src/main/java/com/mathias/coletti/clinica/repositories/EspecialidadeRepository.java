package com.mathias.coletti.clinica.repositories;

import com.mathias.coletti.clinica.models.EspecialidadeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EspecialidadeRepository extends JpaRepository<EspecialidadeModel, Long> {
}
