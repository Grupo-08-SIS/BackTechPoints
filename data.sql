-- MySQL Workbench Forward Engineering

drop database mydb;
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`tipo_acao`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS tipo_acao (
  id_tipoAcao INT NOT NULL,
  Inserir TINYINT NULL,
  Deletar TINYINT NULL,
  Atualizar TINYINT NULL,
  PRIMARY KEY (id_tipoAcao)
);


-- -----------------------------------------------------
-- Table `mydb`.`tipo_usuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS tipo_usuario (
  id_tipo_usuario INT NOT NULL,
  nome VARCHAR(45) NULL,
  fk_tipoAcao INT NULL,
  PRIMARY KEY (id_tipo_usuario),
  CONSTRAINT fk_tipoAcao FOREIGN KEY (fk_tipoAcao) REFERENCES tipo_acao (id_tipoAcao)
);


-- -----------------------------------------------------
-- Table `mydb`.`endereco`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS endereco (
  id_endereco INT AUTO_INCREMENT PRIMARY KEY,
  estado CHAR(2) NOT NULL,
  cidade VARCHAR(100) NOT NULL,
  CEP CHAR(9) NOT NULL,
  rua VARCHAR(100) NOT NULL,
  data_atualizacao DATETIME NULL
);


-- -----------------------------------------------------
-- Table `mydb`.`usuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `usuario` (
  id_usuario INT AUTO_INCREMENT PRIMARY KEY,
  nome_usuario VARCHAR(100) NOT NULL,
  CPF CHAR(11) NOT NULL,
  senha VARCHAR(200) NOT NULL,
  primeiro_nome VARCHAR(100) NULL,
  sobrenome VARCHAR(100) NULL,
  email VARCHAR(45) NOT NULL,
  imagem_perfil BLOB NULL,
  data_criacao DATETIME NOT NULL,
  deletado TINYINT NOT NULL,
  data_deletado DATETIME NULL,
  data_atualizacao DATETIME NULL,
  autenticado TINYINT,
  fk_endereco INT NULL,
  fk_tipo_usuario INT not null,
  UNIQUE INDEX uk_nome_usuario (nome_usuario),
  UNIQUE INDEX uk_email (email),
  CONSTRAINT fk_endereco FOREIGN KEY (fk_endereco) REFERENCES endereco (id_endereco),
  CONSTRAINT fk_tipo_usuario FOREIGN KEY (fk_tipo_usuario) REFERENCES tipo_usuario (id_tipo_usuario)
);


-- -----------------------------------------------------
-- Table `mydb`.`referencia_gerar_pontuacao`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS referencia_gerar_pontuacao (
  id_referencia_gerar_pontuacao INT NOT NULL AUTO_INCREMENT,
  nome_da_geracao VARCHAR(45) NULL,
  descricao_da_geracao VARCHAR(45) NULL,
  PRIMARY KEY (id_referencia_gerar_pontuacao)
);


-- -----------------------------------------------------
-- Table `mydb`.`categoria_curso`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS categoria_curso (
  id_categoria_curso INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  nome VARCHAR(45) NULL
  );

