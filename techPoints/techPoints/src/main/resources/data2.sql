-- MySQL Workbench Forward Engineering


DROP database mydb;
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
  id_referencia_gerar_pontuacao INT NOT NULL,
  nome_da_geracao VARCHAR(45) NULL,
  descricao_da_geracao VARCHAR(45) NULL,
  PRIMARY KEY (id_referencia_gerar_pontuacao)
);


-- -----------------------------------------------------
-- Table `mydb`.`ponto`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS ponto (
  id_ponto INT NOT NULL,
  qtd_ponto INT NOT NULL,
  fk_referencia_gerar_pontuacao INT NOT NULL,
  PRIMARY KEY (id_ponto),
  CONSTRAINT fk_ponto_referencia_gerar_pontuacao1 FOREIGN KEY (f k_referencia_gerar_pontuacao) REFERENCES referencia_gerar_pontuacao (id_referencia_gerar_pontuacao)
);


-- -----------------------------------------------------
-- Table `mydb`.`pontuacao`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS pontuacao (
  id_pontuacao INT AUTO_INCREMENT NOT NULL,
  fk_usuario INT NOT NULL,
  fk_ponto INT NOT NULL,
  total_pontos_usuario INT NULL,
  data_atualizacao DATETIME NULL,
  PRIMARY KEY (id_pontuacao),
  CONSTRAINT fk_pontuacao_pontos1 FOREIGN KEY (fk_ponto) REFERENCES ponto (id_ponto),
  CONSTRAINT fk_pontuacao_usuario1 FOREIGN KEY (fk_usuario) REFERENCES usuario (id_usuario)
);


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
  CONSTRAINT fk_usuario FOREIGN KEY (fk_usuario) REFERENCES usuario (id_usuario)
);

-- -----------------------------------------------------
-- Table `mydb`.`categoria_curso`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS categoria_curso (
  id_categoria_curso INT AUTO_INCREMENT PRIMARY KEY,
  nome VARCHAR(45) NOT NULL
);


-- -----------------------------------------------------
-- Table `mydb`.`curso`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS curso (
  id_curso INT AUTO_INCREMENT PRIMARY KEY,
  nome VARCHAR(45) NOT NULL,
  qtd_horas INT NOT NULL,
  fk_categoria_curso INT NOT NULL,
  CONSTRAINT fk_curso_categoria_curso1 FOREIGN KEY (fk_categoria_curso) REFERENCES categoria_curso (id_categoria_curso)
);



