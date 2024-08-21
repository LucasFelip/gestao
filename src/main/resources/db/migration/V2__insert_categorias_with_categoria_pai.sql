-- Novas Categorias do Tipo DESPESA com categoria_pai_id
INSERT INTO categoria (nome, tipo_categoria, categoria_pai_id)
VALUES
    ('Aluguel - Casa', 'DESPESA', 1),
    ('Cartão de Crédito - Anuidade', 'DESPESA', 2),
    ('Supermercado - Compras Mensais', 'DESPESA', 3),
    ('Transporte - Combustível', 'DESPESA', 4),
    ('Educação - Cursos Online', 'DESPESA', 5);

-- Novas Categorias do Tipo RECEITA com categoria_pai_id
INSERT INTO categoria (nome, tipo_categoria, categoria_pai_id)
VALUES
    ('Salário - Décimo Terceiro', 'RECEITA', 11),
    ('Freelance - Projetos Especiais', 'RECEITA', 12),
    ('Investimentos - Ações', 'RECEITA', 13),
    ('Aluguel de Imóvel - Comercial', 'RECEITA', 14),
    ('Vendas Online - Marketplace', 'RECEITA', 15);
