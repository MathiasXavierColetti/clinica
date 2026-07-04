package com.mathias.coletti.clinica.services;

import com.mathias.coletti.clinica.dtos.EspecialidadeRequestDTO;
import com.mathias.coletti.clinica.models.EspecialidadeModel;
import com.mathias.coletti.clinica.repositories.EspecialidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EspecialidadeService {

    @Autowired
    private EspecialidadeRepository repository;

    public List<EspecialidadeModel> listarTodos() { return repository.findAll(); }

    public EspecialidadeModel buscarPorId(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Especialidade não encontrada: " + id));
    }

    public EspecialidadeModel salvar(EspecialidadeRequestDTO dto) {
        EspecialidadeModel esp = new EspecialidadeModel();
        esp.setNome(dto.nome());
        esp.setDescricao(dto.descricao());
        return repository.save(esp);
    }

    public EspecialidadeModel atualizar(Long id, EspecialidadeRequestDTO dto) {
        EspecialidadeModel esp = buscarPorId(id);
        esp.setNome(dto.nome());
        esp.setDescricao(dto.descricao());
        return repository.save(esp);
    }

    public void excluir(Long id) {
        EspecialidadeModel esp = buscarPorId(id);
        repository.delete(esp);
    }
}