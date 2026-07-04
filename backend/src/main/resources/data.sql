-- 1. Inserindo Especialidades
INSERT INTO especialidades (nome, descricao) VALUES ('Cardiologia', 'Cuida da saúde do coração e do sistema circulatório.');
INSERT INTO especialidades (nome, descricao) VALUES ('Pediatria', 'Especialidade dedicada à assistência a crianças e adolescentes.');
INSERT INTO especialidades (nome, descricao) VALUES ('Dermatologia', 'Diagnóstico e tratamento de doenças da pele, cabelos e unhas.');


-- 2. Inserindo Médicos (Com a nova coluna CPF herdada de Pessoa)
INSERT INTO medicos (nome, crm, email, cpf, especialidade_id) VALUES ('Dr. Carlos Eduardo', 'CRM/SP 123456', 'carlos.eduardo@clinica.com', '111.222.333-44', 1);
INSERT INTO medicos (nome, crm, email, cpf, especialidade_id) VALUES ('Dra. Ana Beatriz', 'CRM/SP 654321', 'ana.beatriz@clinica.com', '555.666.777-88', 2);
INSERT INTO medicos (nome, crm, email, cpf, especialidade_id) VALUES ('Dr. Roberto Mendes', 'CRM/SP 789101', 'roberto.mendes@clinica.com', '999.888.777-66', 3);
-- 3. Inserindo Pacientes
INSERT INTO pacientes (nome, cpf, email, telefone) VALUES ('João Silva', '123.456.789-00', 'joao.silva@email.com', '(11) 99999-1111');
INSERT INTO pacientes (nome, cpf, email, telefone) VALUES ('Maria Souza', '987.654.321-11', 'maria.souza@email.com', '(11) 98888-2222');
-- 4. Inserindo Agendamentos Iniciais (Opcional - Ajuste as datas conforme necessário)
INSERT INTO agendamento (paciente_id, medico_id, data_hora, java_observacoes) VALUES (1, 1, '2026-08-10 14:00:00', 'Consulta de rotina pós-exames.');
INSERT INTO agendamento (paciente_id, medico_id, data_hora, java_observacoes) VALUES (2, 2, '2026-08-11 10:30:00', 'Retorno de pediatria.');