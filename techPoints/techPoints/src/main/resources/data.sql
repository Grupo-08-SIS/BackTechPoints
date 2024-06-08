SELECT * FROM mydb.usuario;

SELECT * FROM mydb.endereco;

select * from mydb.redefinicao_senha;

select * from mydb.tipo_usuario;

select * from mydb.dados_empresa;

SELECT p.data_atualizacao, p.fk_ponto, p.total_pontos_usuario
FROM pontuacao p
WHERE p.fk_usuario = 1;

SELECT p.fk_ponto, SUM(p.total_pontos_usuario)
FROM pontuacao p
WHERE p.fk_usuario = 1
GROUP BY p.fk_ponto;

DELETE FROM pontuacao WHERE fk_usuario = 1;

-- Inserindo novos registros de pontuação para refletir a distribuição diária
-- Semana 1
INSERT INTO pontuacao (fk_usuario, fk_ponto, total_pontos_usuario, data_atualizacao) VALUES
(1, 1, 100, '2024-06-03'),
(1, 2, 50, '2024-06-03'),
(1, 1, 50, '2024-06-04'),
(1, 2, 70, '2024-06-04'),
(1, 1, 80, '2024-06-05'),
(1, 2, 100, '2024-06-05'),
(1, 1, 120, '2024-06-06'),
(1, 2, 80, '2024-06-06'),
(1, 1, 150, '2024-06-07'),
(1, 2, 120, '2024-06-07'),
(1, 1, 200, '2024-06-08'),
(1, 2, 150, '2024-06-08'),
(1, 1, 180, '2024-06-09'),
(1, 2, 200, '2024-06-09');


-- dash linha
SELECT
    p.data_atualizacao AS data,
    c.nome AS curso,
    SUM(p.total_pontos_usuario) AS total_pontos
FROM
    pontuacao p
JOIN
    usuario u ON p.fk_usuario = u.id_usuario
JOIN
    inscricao i ON u.id_usuario = i.fk_usuario
JOIN
    curso c ON i.fk_curso = c.id_curso
WHERE
    p.fk_usuario = 1
    AND YEARWEEK(p.data_atualizacao, 1) = YEARWEEK(NOW(), 1)
GROUP BY
    p.data_atualizacao,
    c.nome
ORDER BY
    p.data_atualizacao;

 -- dash coluna

 SELECT
    DATE_FORMAT(p.data_atualizacao, '%Y-%m') AS mes,
    c.nome AS curso,
    SUM(p.total_pontos_usuario) AS total_pontos
FROM
    pontuacao p
JOIN
    usuario u ON p.fk_usuario = u.id_usuario
JOIN
    inscricao i ON u.id_usuario = i.fk_usuario
JOIN
    curso c ON i.fk_curso = c.id_curso
WHERE
    p.fk_usuario = 1
GROUP BY
    DATE_FORMAT(p.data_atualizacao, '%Y-%m'),
    c.nome
ORDER BY
    DATE_FORMAT(p.data_atualizacao, '%Y-%m');



-- Atividades feitas
    SELECT
    c.nome AS curso,
    SUM(m.qtd_atividade_total) AS total_atividades,
    SUM(m.qtd_atividade_feita) AS atividades_feitas
FROM
    inscricao i
JOIN
    usuario u ON i.fk_usuario = u.id_usuario
JOIN
    curso c ON i.fk_curso = c.id_curso
JOIN
    modulo m ON c.id_curso = m.fk_curso
WHERE
    u.id_usuario = 1
GROUP BY
    c.nome, m.nome_modulo;



    -- Pontos da semana atual
SELECT
    COALESCE(SUM(p.total_pontos_usuario), 0) AS pontos_semana_atual
FROM
    pontuacao p
WHERE
    p.fk_usuario = 1
    AND YEARWEEK(p.data_atualizacao, 1) = YEARWEEK(CURDATE(), 1);

-- Pontos da semana passada
SELECT
    COALESCE(SUM(p.total_pontos_usuario), 0) AS pontos_semana_passada
FROM
    pontuacao p
WHERE
    p.fk_usuario = 1
    AND YEARWEEK(p.data_atualizacao, 1) = YEARWEEK(CURDATE() - INTERVAL 1 WEEK, 1);

-- Comparando os pontos das semanas
SELECT
    u.id_usuario,
    u.nome_usuario,
    COALESCE(atual.pontos_semana_atual, 0) AS pontos_semana_atual,
    COALESCE(passada.pontos_semana_passada, 0) AS pontos_semana_passada,
    COALESCE(atual.pontos_semana_atual, 0) - COALESCE(passada.pontos_semana_passada, 0) AS diferenca_pontos
FROM
    usuario u
