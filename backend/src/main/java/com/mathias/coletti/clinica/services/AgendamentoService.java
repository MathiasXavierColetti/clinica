package com.mathias.coletti.clinica.services;

import com.mathias.coletti.clinica.dtos.AgendamentoRequestDTO;
import com.mathias.coletti.clinica.dtos.AgendamentoResponseDTO;
import com.mathias.coletti.clinica.models.AgendamentoModel;
import com.mathias.coletti.clinica.models.MedicoModel;
import com.mathias.coletti.clinica.models.PacienteModel;
import com.mathias.coletti.clinica.repositories.AgendamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    // READ - Listar todos os agendamentos formatados para o Angular
    public List<AgendamentoResponseDTO> listarTodos() {
        return agendamentoRepository.findAll().stream()
                .map(this::converterParaResponseDTO)
                .toList();
    }

    // CREATE - Realizar um novo agendamento aplicando as regras de negócio
    public AgendamentoResponseDTO agendar(AgendamentoRequestDTO dto) {

        // Regra de Negócio 1: Impedir agendamentos no passado
        if (dto.dataHora().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Não é possível realizar um agendamento no passado.");
        }

        // Regra de Negócio 2: Validar consistência do intervalo enviado
        if (!dto.dataHoraFim().isAfter(dto.dataHora())) {
            throw new RuntimeException("O horário de término deve ser posterior ao horário de início.");
        }

        // Regra de Negócio 3: Validar SOBREPOSIÇÃO de horários para o médico
        boolean medicoOcupado = agendamentoRepository.verificarSobreposicaoMedico(
                dto.medicoId(), dto.dataHora(), dto.dataHoraFim()
        );

        if (medicoOcupado) {
            throw new RuntimeException("O médico já possui um agendamento que se sobrepõe a este intervalo de tempo.");
        }

        // Busca e garante a existência das entidades associadas
        MedicoModel medico = medicoService.buscarPorId(dto.medicoId());
        PacienteModel paciente = pacienteService.buscarPorId(dto.pacienteId());

        // Instancia e preenche o modelo correto
        AgendamentoModel agendamento = new AgendamentoModel();
        agendamento.setPaciente(paciente);
        agendamento.setMedico(medico);
        agendamento.setDataHora(dto.dataHora());
        agendamento.setDataHoraFim(dto.dataHoraFim());
        agendamento.setJavaObservacoes(dto.observacoes());

        AgendamentoModel salvo = agendamentoRepository.save(agendamento);
        return converterParaResponseDTO(salvo);
    }

    // DELETE - Cancelar/Excluir uma consulta por ID
    public void cancelar(Long id) {
        AgendamentoModel agendamento = agendamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado com o ID: " + id));
        agendamentoRepository.delete(agendamento);
    }

    // Método Auxiliar Único: Converte a Entidade JPA para o Record de Resposta (Response DTO)
    private AgendamentoResponseDTO converterParaResponseDTO(AgendamentoModel agendamento) {
        return new AgendamentoResponseDTO(
                agendamento.getId(),
                agendamento.getPaciente().getNome(),
                agendamento.getMedico().getNome(),
                agendamento.getMedico().getEspecialidade().getNome(),
                agendamento.getDataHora(),
                agendamento.getDataHoraFim(),
                agendamento.getJavaObservacoes()
        );
    }
}