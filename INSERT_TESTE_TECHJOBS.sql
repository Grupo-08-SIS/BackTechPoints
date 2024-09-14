INSERT INTO `mydb`.`usuario` (`autenticado`, `deletado`, `data_atualizacao`, `data_criacao`, `data_deletado`, `cpf`, `email`, `nome_usuario`, `primeiro_nome`, `senha`, `sobrenome`, `telefone`, `imagem_perfil`, `tipo_usuario`)
VALUES (b'1', b'0', NOW(), NOW(), NULL, '12345678901', 'usuario1@example.com', 'usuario1', 'João', 'senha1', 'Silva', '11987654321', "https://images.pexels.com/photos/1310522/pexels-photo-1310522.jpeg?cs=srgb&dl=pexels-george-dolgikh-551816-1310522.jpg&fm=jpg", 1);

INSERT INTO `mydb`.`usuario` (`autenticado`, `deletado`, `data_atualizacao`, `data_criacao`, `data_deletado`, `cpf`, `email`, `nome_usuario`, `primeiro_nome`, `senha`, `sobrenome`, `telefone`, `imagem_perfil`, `tipo_usuario`)
VALUES (b'0', b'0', NOW(), NOW(), NULL, '23456789012', 'usuario2@example.com', 'usuario2', 'Maria', 'senha2', 'Souza', '11912345678', "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ_HFQbK0SjP6lVSn7FUknx5MVcXFb5GOW0sA&s", 1);

INSERT INTO `mydb`.`endereco` (`data_atualizacao`, `data_criacao`, `cep`, `cidade`, `estado`, `rua`, `numero`)
VALUES (NOW(), NOW(), '12345-678', 'São Paulo', 'SP', 'Rua Exemplo', '100');

INSERT INTO `mydb`.`endereco` (`data_atualizacao`, `data_criacao`, `cep`, `cidade`, `estado`, `rua`, `numero`)
VALUES (NOW(), NOW(), '87654-321', 'Rio de Janeiro', 'RJ', 'Avenida Teste', '200');

INSERT INTO `mydb`.`aluno` (`dt_nasc`, `endereco_id`, `id`, `descricao`, `escolaridade`)
VALUES ('1990-01-01', 1, 1, 'Aluno do ensino médio', 'Ensino Médio Completo');

INSERT INTO `mydb`.`aluno` (`dt_nasc`, `endereco_id`, `id`, `descricao`, `escolaridade`)
VALUES ('1995-05-15', 2, 2, 'Aluno de graduação', 'Ensino Superior Incompleto');

INSERT INTO `mydb`.`curso` (`total_atividades`, `total_atividades_do_aluno`, `nome`)
VALUES (10, 5, 'Curso de Programação');

INSERT INTO `mydb`.`curso` (`total_atividades`, `total_atividades_do_aluno`, `nome`)
VALUES (8, 3, 'Curso de Matemática');

INSERT INTO `mydb`.`aluno_curso` (`aluno_id`, `curso_id`)
VALUES (1, 1);

INSERT INTO `mydb`.`aluno_curso` (`aluno_id`, `curso_id`)
VALUES (2, 2);

INSERT INTO `mydb`.`dados_empresa` (`data_atualizacao`, `data_criacao`, `endereco_id`, `telefone_contato`, `email_corporativo`, `cnpj`, `nome_empresa`, `setor_industria`)
VALUES (NOW(), NOW(), 1, '11943005811', 'email@teste.com', '12345678000101', 'Empresa Exemplo', 'Tecnologia');

INSERT INTO `mydb`.`dados_empresa` (`data_atualizacao`, `data_criacao`, `endereco_id`, `telefone_contato`, `email_corporativo`, `cnpj`, `nome_empresa`, `setor_industria`)
VALUES (NOW(), NOW(), 2, '11943005811', 'email@teste2.com', '23456789000102', 'Empresa Teste', 'Educação');

INSERT INTO `mydb`.`meta_estudo_semana` (`horas_total`, `aluno_id`)
VALUES (10, 1);

