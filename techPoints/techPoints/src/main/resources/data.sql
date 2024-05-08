CREATE TABLE IF NOT EXISTS categoria_curso (
  id_categoria_curso INT AUTO_INCREMENT PRIMARY KEY,
  nome VARCHAR(45) NOT NULL
);

CREATE TABLE IF NOT EXISTS curso (
  id_curso INT AUTO_INCREMENT PRIMARY KEY,
  nome VARCHAR(45) NOT NULL,
  qtd_horas INT NOT NULL,
  fk_categoria_curso INT NOT NULL,
  CONSTRAINT fk_curso_categoria_curso1 FOREIGN KEY (fk_categoria_curso) REFERENCES categoria_curso (id_categoria_curso)
);

 CREATE TABLE IF NOT EXISTS atividade (
   id_atividade INT AUTO_INCREMENT NOT NULL,
   nota INT NOT NULL,
   temp_duracao TIME NOT NULL,
   PRIMARY KEY (id_atividade)
   );

 CREATE TABLE IF NOT EXISTS modulo (
   id_modulo INT AUTO_INCREMENT PRIMARY KEY,
   fk_curso INT NOT NULL,
   fk_atividade INT NOT NULL,
   qtd_atividade_feita INT NOT NULL,
   qtd_atividade_total INT NOT NULL,
   nome_modulo VARCHAR(45),
   CONSTRAINT fk_modulo_curso FOREIGN KEY (fk_curso) REFERENCES curso (id_curso),
   CONSTRAINT fk_modulo_atividade FOREIGN KEY (fk_atividade) REFERENCES atividade (id_atividade)
 );

INSERT INTO tipo_ponto (nome, fk_curso, valido) VALUES
('Presença', 1, true),
('Avaliação', 2, true),
('Participação', 3, true),
('Entrega de Tarefa', 4, true);

INSERT INTO categoria_curso (nome) VALUES
('Tecnologia da Informação'),
('Design Gráfico'),
('Marketing Digital'),
('Programação');

INSERT INTO curso (nome, qtd_horas, fk_categoria_curso) VALUES
('Desenvolvimento Web', 80, 1),
('Photoshop Avançado', 40, 2),
('Marketing de Conteúdo', 60, 3),
('Python para Iniciantes', 40, 1),
('UI/UX Design Essentials', 30, 2);

INSERT INTO ponto (qtd_ponto, fk_tipo_ponto) VALUES
(10, 1),
(20, 2),
(15, 3),
(15, 3),
(25, 2);

INSERT INTO categoria_produto (nome) VALUES
('Eletrônicos'),
('Livros'),
('Roupas'),
('Alimentos'),
('Brinquedos');

INSERT INTO produto (nome, valor_pontos, descricao, quantidade, disponivel, categoria_produto_id) VALUES
('Smartphone', 500.00, 'Modelo X', 10, 1, 1),
('A Guerra dos Tronos', 150.00, 'Livro da série', 20, 1, 2),
('Camiseta Preta', 80.00, 'Tamanho M', 30, 1, 3),
('Fone de Ouvido Bluetooth', 200.00, 'Modelo Y', 15, 1, 1),
('Cesta de Café da Manhã', 100.00, 'Variada', 10, 1, 2),
('Boneca de Pelúcia', 50.00, 'Tamanho Grande', 20, 1, 3);

INSERT INTO atividade (nota, temp_duracao) VALUES
(80, '02:30:00'),
(90, '01:45:00'),
(75, '03:00:00'),
(70, '02:15:00'),
(85, '01:50:00');

INSERT INTO modulo (fk_curso, fk_atividade, qtd_atividade_feita, qtd_atividade_total, nome_modulo) VALUES
(1, 1, 1, 2, 'Introdução à Linguagem Python'),
(1, 2, 0, 1, 'Princípios de Design de Interface'),
(3, 3, 1, 1, 'Estratégias de Marketing de Conteúdo'),
(4, 4, 1, 2, 'Lógica de Programação'),
(5, 5, 0, 1, 'Design Responsivo');

INSERT INTO pontuacao (fk_pontos, fk_tipo_ponto, fk_usuario, total_pontos_usuario, data_atualizacao)
VALUES
(1, 1, 1, '100', '2024-04-10 08:00:00'),
(2, 2, 2, '150', '2024-04-15 10:30:00'),
(3, 3, 3, '200', '2024-04-20 14:45:00'),
(4, 1, 4, '180', '2024-04-25 09:15:00');
