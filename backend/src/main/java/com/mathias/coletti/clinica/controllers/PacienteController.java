package com.mathias.coletti.clinica.controllers;

import com.mathias.coletti.clinica.dtos.PacienteRequestDTO;
import com.mathias.coletti.clinica.dtos.PacienteResponseDTO;
import com.mathias.coletti.clinica.services.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pacientes")
@CrossOrigin(origins = "*") // Permite a comunicação direta com o seu front-end em Angular
public class PacienteController {

    @Autowired
    private PacienteService service;

    // GET: Listar todos os pacientes formatados em DTO
    @GetMapping
    public ResponseEntity<List<PacienteResponseDTO>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    // GET: Buscar um paciente específico por ID (retornando o DTO seguro)
    @GetMapping("/{id}")
    public ResponseEntity<PacienteResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorIdDto(id));
    }

    // POST: Cadastrar um novo paciente (Retorna HTTP 201 Created)
    @PostMapping
    public ResponseEntity<PacienteResponseDTO> salvar(@RequestBody PacienteRequestDTO dto) {
        PacienteResponseDTO salvo = service.salvar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    // PUT: Atualizar os dados de um paciente existente (Retorna HTTP 200 OK)
    @PutMapping("/{id}")
    public ResponseEntity<PacienteResponseDTO> atualizar(@PathVariable Long id, @RequestBody PacienteRequestDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    // DELETE: Remover um paciente do banco de dados (Retorna HTTP 204 No Content)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}