INSERT INTO `mydb`.`meta_estudo_semana` (`horas_total`, `aluno_id`)
VALUES (15, 2);

INSERT INTO `mydb`.`pontuacao` (`nota_aluno`, `nota_atividade`, `aluno_id`, `curso_id`, `data_entrega`, `nome_atividade`)
VALUES (9.5, 10, 1, 1, '2024-09-07', 'Atividade 1');

INSERT INTO `mydb`.`pontuacao` (`nota_aluno`, `nota_atividade`, `aluno_id`, `curso_id`, `data_entrega`, `nome_atividade`)
VALUES (8.0, 10, 2, 2, '2024-09-07', 'Atividade 2');

INSERT INTO `mydb`.`recrutador` (`empresa_id`, `id`, `cargo_usuario`, `favoritos_json`, `interessados_json`)
VALUES (1, 1, 'Gerente de RH', NULL, NULL);

INSERT INTO `mydb`.`recrutador` (`empresa_id`, `id`, `cargo_usuario`, `favoritos_json`, `interessados_json`)
VALUES (2, 2, 'Coordenador de Seleção', NULL, NULL);

INSERT INTO `mydb`.`tempo_estudo` (`ativado`, `meta_atingida`, `meta_estudo_semana_id`, `nome_dia`, `qtd_tempo_estudo`)
VALUES (b'1', b'0', 1, 'Segunda-feira', '2h');

INSERT INTO `mydb`.`tempo_estudo` (`ativado`, `meta_atingida`, `meta_estudo_semana_id`, `nome_dia`, `qtd_tempo_estudo`)
VALUES (b'1', b'1', 2, 'Terça-feira', '3h');

INSERT INTO `mydb`.`tempo_sessao` (`qtd_tempo_sessao`, `aluno_id`, `meta_estudo_semana_id`, `dia_sessao`)
VALUES (2.5, 1, 1, '2024-09-07');

INSERT INTO `mydb`.`tempo_sessao` (`qtd_tempo_sessao`, `aluno_id`, `meta_estudo_semana_id`, `dia_sessao`)
VALUES (3.0, 2, 2, '2024-09-07');

INSERT INTO `mydb`.`curso` (`total_atividades`, `total_atividades_do_aluno`, `nome`)
VALUES (10, 5, 'Introdução à Programação');

INSERT INTO `mydb`.`curso` (`total_atividades`, `total_atividades_do_aluno`, `nome`)
VALUES (8, 4, 'Banco de Dados Relacional');

INSERT INTO `mydb`.`aluno_curso` (`aluno_id`, `curso_id`)
VALUES (1, 2); -- Supondo que o curso com id 2 já exista

INSERT INTO `mydb`.`aluno_curso` (`aluno_id`, `curso_id`)
VALUES (1, 3); -- Supondo que o curso com id 3 já exista

INSERT INTO `mydb`.`pontuacao` (`nota_aluno`, `nota_atividade`, `aluno_id`, `curso_id`, `data_entrega`, `nome_atividade`)
VALUES (9.0, 10.0, 1, 2, '2024-09-05', 'Atividade 1 - Introdução à Programação');

INSERT INTO `mydb`.`pontuacao` (`nota_aluno`, `nota_atividade`, `aluno_id`, `curso_id`, `data_entrega`, `nome_atividade`)
VALUES (8.5, 9.0, 1, 2, '2024-09-10', 'Atividade 2 - Introdução à Programação');

INSERT INTO `mydb`.`pontuacao` (`nota_aluno`, `nota_atividade`, `aluno_id`, `curso_id`, `data_entrega`, `nome_atividade`)
VALUES (7.5, 8.0, 1, 3, '2024-09-12', 'Atividade 1 - Banco de Dados Relacional');

INSERT INTO `mydb`.`pontuacao` (`nota_aluno`, `nota_atividade`, `aluno_id`, `curso_id`, `data_entrega`, `nome_atividade`)
VALUES (9.2, 9.5, 1, 3, '2024-09-18', 'Atividade 2 - Banco de Dados Relacional');

