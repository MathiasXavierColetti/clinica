package com.mathias.coletti.clinica.services;

import com.mathias.coletti.clinica.dtos.MedicoRequestDTO;
import com.mathias.coletti.clinica.dtos.MedicoResponseDTO;
import com.mathias.coletti.clinica.models.EspecialidadeModel;
import com.mathias.coletti.clinica.models.MedicoModel;
import com.mathias.coletti.clinica.repositories.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicoService {
    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private EspecialidadeService especialidadeService;

    public List<MedicoResponseDTO> listarTodos() {
        return medicoRepository.findAll().stream()
                .map(this::converterParaResponseDTO)
                .toList();
    }

    public MedicoModel buscarPorId(Long id) {
        return medicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Médico não encontrado com o ID: " + id));
    }

    // Método solicitado para buscar médicos por CPF
    public MedicoResponseDTO buscarPorCpf(String cpf) {
        MedicoModel medico = medicoRepository.findByCpf(cpf)
                .orElseThrow(() -> new RuntimeException("Médico não encontrado com o CPF: " + cpf));
        return converterParaResponseDTO(medico);
    }

    public MedicoResponseDTO salvar(MedicoRequestDTO dto) {
        // Regra de Negócio 1: Impede CRMs duplicados
        if (medicoRepository.existsByCrm(dto.crm())) {
            throw new RuntimeException("Já existe um médico cadastrado com este CRM.");
        }

        // Regra de Negócio 2: Impede CPFs duplicados para médicos
        if (medicoRepository.existsByCpf(dto.cpf())) {
            throw new RuntimeException("Já existe um médico cadastrado com este CPF.");
        }

        // Busca a especialidade usando a service
        EspecialidadeModel esp = especialidadeService.buscarPorId(dto.especialidadeId());

        // Mapeia DTO para a Entidade herdada
        MedicoModel medico = new MedicoModel();
        medico.setNome(dto.nome());
        medico.setCrm(dto.crm());
        medico.setEmail(dto.email());
        medico.setCpf(dto.cpf()); // Setando o CPF herdado de Pessoa
        medico.setEspecialidade(esp);

        MedicoModel medicoSalvo = medicoRepository.save(medico);
        return converterParaResponseDTO(medicoSalvo);
    }

    private MedicoResponseDTO converterParaResponseDTO(MedicoModel medico) {
        return new MedicoResponseDTO(
                medico.getId(),
                medico.getNome(),
                medico.getCrm(),
                medico.getEmail(),
                medico.getEspecialidade().getNome()
        );
    }

    public MedicoResponseDTO atualizar(Long id, MedicoRequestDTO dto) {
        MedicoModel medico = buscarPorId(id);

        // Validações de unicidade para alteração
        if (!medico.getCrm().equals(dto.crm()) && medicoRepository.existsByCrm(dto.crm())) {
            throw new RuntimeException("Já existe um médico cadastrado com este CRM.");
        }
        if (!medico.getCpf().equals(dto.cpf()) && medicoRepository.existsByCpf(dto.cpf())) {
            throw new RuntimeException("Já existe um médico cadastrado com este CPF.");
        }

        EspecialidadeModel esp = especialidadeService.buscarPorId(dto.especialidadeId());

        medico.setNome(dto.nome());
        medico.setCrm(dto.crm());
        medico.setEmail(dto.email());
        medico.setCpf(dto.cpf());
        medico.setEspecialidade(esp);

        return converterParaResponseDTO(medicoRepository.save(medico));
    }

    public void excluir(Long id) {
        MedicoModel medico = buscarPorId(id);
        medicoRepository.delete(medico);
    }
}
