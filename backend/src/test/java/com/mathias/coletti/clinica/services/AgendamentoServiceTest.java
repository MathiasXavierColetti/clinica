package com.mathias.coletti.clinica.services;

import com.mathias.coletti.clinica.dtos.AgendamentoRequestDTO;
import com.mathias.coletti.clinica.dtos.AgendamentoResponseDTO;
import com.mathias.coletti.clinica.models.AgendamentoModel;
import com.mathias.coletti.clinica.models.EspecialidadeModel;
import com.mathias.coletti.clinica.models.MedicoModel;
import com.mathias.coletti.clinica.models.PacienteModel;
import com.mathias.coletti.clinica.repositories.AgendamentoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AgendamentoServiceTest {

    @Mock
    private AgendamentoRepository agendamentoRepository;
    @Mock
    private MedicoService medicoService;
    @Mock
    private PacienteService pacienteService;

    @InjectMocks
    private AgendamentoService agendamentoService;

    private AgendamentoRequestDTO requestDTO;
    private MedicoModel medico;
    private PacienteModel paciente;
    private AgendamentoModel agendamentoSalvo;

    @BeforeEach
    void setUp() {
        LocalDateTime dataHoraInicio = LocalDateTime.of(2026, 10, 10, 14, 0);
        LocalDateTime dataHoraFim = dataHoraInicio.plusHours(1);

        requestDTO = new AgendamentoRequestDTO(1L, 1L, dataHoraInicio, dataHoraFim, "Consulta de rotina");

        // Solução do NPE: Criamos uma especialidade fictícia e colocamos no médico
        EspecialidadeModel especialidade = new EspecialidadeModel();
        especialidade.setId(1L);
        especialidade.setNome("Clínica Geral");

        medico = new MedicoModel();
        medico.setId(1L);
        medico.setNome("Dr. Mathias");
        medico.setEspecialidade(especialidade); // <--- Vinculado aqui!

        paciente = new PacienteModel();
        paciente.setId(1L);
        paciente.setNome("Paciente Teste");

        agendamentoSalvo = new AgendamentoModel();
        agendamentoSalvo.setId(10L);
        agendamentoSalvo.setMedico(medico);
        agendamentoSalvo.setPaciente(paciente);
        agendamentoSalvo.setDataHora(dataHoraInicio);
        agendamentoSalvo.setDataHoraFim(dataHoraFim);
    }

    @Test
    @DisplayName("Deve agendar uma consulta com sucesso se o médico estiver livre")
    void agendarComSucesso() {
        when(medicoService.buscarPorId(1L)).thenReturn(medico);
        when(pacienteService.buscarPorId(1L)).thenReturn(paciente);
        when(agendamentoRepository.verificarSobreposicaoMedico(eq(1L), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(false);
        when(agendamentoRepository.save(any(AgendamentoModel.class))).thenReturn(agendamentoSalvo);

        AgendamentoResponseDTO resultado = agendamentoService.agendar(requestDTO);

        assertNotNull(resultado);
        assertEquals(10L, resultado.id());
        verify(agendamentoRepository, times(1)).save(any(AgendamentoModel.class));
    }

    @Test
    @DisplayName("Deve bloquear o agendamento se o médico já tiver outra consulta no mesmo horário")
    void agendarComMedicoOcupadoLançaExcecao() {
        // Usamos leniency() para que o Mockito não reclame caso a Service barre o fluxo antes
        lenient().when(medicoService.buscarPorId(1L)).thenReturn(medico);
        lenient().when(pacienteService.buscarPorId(1L)).thenReturn(paciente);

        when(agendamentoRepository.verificarSobreposicaoMedico(eq(1L), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(true);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            agendamentoService.agendar(requestDTO);
        });

        verify(agendamentoRepository, never()).save(any(AgendamentoModel.class));
    }
}