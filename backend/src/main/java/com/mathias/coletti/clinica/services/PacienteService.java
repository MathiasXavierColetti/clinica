package com.mathias.coletti.clinica.services;

import com.mathias.coletti.clinica.dtos.PacienteRequestDTO;
import com.mathias.coletti.clinica.dtos.PacienteResponseDTO;
import com.mathias.coletti.clinica.models.PacienteModel;
import com.mathias.coletti.clinica.repositories.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository repository;

    // READ - Listar todos convertendo para DTO (usado pelo Controller)
    public List<PacienteResponseDTO> listarTodos() {
        return repository.findAll().stream()
                .map(this::converterParaResponseDTO)
                .toList();
    }

    // READ - Uso interno para outras services (como AgendamentoService) que precisam do Model puro
    public PacienteModel buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado com o ID: " + id));
    }

    // READ - Uso do Controller para expor o paciente formatado em DTO na API
    public PacienteResponseDTO buscarPorIdDto(Long id) {
        return converterParaResponseDTO(buscarPorId(id));
    }

    // CREATE - Salvar novo paciente aplicando validações
    public PacienteResponseDTO salvar(PacienteRequestDTO dto) {
        // Regra de negócio: Impede CPFs duplicados
        if (repository.existsByCpf(dto.cpf())) {
            throw new RuntimeException("Já existe um paciente cadastrado com este CPF.");
        }

        PacienteModel paciente = new PacienteModel();
        paciente.setNome(dto.nome());
        paciente.setCpf(dto.cpf());
        paciente.setEmail(dto.email());
        paciente.setTelefone(dto.telefone());

        return converterParaResponseDTO(repository.save(paciente));
    }

    // UPDATE - Atualizar paciente existente
    public PacienteResponseDTO atualizar(Long id, PacienteRequestDTO dto) {
        PacienteModel paciente = buscarPorId(id); // Garante que o paciente existe

        // Se o CPF mudou, valida se o novo CPF já não pertence a outra pessoa
        if (!paciente.getCpf().equals(dto.cpf()) && repository.existsByCpf(dto.cpf())) {
            throw new RuntimeException("Já existe um paciente cadastrado com este CPF.");
        }

        paciente.setNome(dto.nome());
        paciente.setCpf(dto.cpf());
        paciente.setEmail(dto.email());
        paciente.setTelefone(dto.telefone());

        return converterParaResponseDTO(repository.save(paciente));
    }

    // DELETE - Excluir paciente do sistema
    public void excluir(Long id) {
        PacienteModel paciente = buscarPorId(id);
        repository.delete(paciente);
    }

    // Método Auxiliar: Conversor isolado de Model para DTO de Resposta
    private PacienteResponseDTO converterParaResponseDTO(PacienteModel paciente) {
        return new PacienteResponseDTO(
                paciente.getId(),
                paciente.getNome(),
                paciente.getCpf(),
                paciente.getEmail(),
                paciente.getTelefone()
        );
    }
}