INSERT INTO `mydb`.`pontuacao` (`nota_aluno`, `nota_atividade`, `aluno_id`, `curso_id`, `data_entrega`, `nome_atividade`)
VALUES (8.8, 9.0, 1, 2, '2024-09-20', 'Atividade 3 - Introdução à Programação');


-- Adicionando mais cursos
INSERT INTO `mydb`.`curso` (`total_atividades`, `total_atividades_do_aluno`, `nome`)
VALUES (12, 6, 'Curso de Física');

INSERT INTO `mydb`.`curso` (`total_atividades`, `total_atividades_do_aluno`, `nome`)
VALUES (15, 7, 'Curso de Química');

-- Associando mais cursos aos alunos
INSERT INTO `mydb`.`aluno_curso` (`aluno_id`, `curso_id`)
VALUES (2, 3);

INSERT INTO `mydb`.`aluno_curso` (`aluno_id`, `curso_id`)
VALUES (1, 4);

-- Adicionando mais atividades e pontuações
INSERT INTO `mydb`.`pontuacao` (`nota_aluno`, `nota_atividade`, `aluno_id`, `curso_id`, `data_entrega`, `nome_atividade`)
VALUES (9.0, 10.0, 1, 4, '2024-09-15', 'Atividade 1 - Introdução à Física');

INSERT INTO `mydb`.`pontuacao` (`nota_aluno`, `nota_atividade`, `aluno_id`, `curso_id`, `data_entrega`, `nome_atividade`)
VALUES (8.0, 10.0, 1, 4, '2024-09-22', 'Atividade 2 - Introdução à Física');

INSERT INTO `mydb`.`pontuacao` (`nota_aluno`, `nota_atividade`, `aluno_id`, `curso_id`, `data_entrega`, `nome_atividade`)
VALUES (9.5, 10.0, 2, 3, '2024-09-13', 'Atividade 1 - Química Orgânica');

INSERT INTO `mydb`.`pontuacao` (`nota_aluno`, `nota_atividade`, `aluno_id`, `curso_id`, `data_entrega`, `nome_atividade`)
VALUES (8.7, 9.0, 2, 3, '2024-09-19', 'Atividade 2 - Química Orgânica');

-- Adicionando mais atividades para o curso de Matemática
INSERT INTO `mydb`.`pontuacao` (`nota_aluno`, `nota_atividade`, `aluno_id`, `curso_id`, `data_entrega`, `nome_atividade`)
VALUES (8.0, 9.0, 1, 2, '2024-09-12', 'Atividade 4 - Álgebra Linear');

INSERT INTO `mydb`.`pontuacao` (`nota_aluno`, `nota_atividade`, `aluno_id`, `curso_id`, `data_entrega`, `nome_atividade`)
VALUES (7.5, 8.0, 1, 2, '2024-09-18', 'Atividade 5 - Cálculo Diferencial');

-- Adicionando mais atividades para o curso de Programação
INSERT INTO `mydb`.`pontuacao` (`nota_aluno`, `nota_atividade`, `aluno_id`, `curso_id`, `data_entrega`, `nome_atividade`)
VALUES (9.0, 10.0, 1, 1, '2024-09-10', 'Atividade 2 - Estruturas de Dados');

INSERT INTO `mydb`.`pontuacao` (`nota_aluno`, `nota_atividade`, `aluno_id`, `curso_id`, `data_entrega`, `nome_atividade`)
VALUES (8.5, 9.0, 1, 1, '2024-09-20', 'Atividade 3 - Algoritmos');

-- Adicionando mais atividades para o curso de Banco de Dados Relacional
INSERT INTO `mydb`.`pontuacao` (`nota_aluno`, `nota_atividade`, `aluno_id`, `curso_id`, `data_entrega`, `nome_atividade`)
VALUES (9.2, 10.0, 1, 3, '2024-09-15', 'Atividade 3 - Modelagem de Dados');

INSERT INTO `mydb`.`pontuacao` (`nota_aluno`, `nota_atividade`, `aluno_id`, `curso_id`, `data_entrega`, `nome_atividade`)
VALUES (8.8, 8.5, 1, 3, '2024-09-25', 'Atividade 4 - Normalização');

