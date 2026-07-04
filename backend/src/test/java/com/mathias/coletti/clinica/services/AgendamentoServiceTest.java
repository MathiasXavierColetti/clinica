package com.mathias.coletti.clinica.services;

import com.mathias.coletti.clinica.dtos.AgendamentoRequestDTO;
import com.mathias.coletti.clinica.dtos.AgendamentoResponseDTO;
import com.mathias.coletti.clinica.models.AgendamentoModel;
import com.mathias.coletti.clinica.models.MedicoModel;
import com.mathias.coletti.clinica.models.PacienteModel;
import com.mathias.coletti.clinica.models.enums.StatusAgendamento;
import com.mathias.coletti.clinica.repositories.AgendamentoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AgendamentoServiceTest {

    @Mock private AgendamentoRepository repository;
    @Mock private MedicoService medicoService;
    @Mock private PacienteService pacienteService;

    @InjectMocks private AgendamentoService service;

    private AgendamentoModel agendamento;

    @BeforeEach
    void setUp() {
        agendamento = new AgendamentoModel();
        agendamento.setId(1L);
        agendamento.setStatus(StatusAgendamento.AGENDADO);
        agendamento.setDataHora(LocalDateTime.now().plusHours(1));

        MedicoModel medico = new MedicoModel();
        medico.setNome("Dr. Teste");
        PacienteModel paciente = new PacienteModel();
        paciente.setNome("Paciente Teste");

        agendamento.setMedico(medico);
        agendamento.setPaciente(paciente);
    }

    @Test
    void deveConfirmarAgendamentoComSucesso() {
        when(repository.findById(1L)).thenReturn(Optional.of(agendamento));
        when(repository.save(any(AgendamentoModel.class))).thenReturn(agendamento);

        AgendamentoResponseDTO response = service.alterarStatus(1L, StatusAgendamento.CONFIRMADO);

        assertEquals(StatusAgendamento.CONFIRMADO, response.status());
        verify(repository, times(1)).save(agendamento);
    }

    @Test
    void naoDeveFinalizarConsultaSemEstarConfirmada() {
        // Tenta mudar de AGENDADO para REALIZADO direto
        when(repository.findById(1L)).thenReturn(Optional.of(agendamento));

        assertThrows(RuntimeException.class, () ->
                service.alterarStatus(1L, StatusAgendamento.REALIZADO)
        );
    }

    @Test
    void naoDeveRealizarConsultaNoFuturo() {
        agendamento.setStatus(StatusAgendamento.CONFIRMADO);
        agendamento.setDataHora(LocalDateTime.now().plusDays(1)); // Data no futuro

        when(repository.findById(1L)).thenReturn(Optional.of(agendamento));

        assertThrows(RuntimeException.class, () ->
                service.alterarStatus(1L, StatusAgendamento.REALIZADO)
        );
    }
}