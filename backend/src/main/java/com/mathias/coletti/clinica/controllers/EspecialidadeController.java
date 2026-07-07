package com.mathias.coletti.clinica.controllers;

import com.mathias.coletti.clinica.dtos.EspecialidadeRequestDTO;
import com.mathias.coletti.clinica.models.EspecialidadeModel;
import com.mathias.coletti.clinica.services.EspecialidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/especialidades")
@CrossOrigin(origins = "*")
public class EspecialidadeController {

    @Autowired
    private EspecialidadeService service;

    @GetMapping
    public ResponseEntity<List<EspecialidadeModel>> listarTodos() { return ResponseEntity.ok(service.listarTodos()); }

    @GetMapping("/{id}")
    public ResponseEntity<EspecialidadeModel> buscarPorId(@PathVariable Long id) { return ResponseEntity.ok(service.buscarPorId(id)); }

    @PostMapping
    public ResponseEntity<EspecialidadeModel> salvar(@RequestBody EspecialidadeRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EspecialidadeModel> atualizar(@PathVariable Long id, @RequestBody EspecialidadeRequestDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (service.buscarPorId(id).isPresent()) { // Verifique se existe
            return ResponseEntity.notFound().build();
        }
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}