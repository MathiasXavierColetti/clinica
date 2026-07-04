package com.mathias.coletti.clinica.repositories;

import com.mathias.coletti.clinica.models.AgendamentoModel;
import com.mathias.coletti.clinica.models.PacienteModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface AgendamentoRepository  extends JpaRepository<AgendamentoModel, Long> {
    @Query("SELECT COUNT(a) > 0 FROM AgendamentoModel a WHERE a.medico.id = :medicoId " +
            "AND :dataHoraInicio < a.dataHoraFim AND :dataHoraFim > a.dataHora")
    boolean verificarSobreposicaoMedico(
            @Param("medicoId") Long medicoId,
            @Param("dataHoraInicio") LocalDateTime dataHoraInicio,
            @Param("dataHoraFim") LocalDateTime dataHoraFim
    );
}