LEFT JOIN
    (
        SELECT
            p.fk_usuario,
            SUM(p.total_pontos_usuario) AS pontos_semana_atual
        FROM
            pontuacao p
        WHERE
            YEARWEEK(p.data_atualizacao, 1) = YEARWEEK(CURDATE(), 1)
        GROUP BY
            p.fk_usuario
    ) AS atual
ON
    u.id_usuario = atual.fk_usuario
LEFT JOIN
    (
        SELECT
            p.fk_usuario,
            SUM(p.total_pontos_usuario) AS pontos_semana_passada
        FROM
            pontuacao p
        WHERE
            YEARWEEK(p.data_atualizacao, 1) = YEARWEEK(CURDATE() - INTERVAL 1 WEEK, 1)
        GROUP BY
            p.fk_usuario
    ) AS passada
ON
    u.id_usuario = passada.fk_usuario;



    -- view
CREATE VIEW pontuacao_usuario_semana AS
SELECT
    u.id_usuario,
    u.nome_usuario,
    COALESCE(atual.pontos_semana_atual, 0) AS pontos_semana_atual,
    COALESCE(passada.pontos_semana_passada, 0) AS pontos_semana_passada,
    COALESCE(atual.pontos_semana_atual, 0) - COALESCE(passada.pontos_semana_passada, 0) AS diferenca_pontos
FROM
    usuario u
LEFT JOIN
    (
        SELECT
            p.fk_usuario,
            SUM(p.total_pontos_usuario) AS pontos_semana_atual
        FROM
            pontuacao p
        WHERE
            YEARWEEK(p.data_atualizacao, 1) = YEARWEEK(CURDATE(), 1)
        GROUP BY
            p.fk_usuario
    ) AS atual
ON
    u.id_usuario = atual.fk_usuario
LEFT JOIN
    (
        SELECT
            p.fk_usuario,
            SUM(p.total_pontos_usuario) AS pontos_semana_passada
        FROM
            pontuacao p
        WHERE
            YEARWEEK(p.data_atualizacao, 1) = YEARWEEK(CURDATE() - INTERVAL 1 WEEK, 1)
        GROUP BY
            p.fk_usuario
    ) AS passada
ON
    u.id_usuario = passada.fk_usuario;

    -- Dash aluno progresso
    SELECT
    SEC_TO_TIME(SUM(TIME_TO_SEC(se.duracao))) AS total_estudado,
    SEC_TO_TIME(TIME_TO_SEC(me.meta_semanal)) AS meta_semanal,
    ROUND(
        (SUM(TIME_TO_SEC(se.duracao)) / TIME_TO_SEC(me.meta_semanal)) * 100,
        2
    ) AS progresso_percentual
FROM
    sessoes_estudo se
JOIN
    metas_estudo me ON se.fk_usuario = me.fk_usuario
WHERE
    se.fk_usuario = 1
    AND YEARWEEK(se.data, 1) = YEARWEEK(CURDATE(), 1);


-- Dash RH
SELECT
    u.id_usuario,
    u.nome_usuario,
    u.primeiro_nome,
    u.sobrenome,
    u.email,
    c.nome AS nome_curso,
    e.cidade
FROM
    usuario u
JOIN
    inscricao i ON u.id_usuario = i.fk_usuario
JOIN
    curso c ON i.fk_curso = c.id_curso
JOIN
    endereco e ON u.fk_endereco = e.id_endereco
WHERE
    c.nome = 'Nome do Curso'
    AND e.cidade = 'Nome da Cidade';





-- http://localhost:8080/grafico/pontosPorCursoAoMes/2
select
        date_format(p1_0.data_atualizacao, '%Y-%m'),
        c1_0.nome,
        sum(p1_0.total_pontos_usuario)
    from
        pontuacao p1_0
    join
        usuario u1_0
            on u1_0.id_usuario=p1_0.fk_usuario
    join
        inscricao i1_0
            on u1_0.id_usuario=i1_0.fk_usuario
    join
        curso c1_0
            on c1_0.id_curso=i1_0.fk_curso
    where
        u1_0.id_usuario=1
    group by
        date_format(p1_0.data_atualizacao, '%Y-%m'),
        c1_0.nome
    order by
        date_format(p1_0.data_atualizacao, '%Y-%m');

-- http://localhost:8080/grafico/pontosAoLongoDoTempo/0
select
        p1_0.data_atualizacao,
        c1_0.nome,
        sum(p1_0.total_pontos_usuario)
    from
        pontuacao p1_0
    join
        usuario u1_0
            on u1_0.id_usuario=p1_0.fk_usuario
    join
        inscricao i1_0
            on u1_0.id_usuario=i1_0.fk_usuario
    join
        curso c1_0
            on c1_0.id_curso=i1_0.fk_curso
    where
        u1_0.id_usuario=1
        and yearweek(p1_0.data_atualizacao, 1)=yearweek(now(), 1)
    group by
        p1_0.data_atualizacao,
        c1_0.nome
    order by
        p1_0.data_atualizacao;
