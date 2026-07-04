package com.mathias.coletti.clinica.services;

import com.mathias.coletti.clinica.dtos.PacienteRequestDTO;
import com.mathias.coletti.clinica.dtos.PacienteResponseDTO;
import com.mathias.coletti.clinica.models.PacienteModel;
import com.mathias.coletti.clinica.repositories.PacienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PacienteServiceTest {

    @Mock
    private PacienteRepository repository;

    @InjectMocks
    private PacienteService service;

    private PacienteModel paciente;
    private PacienteRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        paciente = new PacienteModel();
        paciente.setId(1L);
        paciente.setNome("Mathias Coletti");
        paciente.setCpf("123.456.789-00");
        paciente.setEmail("mathias@email.com");
        paciente.setTelefone("51999999999");

        requestDTO = new PacienteRequestDTO("Mathias Coletti", "123.456.789-00", "mathias@email.com", "51999999999");
    }

    @Test
    @DisplayName("Deve salvar um paciente com sucesso quando o CPF não estiver duplicado")
    void salvarComSucesso() {
        // Arrange: O CPF não existe no banco, e o save retorna o paciente populado
        when(repository.existsByCpf(requestDTO.cpf())).thenReturn(false);
        when(repository.save(any(PacienteModel.class))).thenReturn(paciente);

        // Act: Executa o método da service
        PacienteResponseDTO resultado = service.salvar(requestDTO);

        // Assert: Valida se mapeou os campos corretamente pro DTO de resposta
        assertNotNull(resultado);
        assertEquals("Mathias Coletti", resultado.nome());
        assertEquals("123.456.789-00", resultado.cpf());
        verify(repository, times(1)).save(any(PacienteModel.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar cadastrar paciente com CPF já existente")
    void salvarComCpfDuplicadoLançaExcecao() {
        // Arrange: Força o repositório a dizer que o CPF já existe
        when(repository.existsByCpf(requestDTO.cpf())).thenReturn(true);

        // Act & Assert: Garante que a RuntimeException é disparada
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            service.salvar(requestDTO);
        });

        assertEquals("Já existe um paciente cadastrado com este CPF.", exception.getMessage());
        verify(repository, never()).save(any(PacienteModel.class)); // Garante que o banco NUNCA foi acionado para salvar
    }
}