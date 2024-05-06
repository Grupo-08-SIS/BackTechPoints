INSERT INTO categoria_produto (nome, descricao_categoria) VALUES
('Alimentação', 'Produtos para preparação de comida'),
('Bebidas', 'Diversos tipos de bebidas para consumo'),
('Utensílios de Cozinha', 'Ferramentas e acessórios para cozinhar'),
('Produtos Orgânicos', 'Alimentos cultivados sem o uso de pesticidas ou fertilizantes sintéticos'),
('Sobremesas', 'Deliciosas opções para finalizar a refeição com doçura');

INSERT INTO Produto (nome, valor_pontos, descricao, quantidade, disponivel, categoria_produto_id)
VALUES ('Arroz', 50, 'Arroz integral', 10, true, 1),
('Feijão', 60, 'Feijão carioca', 8, true, 1),
('Bolacha', 30, 'Bolacha cream cracker', 15, true, 1),
('Farofa', 40, 'Farofa pronta', 5, true, 1),
('Refrigerante', 50, 'Refrigerante de Cola', 7, false, null)