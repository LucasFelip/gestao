-- Script para inserir gastos na tabela Gasto
INSERT INTO Gasto (pessoa_id, descricao, categoria_id, valor, data)
VALUES
    (5, 'Compra de materiais de escritório', (SELECT id FROM Categoria WHERE nome = 'Compras'), 150.00, '2024-08-01'),
    (5, 'Pagamento de aluguel', (SELECT id FROM Categoria WHERE nome = 'Aluguel'), 1200.00, '2024-08-05'),
    (5, 'Manutenção do carro', (SELECT id FROM Categoria WHERE nome = 'Manutencao'), 300.00, '2024-08-10'),
    (5, 'Seguro do carro', (SELECT id FROM Categoria WHERE nome = 'Seguro'), 400.00, '2024-08-12'),
    (5, 'Compra de alimentos', (SELECT id FROM Categoria WHERE nome = 'Alimentacao'), 250.00, '2024-08-15');
