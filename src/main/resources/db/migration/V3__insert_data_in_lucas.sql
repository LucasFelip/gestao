-- Plano Orçamentário 1 (Ativo)
INSERT INTO plano_orcamentario (nome, data_inicio, data_fim, ativo, usuario_id)
VALUES ('Plano Orçamentário Anual 2024', '2024-01-01', '2024-12-31', true, 10);

-- Plano Orçamentário 2 (Inativo)
INSERT INTO plano_orcamentario (nome, data_inicio, data_fim, ativo, usuario_id)
VALUES ('Plano Orçamentário Anual 2023', '2023-01-01', '2023-12-31', false, 10);

-- Orçamento para o Plano Orçamentário Ativo
INSERT INTO orcamento (valor_previsto, plano_orcamentario_id, categoria_id, ativo)
VALUES
    (8000, 1, 3, true),
    (6000, 1, 4, true),
    (10000, 1, 5, true),
    (7000, 1, 6, true),
    (4000, 1, 7, true);

-- Transações Financeiras de Despesas
INSERT INTO transacao_financeira (descricao, valor, data, tipo_transacao, categoria_id, usuario_id, plano_orcamentario_id)
VALUES
    ('Compra Supermercado', 150.75, '2024-01-10', 'DESPESA', 3, 10, 1),
    ('Abastecimento de Combustível', 200.50, '2024-01-15', 'DESPESA', 4, 10, 1),
    ('Mensalidade Curso Online', 500.00, '2024-01-20', 'DESPESA', 5, 10, 1),
    ('Consulta Médica', 250.00, '2024-01-25', 'DESPESA', 6, 10, 1),
    ('Cinema e Lazer', 120.00, '2024-02-01', 'DESPESA', 7, 10, 1),
    ('Compra de Roupas', 300.00, '2024-02-05', 'DESPESA', 8, 10, 1),
    ('Viagem de Férias', 1500.00, '2024-03-10', 'DESPESA', 9, 10, 1),
    ('Pagamento de Assinaturas', 50.00, '2024-03-15', 'DESPESA', 10, 10, 1),
    ('Jantar em Restaurante', 100.00, '2024-03-20', 'DESPESA', 3, 10, 1),
    ('Compra de Material Escolar', 200.00, '2024-03-25', 'DESPESA', 5, 10, 1);

-- Transações Financeiras de Receitas
INSERT INTO transacao_financeira (descricao, valor, data, tipo_transacao, categoria_id, usuario_id, plano_orcamentario_id)
VALUES
    ('Recebimento de Salário', 5000.00, '2024-01-05', 'RECEITA', 11, 10, 1),
    ('Freelance de Projeto Web', 1200.00, '2024-01-15', 'RECEITA', 12, 10, 1),
    ('Recebimento de Dividendos', 300.00, '2024-01-20', 'RECEITA', 16, 10, 1),
    ('Aluguel de Imóvel', 2500.00, '2024-02-01', 'RECEITA', 14, 10, 1),
    ('Venda Online de Produtos', 600.00, '2024-02-10', 'RECEITA', 15, 10, 1),
    ('Prêmio em Concurso', 800.00, '2024-02-20', 'RECEITA', 18, 10, 1),
    ('Restituição de Imposto de Renda', 1500.00, '2024-03-10', 'RECEITA', 19, 10, 1),
    ('Receita de Pensão', 1000.00, '2024-03-15', 'RECEITA', 20, 10, 1),
    ('Rendimento de Poupança', 200.00, '2024-03-25', 'RECEITA', 17, 10, 1),
    ('Bonificação', 700.00, '2024-03-30', 'RECEITA', 11, 10, 1);
