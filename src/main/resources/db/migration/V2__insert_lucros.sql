-- Script para inserir lucros na tabela Lucro
INSERT INTO Lucro (pessoa_id, descricao, categoria_id, valor, data)
VALUES
    (5, 'Salário mensal', (SELECT id FROM Categoria WHERE nome = 'Salario'), 5000.00, '2024-08-01'),
    (5, 'Venda de produtos', (SELECT id FROM Categoria WHERE nome = 'Vendas'), 2000.00, '2024-08-03'),
    (5, 'Consultoria realizada', (SELECT id FROM Categoria WHERE nome = 'Consultoria'), 1500.00, '2024-08-07'),
    (5, 'Dividendos recebidos', (SELECT id FROM Categoria WHERE nome = 'Dividendos'), 800.00, '2024-08-15'),
    (5, 'Renda extra de freelancing', (SELECT id FROM Categoria WHERE nome = 'Freelance'), 600.00, '2024-08-20');
