package com.mathias.coletti.clinica.services;

import com.mathias.coletti.clinica.dtos.AgendamentoRequestDTO;
import com.mathias.coletti.clinica.dtos.AgendamentoResponseDTO;
import com.mathias.coletti.clinica.enums.StatusAgendamento;
import com.mathias.coletti.clinica.models.AgendamentoModel;
import com.mathias.coletti.clinica.models.MedicoModel;
import com.mathias.coletti.clinica.models.PacienteModel;

import com.mathias.coletti.clinica.repositories.AgendamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AgendamentoService {

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @Autowired
    private MedicoService medicoService;

    @Autowired
    private PacienteService pacienteService;

    // READ
    public List<AgendamentoResponseDTO> listarTodos() {
        return agendamentoRepository.findAll().stream()
                .map(this::converterParaResponseDTO)
                .toList();
    }

    // CREATE
    @Transactional
    public AgendamentoResponseDTO agendar(AgendamentoRequestDTO dto) {
        if (dto.dataHora().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Não é possível realizar um agendamento no passado.");
        }

        if (!dto.dataHoraFim().isAfter(dto.dataHora())) {
            throw new RuntimeException("O horário de término deve ser posterior ao horário de início.");
        }

        boolean medicoOcupado = agendamentoRepository.verificarSobreposicaoMedico(
                dto.medicoId(), dto.dataHora(), dto.dataHoraFim());

        if (medicoOcupado) {
            throw new RuntimeException("O médico já possui um agendamento neste horário.");
        }

        MedicoModel medico = medicoService.buscarPorId(dto.medicoId());
        PacienteModel paciente = pacienteService.buscarPorId(dto.pacienteId());

        AgendamentoModel agendamento = new AgendamentoModel();
        agendamento.setPaciente(paciente);
        agendamento.setMedico(medico);
        agendamento.setDataHora(dto.dataHora());
        agendamento.setDataHoraFim(dto.dataHoraFim());
        agendamento.setJavaObservacoes(dto.observacoes());

        // Status inicial definido como AGENDADO
        agendamento.setStatus(StatusAgendamento.AGENDADO);

        return converterParaResponseDTO(agendamentoRepository.save(agendamento));
    }

    // UPDATE - Mudança de Status (Confirmar, Realizar, Cancelar)
    @Transactional
    public AgendamentoResponseDTO alterarStatus(Long id, StatusAgendamento novoStatus) {
        AgendamentoModel agendamento = agendamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado"));

        // Regra de Negócio: Finalização (REALIZADO)
        if (novoStatus == StatusAgendamento.REALIZADO) {
            if (agendamento.getStatus() != StatusAgendamento.CONFIRMADO) {
                throw new RuntimeException("Apenas agendamentos CONFIRMADOS podem ser finalizados.");
            }
            if (agendamento.getDataHora().isAfter(LocalDateTime.now())) {
                throw new RuntimeException("Não é possível finalizar uma consulta que ainda não ocorreu.");
            }
        }

        agendamento.setStatus(novoStatus);
        return converterParaResponseDTO(agendamentoRepository.save(agendamento));
    }

    // DELETE (Cancelamento via alteração de status)
    @Transactional
    public void cancelar(Long id) {
        AgendamentoModel agendamento = agendamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado"));

        agendamento.setStatus(StatusAgendamento.CANCELADO);
        agendamentoRepository.save(agendamento);
    }

    // CONVERSOR
    private AgendamentoResponseDTO converterParaResponseDTO(AgendamentoModel a) {
        return new AgendamentoResponseDTO(
                a.getId(),
                a.getPaciente().getNome(),
                a.getMedico().getNome(),
                a.getMedico().getEspecialidade().getNome(),
                a.getDataHora(),
                a.getDataHoraFim(),
                a.getJavaObservacoes(),
                a.getStatus() // Certifique-se que o DTO tenha este campo
        );
    }
}