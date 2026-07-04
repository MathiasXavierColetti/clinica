package com.mathias.coletti.clinica.controllers;

import com.mathias.coletti.clinica.dtos.AgendamentoRequestDTO;
import com.mathias.coletti.clinica.dtos.AgendamentoResponseDTO;
import com.mathias.coletti.clinica.dtos.EspecialidadeRequestDTO;
import com.mathias.coletti.clinica.models.EspecialidadeModel;
import com.mathias.coletti.clinica.services.AgendamentoService;
import com.mathias.coletti.clinica.services.EspecialidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/agendamentos")
@CrossOrigin(origins = "*")
public class AgendamentoController {
    // Altere para a Service correta:
    @Autowired
    private AgendamentoService service;

    @GetMapping
    public ResponseEntity<List<AgendamentoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos()); // <-- Corrigido para "Todos"
    }

    @PostMapping
    public ResponseEntity<AgendamentoResponseDTO> agendar(@RequestBody AgendamentoRequestDTO dto) {
        // Corrigido: chama .agendar() da service e passa o DTO de agendamento
        AgendamentoResponseDTO salvo = service.agendar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

}