-- -----------------------------------------------------
-- Table `mydb`.`inscricao`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`inscricao` (
  `fk_usuario` INT NOT NULL,
  `fk_curso` INT NOT NULL,
  `codigo_inscricao` VARCHAR(100) NULL,
  PRIMARY KEY (`fk_usuario`, `fk_curso`),
  INDEX `fk_usuario_has_curso_curso1_idx` (`fk_curso` ASC) VISIBLE,
  INDEX `fk_usuario_has_curso_usuario1_idx` (`fk_usuario` ASC) VISIBLE,
  CONSTRAINT `fk_usuario_has_curso_usuario1`
    FOREIGN KEY (`fk_usuario`)
    REFERENCES `mydb`.`usuario` (`id_usuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_usuario_has_curso_curso1`
    FOREIGN KEY (`fk_curso`)
    REFERENCES `mydb`.`curso` (`id_curso`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`atividade`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `atividade` (
  id_atividade INT AUTO_INCREMENT PRIMARY KEY,
  nota INT NOT NULL,
  temp_duracao TIME NOT NULL
);


-- -----------------------------------------------------
-- Table `mydb`.`modulo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `modulo` (
  id_modulo INT AUTO_INCREMENT PRIMARY KEY,
  fk_curso INT NOT NULL,
  fk_atividade INT NOT NULL,
  qtd_atividade_feita INT NOT NULL,
  qtd_atividade_total INT NOT NULL,
  nome_modulo VARCHAR(45),
  CONSTRAINT fk_modulo_curso FOREIGN KEY (fk_curso) REFERENCES curso (id_curso),
  CONSTRAINT fk_modulo_atividade FOREIGN KEY (fk_atividade) REFERENCES atividade (id_atividade)
);


-- -----------------------------------------------------
-- Table `mydb`.`classificacao`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`classificacao` (
  `id_classificacao` INT NOT NULL,
  `total_pontos` INT NULL,
  PRIMARY KEY (`id_classificacao`))
ENGINE = InnoDB;


-- -----------------------------------------------------
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
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`tempo_estudo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`tempo_estudo` (
  `id_tempo_estudo` INT NOT NULL,
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
  `id_tempo_sessao` INT NOT NULL,
  `dia_sessao` VARCHAR(45) NULL,
  `qtd_tempo_sessao` VARCHAR(5) NULL,
  `fk_usuario` INT NOT NULL,
  PRIMARY KEY (`id_tempo_sessao`),
  INDEX `fk_tempo_sessao_usuario1_idx` (`fk_usuario` ASC) VISIBLE,
  CONSTRAINT `fk_tempo_sessao_usuario1`
    FOREIGN KEY (`fk_usuario`)
    REFERENCES `mydb`.`usuario` (`id_usuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`meta_estudo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`meta_estudo` (
  `fk_tempo_estudo` INT NOT NULL,
  `fk_tempo_sessao` INT NOT NULL,
  `tempo_ate_completar` VARCHAR(5) NULL,
  `tempo_total_estimado` VARCHAR(5) NULL,
  INDEX `fk_usuario_has_tempo_estudo_tempo_estudo1_idx` (`fk_tempo_estudo` ASC) VISIBLE,
  INDEX `fk_meta_estudo_tempo_sessao1_idx` (`fk_tempo_sessao` ASC) VISIBLE,
  CONSTRAINT `fk_usuario_has_tempo_estudo_tempo_estudo1`
    FOREIGN KEY (`fk_tempo_estudo`)
    REFERENCES `mydb`.`tempo_estudo` (`id_tempo_estudo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_meta_estudo_tempo_sessao1`
    FOREIGN KEY (`fk_tempo_sessao`)
    REFERENCES `mydb`.`tempo_sessao` (`id_tempo_sessao`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


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
('usuario1', '12345678901', 'senha1', 'João', 'Silva', 'joao.silva@example.com', NOW(), 0, 1, 1, 2),
('usuario2', '98765432100', 'senha2', 'Maria', 'Souza', 'maria.souza@example.com', NOW(), 0, 1, 2, 2),
('usuario3', '11122233344', 'senha3', 'Carlos', 'Pereira', 'carlos.pereira@example.com', NOW(), 0, 1, 3, 3);

-- Populando a tabela referencia_gerar_pontuacao
INSERT INTO referencia_gerar_pontuacao (id_referencia_gerar_pontuacao, nome_da_geracao, descricao_da_geracao) VALUES
(1, 'Estudo', 'Pontuação por horas de estudo'),
(2, 'Participação', 'Pontuação por participação em atividades');

-- Populando a tabela ponto
INSERT INTO ponto (id_ponto, qtd_ponto, fk_referencia_gerar_pontuacao) VALUES
(1, 10, 1),
(2, 20, 2),
(3, 15, 1),
(4, 25, 2);

-- Populando a tabela pontuacao
INSERT INTO pontuacao (fk_usuario, fk_ponto, total_pontos_usuario, data_atualizacao) VALUES
(1, 1, 100, NOW()),
(1, 2, 200, NOW()),
(1, 3, 150, NOW()),
(1, 4, 250, NOW());

-- Populando a tabela redefinicao_senha
INSERT INTO redefinicao_senha (codigo_redefinicao, data_criacao, data_expiracao, valido, email_redefinicao, fk_usuario) VALUES
('ABC123', NOW(), DATE_ADD(NOW(), INTERVAL 1 DAY), 1, 'joao.silva@example.com', 1),
('XYZ789', NOW(), DATE_ADD(NOW(), INTERVAL 1 DAY), 1, 'maria.souza@example.com', 2);

-- Populando a tabela categoria_curso
INSERT INTO categoria_curso (nome) VALUES
('Tecnologia'),
('Gestão'),
('Idiomas');

-- Populando a tabela curso
INSERT INTO curso (nome, qtd_horas, fk_categoria_curso) VALUES
('Curso de Programação', 40, 1),
('Curso de Liderança', 20, 2),
('Curso de Inglês', 60, 3),
('Curso de Marketing Digital', 30, 2),
('Curso de Web Design', 50, 1),
('Curso de Fotografia', 40, 3),
('Curso de Finanças Pessoais', 25, 2),
('Curso de Gestão de Projetos', 35, 2);

-- Populando a tabela inscricao
INSERT INTO inscricao (fk_usuario, fk_curso, codigo_inscricao) VALUES
(1, 1, 'INSCR001'),
(1, 2, 'INSCR002'),
(1, 3, 'INSCR003'),
(1, 4, 'INSCR004'),
(1, 5, 'INSCR005'),
(1, 6, 'INSCR006'),
(1, 7, 'INSCR007'),
(1, 8, 'INSCR008');

-- Populando a tabela atividade
INSERT INTO atividade (nota, temp_duracao) VALUES
(85, '01:30:00'),
(90, '02:00:00'),
(95, '00:45:00'),
(80, '01:15:00'),
(92, '02:30:00'),
(88, '01:00:00');

-- Populando a tabela modulo
INSERT INTO modulo (fk_curso, fk_atividade, qtd_atividade_feita, qtd_atividade_total, nome_modulo) VALUES
(1, 1, 5, 10, 'Módulo 1 de Programação'),
(2, 2, 3, 5, 'Módulo 1 de Liderança'),
(3, 3, 2, 4, 'Módulo 1 de Inglês'),
(4, 4, 4, 6, 'Módulo 1 de Marketing Digital'),
(5, 5, 2, 3, 'Módulo 1 de Web Design'),
(6, 6, 3, 5, 'Módulo 1 de Fotografia'),
(7, 1, 2, 4, 'Módulo 1 de Finanças Pessoais'),
(8, 2, 4, 6, 'Módulo 1 de Gestão de Projetos');

-- Populando a tabela classificacao
INSERT INTO classificacao (id_classificacao, total_pontos) VALUES
(1, 100),
(2, 200),
(3, 300);

-- Populando a tabela dados_empresa
INSERT INTO dados_empresa (nome_empresa, setor_industria, cargo_usuario, cnpj, data_atualizacao, `e-mail_corporativo`, telefone_contato_corporativo, fk_usuario) VALUES
('Empresa A', 'Tecnologia', 'Desenvolvedor', '12345678000100', NOW(), 'joao@empresaA.com', '11987654321', 1),
('Empresa B', 'Gestão', 'Gerente', '98765432000111', NOW(), 'maria@empresaB.com', '21987654321', 2),
('Empresa C', 'Educação', 'Professor', '11223344000155', NOW(), 'carlos@empresaC.com', '31987654321', 3);

-- Populando a tabela tempo_estudo
INSERT INTO tempo_estudo (id_tempo_estudo, nome_dia, qtd_tempo_estudo, ativado, meta_alcancada) VALUES
(1, 'Segunda-feira', '01:00', 1, 1),
(2, 'Terça-feira', '02:00', 1, 0),
(3, 'Quarta-feira', '03:00', 1, 1);

-- Populando a tabela tempo_sessao
INSERT INTO tempo_sessao (id_tempo_sessao, dia_sessao, qtd_tempo_sessao, fk_usuario) VALUES
(1, '2023-06-01', '01:00', 1),
(2, '2023-06-02', '02:00', 2),
(3, '2023-06-03', '03:00', 3);

-- Populando a tabela meta_estudo
INSERT INTO meta_estudo (fk_tempo_estudo, fk_tempo_sessao, tempo_ate_completar, tempo_total_estimado) VALUES
(1, 1, '01:00', '02:00'),
(2, 2, '02:00', '04:00'),
(3, 3, '03:00', '06:00');