-- -----------------------------------------------------
-- Table `mydb`.`curso`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS curso (
  id_curso INT PRIMARY KEY AUTO_INCREMENT,
  nome VARCHAR(45) NULL,
  qtd_horas INT NULL,
  fk_categoria_curso INT NOT NULL,
    FOREIGN KEY (fk_categoria_curso)
    REFERENCES `mydb`.`categoria_curso` (`id_categoria_curso`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `mydb`.`modulo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS modulo (
  id_modulo INT NOT NULL,
  fk_curso INT NOT NULL,
  qtd_atividade_total INT NOT NULL,
  nome_modulo VARCHAR(45) NULL,
  PRIMARY KEY (id_modulo, fk_curso),
  FOREIGN KEY (fk_curso)
    REFERENCES curso (id_curso)
    ON DELETE CASCADE
    ON UPDATE NO ACTION
)
ENGINE = InnoDB;


ALTER TABLE `mydb`.`modulo`
ADD UNIQUE INDEX `idx_modulo_fk_curso` (`id_modulo`, `fk_curso`);

-- -----------------------------------------------------
-- Table `mydb`.`atividade`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`atividade` (
  id_atividade INT,
  nome VARCHAR(45),
  descricao VARCHAR(200),
  valor INT NULL,
  peso DOUBLE,
  temp_duracao TIME NULL,
  data_entrega DATE NULL,
  fk_modulo INT NOT NULL,
  fk_curso INT NOT NULL,
  primary key (`id_atividade`, `fk_modulo` , `fk_curso`),
    FOREIGN KEY (`fk_modulo` , `fk_curso`)
    REFERENCES `mydb`.`modulo` (`id_modulo` , `fk_curso`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

ALTER TABLE `mydb`.`atividade`
ADD INDEX `idx_atividade_fk_modulo_fk_curso` (`id_atividade`, `fk_modulo`, `fk_curso`);

-- -----------------------------------------------------
-- Table `mydb`.`ponto`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`ponto` (
  id_ponto INT,
  nota DOUBLE,
  qtd_ponto INT NOT NULL,
  data_entrega DATE NOT NULL,
  fk_referencia_gerar_pontuacao INT NOT NULL,
  fk_atividade INT NOT NULL,
  fk_modulo INT NOT NULL,
  fk_curso INT NOT NULL,
  fk_usuario INT NOT NULL,
  primary key (`id_ponto`, `fk_atividade` , `fk_modulo` , `fk_curso`, `fk_usuario`),
    FOREIGN KEY (`fk_referencia_gerar_pontuacao`)
    REFERENCES `mydb`.`referencia_gerar_pontuacao` (`id_referencia_gerar_pontuacao`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    FOREIGN KEY (`fk_atividade` , `fk_modulo` , `fk_curso`)
    REFERENCES `mydb`.`atividade` (`id_atividade` , `fk_modulo` , `fk_curso`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    FOREIGN KEY (`fk_usuario`)
    REFERENCES `mydb`.`usuario` (`id_usuario`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `mydb`.`redefinicao_senha`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS redefinicao_senha (
  id_redefinicao_senha INT AUTO_INCREMENT PRIMARY KEY,
  codigo_redefinicao VARCHAR(8) NULL,
  data_criacao DATETIME NULL,
  data_expiracao DATETIME NOT NULL,
  valido TINYINT NOT NULL,
  email_redefinicao VARCHAR(45) NOT NULL,
  fk_usuario INT NOT NULL,
  CONSTRAINT fk_usuario FOREIGN KEY (fk_usuario) REFERENCES usuario (id_usuario) ON DELETE CASCADE
);


-- -----------------------------------------------------
-- Table `mydb`.`inscricao`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`inscricao` (
  fk_usuario INT NOT NULL AUTO_INCREMENT,
  fk_curso INT NOT NULL,
  codigo_inscricao VARCHAR(100) NULL,
    FOREIGN KEY (`fk_usuario`)
    REFERENCES `mydb`.`usuario` (`id_usuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    FOREIGN KEY (`fk_curso`)
    REFERENCES `mydb`.`curso` (`id_curso`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

-----------------------------------------------------
-- Table `mydb`.`dados_empresa`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`dados_empresa` (
  `id_empresa` INT AUTO_INCREMENT PRIMARY KEY,
  `nome_empresa` VARCHAR(45) NULL,
  `setor_industria` VARCHAR(45) NULL,
  `cargo_usuario` VARCHAR(45) NULL,
   cnpj VARCHAR(14) not null,
   data_atualizacao DATETIME NULL,
  `e-mail_corporativo` VARCHAR(45) NULL,
  `telefone_contato_corporativo` CHAR(11) NULL,
  `fk_usuario` INT NOT NULL,
  INDEX `fk_dados_empresa_usuario1_idx` (`fk_usuario` ASC) VISIBLE,
  CONSTRAINT `fk_dados_empresa_usuario1`
    FOREIGN KEY (`fk_usuario`)
    REFERENCES `mydb`.`usuario` (`id_usuario`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`tempo_estudo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`tempo_estudo` (
  `id_tempo_estudo` INT NOT NULL AUTO_INCREMENT,
  `nome_dia` VARCHAR(45) NULL,
  `qtd_tempo_estudo` VARCHAR(5) NULL,
  `ativado` TINYINT NULL,
  `meta_alcancada` TINYINT NULL,
  PRIMARY KEY (`id_tempo_estudo`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`tempo_sessao`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`tempo_sessao` (
  `id_tempo_sessao` INT NOT NULL AUTO_INCREMENT,
  `dia_sessao` VARCHAR(45) NULL,
  `qtd_tempo_sessao` VARCHAR(5) NULL,
  `fk_usuario` INT NOT NULL,
  PRIMARY KEY (`id_tempo_sessao`),
  INDEX `fk_tempo_sessao_usuario1_idx` (`fk_usuario` ASC) VISIBLE,
  CONSTRAINT `fk_tempo_sessao_usuario1`
    FOREIGN KEY (`fk_usuario`)
    REFERENCES `mydb`.`usuario` (`id_usuario`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`meta_estudo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`meta_estudo` (
  `fk_tempo_estudo` INT NOT NULL AUTO_INCREMENT,
  `fk_tempo_sessao` INT NOT NULL,
  `tempo_ate_completar` VARCHAR(5) NULL,
  `tempo_total_estimado` VARCHAR(5) NULL,
  INDEX `fk_usuario_has_tempo_estudo_tempo_estudo1_idx` (`fk_tempo_estudo` ASC) VISIBLE,
  INDEX `fk_meta_estudo_tempo_sessao1_idx` (`fk_tempo_sessao` ASC) VISIBLE,
  CONSTRAINT `fk_usuario_has_tempo_estudo_tempo_estudo1`
    FOREIGN KEY (`fk_tempo_estudo`)
    REFERENCES `mydb`.`tempo_estudo` (`id_tempo_estudo`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_meta_estudo_tempo_sessao1`
    FOREIGN KEY (`fk_tempo_sessao`)
    REFERENCES `mydb`.`tempo_sessao` (`id_tempo_sessao`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

ALTER TABLE `mydb`.`ponto` DROP FOREIGN KEY `ponto_ibfk_1`;
ALTER TABLE usuario DROP FOREIGN KEY fk_endereco;

ALTER TABLE usuario
ADD CONSTRAINT fk_endereco
FOREIGN KEY (fk_endereco) REFERENCES endereco(id_endereco)
ON DELETE CASCADE;

ALTER TABLE redefinicao_senha DROP FOREIGN KEY fk_usuario;

ALTER TABLE redefinicao_senha
ADD CONSTRAINT fk_usuario
FOREIGN KEY (fk_usuario) REFERENCES usuario(id_usuario)
ON DELETE CASCADE;

ALTER TABLE dados_empresa
DROP FOREIGN KEY fk_dados_empresa_usuario1;

ALTER TABLE dados_empresa
ADD CONSTRAINT fk_dados_empresa_usuario1
FOREIGN KEY (fk_usuario) REFERENCES usuario(id_usuario)
ON DELETE CASCADE;

ALTER TABLE inscricao
DROP FOREIGN KEY inscricao_ibfk_1;

ALTER TABLE inscricao
ADD CONSTRAINT inscricao_ibfk_1
FOREIGN KEY (fk_usuario) REFERENCES usuario(id_usuario)
ON DELETE CASCADE;


-- Populando a tabela tipo_acao
INSERT INTO tipo_acao (id_tipoAcao, Inserir, Deletar, Atualizar) VALUES
(1, 1, 1, 1),
(2, 1, 0, 1),
(3, 0, 0, 1);

-- Populando a tabela tipo_usuario
INSERT INTO tipo_usuario (id_tipo_usuario, nome, fk_tipoAcao) VALUES
(1, 'ADM', 1),
(2, 'ALUNO', 2),
(3, 'RH', 3);

-- Populando a tabela endereco
INSERT INTO endereco (estado, cidade, CEP, rua, data_atualizacao) VALUES
('SP', 'São Paulo', '01000-000', 'Rua A', NOW()),
('RJ', 'Rio de Janeiro', '20000-000', 'Rua B', NOW()),
('MG', 'Belo Horizonte', '30000-000', 'Rua C', NOW());

-- Populando a tabela usuario
INSERT INTO usuario (nome_usuario, CPF, senha, primeiro_nome, sobrenome, email, data_criacao, deletado, autenticado, fk_endereco, fk_tipo_usuario) VALUES
('João Silva', '12345678901', 'senha1', 'João', 'Silva', 'joao.silva@example.com', NOW(), 0, 1, 1, 2),
('Maria', '98765432100', 'senha2', 'Maria', 'Souza', 'maria.souza@example.com', NOW(), 0, 1, 2, 2),
('Carlos', '11122233344', 'senha3', 'Carlos', 'Pereira', 'carlos.pereira@example.com', NOW(), 0, 1, 3, 3);

-- Populando a tabela referencia_gerar_pontuacao
INSERT INTO referencia_gerar_pontuacao (id_referencia_gerar_pontuacao, nome_da_geracao, descricao_da_geracao) VALUES
(1, 'Quiz', 'Pontuação por quiz concluído'),
(2, 'Projeto', 'Pontuação por projeto concluído'),
(3, 'Exercício', 'Pontuação por exercício concluído');


-- Populando a tabela dados_empresa
INSERT INTO dados_empresa (nome_empresa, setor_industria, cargo_usuario, cnpj, data_atualizacao, `e-mail_corporativo`, telefone_contato_corporativo, fk_usuario) VALUES
('Empresa A', 'Tecnologia', 'Desenvolvedor', '12345678000100', NOW(), 'joao@empresaA.com', '11987654321', 1),
('Empresa B', 'Gestão', 'Gerente', '98765432000111', NOW(), 'maria@empresaB.com', '21987654321', 2),
('Empresa C', 'Educação', 'Professor', '11223344000155', NOW(), 'carlos@empresaC.com', '31987654321', 3);


INSERT INTO categoria_curso (id_categoria_curso, nome) VALUE
(1, 'Tecnologia'),
(2, 'Gestão'),
(3, 'Idiomas');

-- Populando a tabela curso
INSERT INTO curso (nome, qtd_horas, fk_categoria_curso) VALUES
('Programação', 40, 1),
('Liderança', 20, 2),
('Inglês', 60, 3),
('Excel', 40, 1),
('Marketing Digital', 30, 2),
('Web Design', 50, 1),
('Finanças Pessoais', 25, 2),
('Gestão de Projetos', 35, 2);

INSERT INTO inscricao (fk_usuario, fk_curso, codigo_inscricao) VALUES
(1, 1, 'ABC123'),
(1, 2, 'DEF456'),
(1, 3, 'GHI789'),
(1, 4, 'JKL012'),
(2, 1, 'ABC123'),
(2, 2, 'DEF456'),
(2, 3, 'GHI789'),
(2, 4, 'JKL012'),
(3, 1, 'ABC123'),
(3, 2, 'DEF456'),
(3, 3, 'GHI789'),
(3, 4, 'JKL012');

-- Inserir dados em `modulo` curso 1
INSERT INTO modulo (id_modulo, fk_curso, qtd_atividade_total, nome_modulo) VALUES
(1, 1, 10, 'Módulo 1: Introdução'),
(2, 1, 10, 'Módulo 2: Fundamentos'),
(3, 1, 10, 'Módulo 3: Básico');

-- Inserir dados em `modulo` curso 2
INSERT INTO modulo (id_modulo, fk_curso, qtd_atividade_total, nome_modulo) VALUES
(1, 2, 5, 'Módulo 1: Introdução'),
(2, 2, 5, 'Módulo 2: Fundamentos'),
(3, 2, 5, 'Módulo 3: Básico');

-- Inserir dados em `modulo` curso 3
INSERT INTO modulo (id_modulo, fk_curso, qtd_atividade_total, nome_modulo) VALUES
(1, 3, 3, 'Módulo 1: Introdução'),
(2, 3, 3, 'Módulo 2: Fundamentos'),
(3, 3, 3, 'Módulo 3: Básico');

-- Inserir dados em `modulo` curso 4
INSERT INTO modulo (id_modulo, fk_curso, qtd_atividade_total, nome_modulo) VALUES
(1, 4, 3, 'Módulo 1: Introdução'),
(2, 4, 3, 'Módulo 2: Fundamentos'),
(3, 4, 3, 'Módulo 3: Básico');

-- Atividades para o Módulo 1 Curso 1
INSERT INTO atividade (id_atividade, fk_curso, fk_modulo, nome, descricao, peso, valor) VALUES
(1, 1, 1, 'Introdução à Programação', 'Exploração dos conceitos básicos de programação.', 1.0, 100),
(2, 1, 1, 'Estruturas de Controle', 'Estudo de condicionais e laços de repetição.', 1.5, 100),
(3, 1, 1, 'Funções e Procedimentos', 'Definição e uso de funções e procedimentos.', 2.0, 100),
(4, 1, 1, 'Manipulação de Strings', 'Trabalhando com strings em várias operações.', 2.5, 100),
(5, 1, 1, 'Listas e Tuplas', 'Compreensão e uso de listas e tuplas.', 1.0, 100),
(6, 1, 1, 'Dicionários e Conjuntos', 'Uso de dicionários e conjuntos para armazenamento de dados.', 1.5, 100),
(7, 1, 1, 'Tratamento de Exceções', 'Como lidar com erros e exceções no código.', 2.0, 100),
(8, 1, 1, 'Manipulação de Arquivos', 'Leitura e escrita de arquivos.', 2.5, 100),
(9, 1, 1, 'Recursão', 'Entendimento e implementação de algoritmos recursivos.', 1.0, 100),
(10, 1, 1, 'Bibliotecas e Módulos', 'Uso de bibliotecas e módulos externos.', 1.5, 100);

-- Atividades para o Módulo 2 Curso 1
INSERT INTO atividade (id_atividade, fk_curso, fk_modulo, nome, descricao, peso, valor) VALUES
(1, 1, 2, 'Fundamentos de Redes', 'Introdução aos conceitos básicos de redes de computadores.', 1.0, 100),
(2, 1, 2, 'Protocolos de Comunicação', 'Estudo dos principais protocolos de rede.', 1.5, 100),
(3, 1, 2, 'Topologias de Rede', 'Compreensão das diferentes topologias de rede.', 2.0, 100),
(4, 1, 2, 'Endereçamento IP', 'Aprendizado sobre endereçamento e sub-redes IP.', 2.5, 100),
(5, 1, 2, 'TCP/IP', 'Estudo detalhado do modelo TCP/IP.', 1.0, 100),
(6, 1, 2, 'DNS e DHCP', 'Funcionamento dos serviços DNS e DHCP.', 1.5, 100),
(7, 1, 2, 'Segurança em Redes', 'Princípios básicos de segurança em redes de computadores.', 2.0, 100),
(8, 1, 2, 'Redes sem Fio', 'Exploração das redes sem fio e suas tecnologias.', 2.5, 100),
(9, 1, 2, 'VPNs', 'Configuração e uso de redes privadas virtuais (VPNs).', 1.0, 100),
(10, 1, 2, 'Monitoramento de Redes', 'Ferramentas e técnicas para monitoramento de redes.', 1.5, 100);

-- Atividades para o Módulo 3 Curso 1
INSERT INTO atividade (id_atividade, fk_curso, fk_modulo, nome, descricao, peso, valor) VALUES
(1, 1, 3, 'Banco de Dados Relacional', 'Conceitos fundamentais de bancos de dados relacionais.', 1.0, 100),
(2, 1, 3, 'SQL Básico', 'Introdução à linguagem SQL e suas operações básicas.', 1.5, 100),
(3, 1, 3, 'Modelagem de Dados', 'Técnicas de modelagem de dados usando diagramas ER.', 2.0, 100),
(4, 1, 3, 'Normalização', 'Processo de normalização de banco de dados.', 2.5, 100),
(5, 1, 3, 'Joins e Subconsultas', 'Uso de joins e subconsultas em SQL.', 1.0, 100),
(6, 1, 3, 'Índices e Performance', 'Melhorias de performance através do uso de índices.', 1.5, 100),
(7, 1, 3, 'Procedures e Triggers', 'Implementação de procedures e triggers.', 2.0, 100),
(8, 1, 3, 'Transações e Concurrency', 'Gestão de transações e controle de concorrência.', 2.5, 100),
(9, 1, 3, 'Backup e Recuperação', 'Técnicas de backup e recuperação de dados.', 1.0, 100),
(10, 1, 3, 'NoSQL', 'Introdução aos bancos de dados NoSQL.', 1.5, 100);

-- Pontos para o Usuário 1 (Curso de Programação)
INSERT INTO ponto (id_ponto, nota, qtd_ponto, data_entrega, fk_referencia_gerar_pontuacao, fk_atividade, fk_modulo, fk_curso, fk_usuario) VALUES
(1, 85.0, 85, '2023-01-10', 1, 1, 1, 1, 1),
(2, 92.0, 138, '2023-01-15', 1, 2, 1, 1, 1),
(3, 88.0, 176, '2023-01-20', 1, 3, 1, 1, 1),
(4, 95.0, 238, '2023-01-25', 1, 4, 1, 1, 1),
(5, 80.0, 80, '2023-01-30', 1, 5, 1, 1, 1),
(6, 85.0, 127, '2023-02-05', 1, 6, 1, 1, 1),
(7, 90.0, 180, '2023-02-10', 1, 7, 1, 1, 1),
(8, 93.0, 233, '2023-02-15', 1, 8, 1, 1, 1),
(9, 77.0, 77, '2023-02-20', 1, 9, 1, 1, 1),
(10, 89.0, 133, '2023-02-25', 1, 10, 1, 1, 1),

(1, 84.0, 84, '2023-03-01', 1, 1, 2, 1, 1),
(2, 87.0, 131, '2023-03-05', 1, 2, 2, 1, 1),
(3, 91.0, 182, '2023-03-10', 1, 3, 2, 1, 1),
(4, 92.0, 230, '2023-03-15', 1, 4, 2, 1, 1),
(5, 80.0, 80, '2023-03-20', 1, 5, 2, 1, 1),
(6, 85.0, 127, '2023-03-25', 1, 6, 2, 1, 1),
(7, 89.0, 178, '2023-03-30', 1, 7, 2, 1, 1),
(8, 94.0, 235, '2023-04-05', 1, 8, 2, 1, 1),
(9, 81.0, 81, '2023-04-10', 1, 9, 2, 1, 1),
(10, 90.0, 135, '2023-04-15', 1, 10, 2, 1, 1),

(1, 88.0, 88, '2023-04-20', 1, 1, 3, 1, 1),
(2, 89.0, 134, '2023-04-25', 1, 2, 3, 1, 1),
(3, 92.0, 184, '2023-04-30', 1, 3, 3, 1, 1),
(4, 95.0, 238, '2023-05-05', 1, 4, 3, 1, 1),
(5, 83.0, 83, '2023-05-10', 1, 5, 3, 1, 1),
(6, 86.0, 129, '2023-05-15', 1, 6, 3, 1, 1),
(7, 90.0, 180, '2023-05-20', 1, 7, 3, 1, 1),
(8, 91.0, 228, '2023-05-25', 1, 8, 3, 1, 1),
(9, 79.0, 79, '2023-05-30', 1, 9, 3, 1, 1),
(10, 87.0, 130, '2023-06-05', 1, 10, 3, 1, 1);

-- Pontos para o Usuário 2 (Curso de Programação)
INSERT INTO ponto (id_ponto, nota, qtd_ponto, data_entrega, fk_referencia_gerar_pontuacao, fk_atividade, fk_modulo, fk_curso, fk_usuario) VALUES
(1, 77.0, 77, '2023-01-10', 1, 1, 1, 1, 2),
(2, 85.0, 127, '2023-01-15', 1, 2, 1, 1, 2),
(3, 81.0, 162, '2023-01-20', 1, 3, 1, 1, 2),
(4, 89.0, 223, '2023-01-25', 1, 4, 1, 1, 2),
(5, 74.0, 74, '2023-01-30', 1, 5, 1, 1, 2),
(6, 78.0, 117, '2023-02-05', 1, 6, 1, 1, 2),
(7, 84.0, 168, '2023-02-10', 1, 7, 1, 1, 2),
(8, 90.0, 225, '2023-02-15', 1, 8, 1, 1, 2),
(9, 72.0, 72, '2023-02-20', 1, 9, 1, 1, 2),
(10, 83.0, 125, '2023-02-25', 1, 10, 1, 1, 2),

(1, 80.0, 80, '2023-03-01', 1, 1, 2, 1, 2),
(2, 82.0, 123, '2023-03-05', 1, 2, 2, 1, 2),
(3, 88.0, 176, '2023-03-10', 1, 3, 2, 1, 2),
(4, 87.0, 218, '2023-03-15', 1, 4, 2, 1, 2),
(5, 73.0, 73, '2023-03-20', 1, 5, 2, 1, 2),
(6, 79.0, 118, '2023-03-25', 1, 6, 2, 1, 2),
(7, 85.0, 170, '2023-03-30', 1, 7, 2, 1, 2),
(8, 91.0, 228, '2023-04-05', 1, 8, 2, 1, 2),
(9, 78.0, 78, '2023-04-10', 1, 9, 2, 1, 2),
(10, 86.0, 129, '2023-04-15', 1, 10, 2, 1, 2),

(1, 83.0, 83, '2023-04-20', 1, 1, 3, 1, 2),
(2, 84.0, 126, '2023-04-25', 1, 2, 3, 1, 2),
(3, 89.0, 178, '2023-04-30', 1, 3, 3, 1, 2),
(4, 92.0, 230, '2023-05-05', 1, 4, 3, 1, 2),
(5, 80.0, 80, '2023-05-10', 1, 5, 3, 1, 2),
(6, 81.0, 122, '2023-05-15', 1, 6, 3, 1, 2),
(7, 87.0, 174, '2023-05-20', 1, 7, 3, 1, 2),
(8, 90.0, 230, '2023-05-25', 1, 8, 3, 1, 2),
(9, 76.0, 76, '2023-05-30', 1, 9, 3, 1, 2),
(10, 88.0, 132, '2023-06-05', 1, 10, 3, 1, 2);


-- Pontos para o Usuário 3 (Curso de Programação)
INSERT INTO ponto (id_ponto, nota, qtd_ponto, data_entrega, fk_referencia_gerar_pontuacao, fk_atividade, fk_modulo, fk_curso, fk_usuario) VALUES
(1, 85.0, 85, '2023-01-10', 1, 1, 1, 1, 3),
(2, 88.0, 133, '2023-01-15', 1, 2, 1, 1, 3),
(3, 82.0, 164, '2023-01-20', 1, 3, 1, 1, 3),
(4, 90.0, 225, '2023-01-25', 1, 4, 1, 1, 3),
(5, 75.0, 75, '2023-01-30', 1, 5, 1, 1, 3),
(6, 79.0, 118, '2023-02-05', 1, 6, 1, 1, 3),
(7, 83.0, 166, '2023-02-10', 1, 7, 1, 1, 3),
(8, 89.0, 267, '2023-02-15', 1, 8, 1, 1, 3),
(9, 71.0, 71, '2023-02-20', 1, 9, 1, 1, 3),
(10, 82.0, 123, '2023-02-25', 1, 10, 1, 1, 3),

(1, 79.0, 79, '2023-03-01', 1, 1, 2, 1, 3),
(2, 80.0, 120, '2023-03-05', 1, 2, 2, 1, 3),
(3, 85.0, 170, '2023-03-10', 1, 3, 2, 1, 3),
(4, 88.0, 220, '2023-03-15', 1, 4, 2, 1, 3),
(5, 76.0, 76, '2023-03-20', 1, 5, 2, 1, 3),
(6, 78.0, 117, '2023-03-25', 1, 6, 2, 1, 3),
(7, 81.0, 162, '2023-03-30', 1, 7, 2, 1, 3),
(8, 87.0, 261, '2023-04-05', 1, 8, 2, 1, 3),
(9, 70.0, 70, '2023-04-10', 1, 9, 2, 1, 3),
(10, 81.0, 121, '2023-04-15', 1, 10, 2, 1, 3),

(1, 82.0, 82, '2023-04-20', 1, 1, 3, 1, 3),
(2, 85.0, 128, '2023-04-25', 1, 2, 3, 1, 3),
(3, 90.0, 180, '2023-04-30', 1, 3, 3, 1, 3),
(4, 91.0, 227, '2023-05-05', 1, 4, 3, 1, 3),
(5, 77.0, 77, '2023-05-10', 1, 5, 3, 1, 3),
(6, 80.0, 120, '2023-05-15', 1, 6, 3, 1, 3),
(7, 84.0, 168, '2023-05-20', 1, 7, 3, 1, 3),
(8, 88.0, 222, '2023-05-25', 1, 8, 3, 1, 3),
(9, 73.0, 73, '2023-05-30', 1, 9, 3, 1, 3),
(10, 83.0, 124, '2023-06-05', 1, 10, 3, 1, 3);



-- Atividades para o Módulo 1 (Excel Básico)
INSERT INTO atividade (id_atividade, fk_curso, fk_modulo, nome, descricao, peso, valor) VALUES
(1, 4, 1,'Introdução ao Excel', 'Familiarização com a interface e funcionalidades básicas do Excel.', 1.0, 100),
(2, 4, 1,'Formatação de Células', 'Aplicação de formatação em células e planilhas.', 1.5, 100),
(3, 4, 1,'Fórmulas Básicas', 'Utilização de fórmulas básicas para cálculos simples.', 2.0, 100),
(4, 4, 1,'Gráficos Simples', 'Criação de gráficos simples para visualização de dados.', 2.5, 100),
(5, 4, 1,'Classificação e Filtro', 'Classificação e filtragem de dados em planilhas.', 1.0, 100);

-- Atividades para o Módulo 2 (Excel Avançado)
INSERT INTO atividade (id_atividade, fk_curso, fk_modulo, nome, descricao, peso, valor) VALUES
(1, 4, 2, 'Tabelas Dinâmicas', 'Criação e manipulação de tabelas dinâmicas.', 1.0, 100),
(2, 4, 2, 'Gráficos Avançados', 'Desenvolvimento de gráficos avançados e personalizados.', 1.5, 100),
(3, 4, 2, 'Macros', 'Gravação e execução de macros.', 2.0, 100),
(4, 4, 2, 'Solver', 'Utilização do Solver para otimização de problemas.', 2.5, 100),
(5, 4, 2, 'Análise de Cenários', 'Criação e análise de diferentes cenários.', 1.0, 100);

-- Atividades para o Módulo 3 (Excel Avançado)
INSERT INTO atividade (id_atividade, fk_curso, fk_modulo, nome, descricao, peso, valor) VALUES
(1, 4, 3, 'Fórmulas Avançadas', 'Utilização de fórmulas avançadas como PROCV, PROCH, ÍNDICE, CORRESP, entre outras.', 1.0, 100),
(2, 4, 3, 'Funções de Banco de Dados', 'Aplicação de funções de banco de dados para filtrar e analisar grandes conjuntos de dados.', 1.5, 100),
(3, 4, 3, 'Consolidação de Dados', 'Consolidação e análise de dados de múltiplas fontes.', 2.0, 100),
(4, 4, 3, 'Ferramentas de Análise', 'Uso de ferramentas de análise como Tabelas de Dados e Amostragem.', 2.5, 100),
(5, 4, 3, 'Visual Basic for Applications (VBA)', 'Introdução à programação VBA para automação de tarefas no Excel.', 1.0, 100);


-- Pontos para o Usuário 1 (Curso de Excel Básico)
INSERT INTO ponto (id_ponto, nota, qtd_ponto, data_entrega, fk_referencia_gerar_pontuacao, fk_atividade, fk_modulo, fk_curso, fk_usuario) VALUES
(1, 90.0, 90, '2023-01-10', 1, 1, 1, 4, 1),
(2, 85.0, 127, '2023-01-15', 1, 2, 1, 4, 1),
(3, 92.0, 184, '2023-01-20', 1, 3, 1, 4, 1),
(4, 88.0, 220, '2023-01-25', 1, 4, 1, 4, 1),
(5, 93.0, 93, '2023-01-30', 1, 5, 1, 4, 1),
(6, 87.0, 130, '2023-02-05', 1, 1, 2, 4, 1),
(7, 86.0, 129, '2023-02-10', 1, 2, 2, 4, 1),
(8, 90.0, 180, '2023-02-15', 1, 3, 2, 4, 1),
(9, 94.0, 235, '2023-02-20', 1, 4, 2, 4, 1),
(10, 89.0, 89, '2023-02-25', 1, 5, 2, 4, 1),
(11, 91.0, 137, '2023-03-01', 1, 1, 3, 4, 1),
(12, 93.0, 140, '2023-03-05', 1, 2, 3, 4, 1),
(13, 86.0, 172, '2023-03-10', 1, 3, 3, 4, 1),
(14, 95.0, 238, '2023-03-15', 1, 4, 3, 4, 1),
(15, 88.0, 88, '2023-03-20', 1, 5, 3, 4, 1);

-- Pontos para o Usuário 2 (Curso de Excel Avançado)
INSERT INTO ponto (id_ponto, nota, qtd_ponto, data_entrega, fk_referencia_gerar_pontuacao, fk_atividade, fk_modulo, fk_curso, fk_usuario) VALUES
(1, 78.0, 78, '2023-01-10', 1, 1, 1, 4, 2),
(2, 80.0, 120, '2023-01-15', 1, 2, 1, 4, 2),
(3, 85.0, 170, '2023-01-20', 1, 3, 1, 4, 2),
(4, 82.0, 205, '2023-01-25', 1, 4, 1, 4, 2),
(5, 77.0, 77, '2023-01-30', 1, 5, 1, 4, 2),
(6, 81.0, 122, '2023-02-05', 1, 1, 2, 4, 2),
(7, 83.0, 124, '2023-02-10', 1, 2, 2, 4, 2),
(8, 79.0, 158, '2023-02-15', 1, 3, 2, 4, 2),
(9, 84.0, 210, '2023-02-20', 1, 4, 2, 4, 2),
(10, 76.0, 76, '2023-02-25', 1, 5, 2, 4, 2),
(11, 82.0, 123, '2023-03-01', 1, 1, 3, 4, 2),
(12, 85.0, 128, '2023-03-05', 1, 2, 3, 4, 2),
(13, 79.0, 158, '2023-03-10', 1, 3, 3, 4, 2),
(14, 86.0, 215, '2023-03-15', 1, 4, 3, 4, 2),
(15, 75.0, 75, '2023-03-20', 1, 5, 3, 4, 2);

-- Pontos para o Usuário 3 (Curso de Excel Básico)
INSERT INTO ponto (id_ponto, nota, qtd_ponto, data_entrega, fk_referencia_gerar_pontuacao, fk_atividade, fk_modulo, fk_curso, fk_usuario) VALUES
(1, 85.0, 85, '2023-01-10', 1, 1, 1, 4, 3),
(2, 88.0, 132, '2023-01-15', 1, 2, 1, 4, 3),
(3, 90.0, 180, '2023-01-20', 1, 3, 1, 4, 3),
(4, 92.0, 230, '2023-01-25', 1, 4, 1, 4, 3),
(5, 86.0, 86, '2023-01-30', 1, 5, 1, 4, 3),
(6, 89.0, 134, '2023-02-05', 1, 1, 2, 4, 3),
(7, 91.0, 136, '2023-02-10', 1, 2, 2, 4, 3),
(8, 93.0, 186, '2023-02-15', 1, 3, 2, 4, 3),
(9, 95.0, 238, '2023-02-20', 1, 4, 2, 4, 3),
(10, 87.0, 87, '2023-02-25', 1, 5, 2, 4, 3),
(11, 90.0, 135, '2023-03-01', 1, 1, 3, 4, 3),
(12, 92.0, 138, '2023-03-05', 1, 2, 3, 4, 3),
(13, 94.0, 188, '2023-03-10', 1, 3, 3, 4, 3),
(14, 96.0, 240, '2023-03-15', 1, 4, 3, 4, 3);






-- KPI atividades
SELECT
    c.id_curso,
    c.nome AS nome_curso,
    COUNT(p.fk_atividade) AS total_atividades_feitas
FROM
    ponto p
JOIN
    curso c ON p.fk_curso = c.id_curso
WHERE
    p.fk_usuario = 1
GROUP BY
    c.id_curso, c.nome;



 -- Pontos por dia e por curso VIEW
  CREATE VIEW PontosPorDiaEporCursoPorUsuario AS
SELECT
    c.nome AS nome_curso,
    p.data_entrega,
    SUM(p.qtd_ponto) AS pontos_por_dia,
    p.fk_usuario AS usuario
FROM
    Ponto p
JOIN
    Curso c ON p.fk_curso = c.id_curso
GROUP BY
    p.fk_curso, p.data_entrega, p.fk_usuario
ORDER BY
    p.fk_usuario, p.fk_curso, p.data_entrega;


-- pontos totais por curso
SELECT
    fk_curso,
    SUM(qtd_ponto) AS total_pontos
FROM
    ponto
WHERE
    fk_usuario = 1
GROUP BY
    fk_curso;


    -- SELECT PONTOS X SEMANA PASSADA E PRESENTE
    SELECT
            u.id_usuario AS idUsuario,
            u.nome_usuario AS nomeUsuario,
            COALESCE(SUM(CASE WHEN WEEK(p.data_entrega) = WEEK(CURRENT_DATE) THEN p.qtd_ponto ELSE 0 END), 0) AS pontosSemanaAtual,
            COALESCE(SUM(CASE WHEN WEEK(p.data_entrega) = WEEK(CURRENT_DATE) - 1 THEN p.qtd_ponto ELSE 0 END), 0) AS pontosSemanaPassada,
            COALESCE(SUM(CASE WHEN WEEK(p.data_entrega) = WEEK(CURRENT_DATE) THEN p.qtd_ponto ELSE 0 END), 0) -
            COALESCE(SUM(CASE WHEN WEEK(p.data_entrega) = WEEK(CURRENT_DATE) - 1 THEN p.qtd_ponto ELSE 0 END), 0) AS diferencaPontos
        FROM
            Ponto p
        JOIN
            Usuario u ON p.fk_usuario = u.id_usuario
        WHERE
            u.id_usuario = 1
        GROUP BY
            u.id_usuario, u.nome_usuario;


 SELECT
        *
    FROM
        PontosPorDiaEporCursoPorUsuario
    WHERE
        usuario = 1;


-- Pontos Por mes
SELECT
    MONTH(p.data_entrega) AS mes,
    c.id_curso,
    c.nome AS nome_curso,
    SUM(p.qtd_ponto) AS total_pontos
FROM
    ponto p
INNER JOIN
    curso c ON p.fk_curso = c.id_curso
WHERE
    p.fk_usuario = 3
GROUP BY
    mes, c.id_curso, c.nome
ORDER BY
    mes, c.id_curso;



SELECT
    u.id_usuario AS id,
    u.nome_usuario,
    u.email,
    SUM(p.qtd_ponto) AS total_pontos,
    RANK() OVER (ORDER BY SUM(p.qtd_ponto) DESC) AS ranking
FROM
    usuario u
JOIN
    ponto p ON u.id_usuario = p.fk_usuario
JOIN
    curso c ON p.fk_curso = c.id_curso
GROUP BY
    u.id_usuario, u.nome_usuario, u.email
ORDER BY
    total_pontos DESC
LIMIT 300;

-- DASH RH
SELECT
    u.id_usuario AS idUsuario,
    u.nome_usuario AS nomeUsuario,
    u.primeiro_nome AS primeiroNome,
    u.sobrenome AS sobrenome,
    u.email AS email,
    c.nome AS nomeCurso,
    e.cidade AS cidade
FROM
    usuario u
JOIN
    tipo_usuario tu ON u.fk_tipo_usuario = tu.id_tipo_usuario
LEFT JOIN
    endereco e ON u.fk_endereco = e.id_endereco
LEFT JOIN
    inscricao i ON u.id_usuario = i.fk_usuario
LEFT JOIN
    curso c ON i.fk_curso = c.id_curso
WHERE
    u.fk_tipo_usuario = 2
LIMIT 0, 300;

SELECT
	* 
FROM  curso
LEFT JOIN
    inscricao i ON u.id_usuario = i.fk_usuario

