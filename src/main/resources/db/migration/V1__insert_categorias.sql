-- Categorias do Tipo DESPESA
INSERT INTO categoria (nome, tipo_categoria)
VALUES
    ('Aluguel', 'DESPESA'),
    ('Cartão de Crédito', 'DESPESA'),
    ('Supermercado', 'DESPESA'),
    ('Transporte', 'DESPESA'),
    ('Educação', 'DESPESA'),
    ('Saúde', 'DESPESA'),
    ('Entretenimento', 'DESPESA'),
    ('Roupas', 'DESPESA'),
    ('Viagem', 'DESPESA'),
    ('Assinaturas', 'DESPESA');

-- Categorias do Tipo RECEITA
INSERT INTO categoria (nome, tipo_categoria)
VALUES
    ('Salário', 'RECEITA'),
    ('Freelance', 'RECEITA'),
    ('Investimentos', 'RECEITA'),
    ('Aluguel de Imóvel', 'RECEITA'),
    ('Vendas Online', 'RECEITA'),
    ('Dividendos', 'RECEITA'),
    ('Rendimento Poupança', 'RECEITA'),
    ('Prêmios', 'RECEITA'),
    ('Restituição de Imposto', 'RECEITA'),
    ('Pensão', 'RECEITA');
