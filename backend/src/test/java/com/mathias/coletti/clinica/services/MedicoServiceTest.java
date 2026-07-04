package com.mathias.coletti.clinica.services;

import com.mathias.coletti.clinica.models.MedicoModel;
import com.mathias.coletti.clinica.repositories.MedicoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MedicoServiceTest {

    @Mock
    private MedicoRepository repository;

    @InjectMocks
    private MedicoService service;

    private MedicoModel medico;

    @BeforeEach
    void setUp() {
        medico = new MedicoModel();
        medico.setId(1L);
        medico.setNome("Dr. House");
        medico.setCrm("123456/RS");
    }

    @Test
    @DisplayName("Deve retornar um Médico Model válido ao buscar por um ID existente")
    void buscarPorIdComSucesso() {
        when(repository.findById(1L)).thenReturn(Optional.of(medico));

        MedicoModel resultado = service.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Dr. House", resultado.getNome());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Deve falhar e acusar erro quando o médico procurado não existir")
    void buscarPorIdInexistenteLançaExcecao() {
        when(repository.findById(404L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> service.buscarPorId(404L));
        verify(repository, times(1)).findById(404L);
    }
}