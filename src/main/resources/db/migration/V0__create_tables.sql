CREATE TABLE usuario (
                         id SERIAL PRIMARY KEY,
                         nome VARCHAR(255),
                         cpf VARCHAR(14),
                         email VARCHAR(255),
                         senha VARCHAR(255),
                         telefone VARCHAR(20),
                         role VARCHAR(255) DEFAULT 'USER'
);

CREATE TABLE categoria (
                           id SERIAL PRIMARY KEY,
                           nome VARCHAR(255),
                           tipo_categoria VARCHAR(255) NOT NULL,
                           categoria_pai_id INTEGER,
                           FOREIGN KEY (categoria_pai_id) REFERENCES categoria(id)
);

CREATE TABLE plano_orcamentario (
                                    id SERIAL PRIMARY KEY,
                                    nome VARCHAR(255),
                                    descricao TEXT,
                                    data_inicio DATE,
                                    data_fim DATE,
                                    ativo BOOLEAN DEFAULT true,
                                    usuario_id INTEGER,
                                    FOREIGN KEY (usuario_id) REFERENCES usuario(id)
);

CREATE TABLE orcamento (
                           id SERIAL PRIMARY KEY,
                           plano_orcamentario_id INTEGER,
                           valor_previsto DECIMAL(19,2),
                           ativo BOOLEAN DEFAULT true,
                           categoria_id INTEGER,
                           FOREIGN KEY (plano_orcamentario_id) REFERENCES plano_orcamentario(id),
                           FOREIGN KEY (categoria_id) REFERENCES categoria(id)
);

CREATE TABLE relatorio_financeiro (
                                      id SERIAL PRIMARY KEY,
                                      descricao TEXT,
                                      data_geracao DATE,
                                      usuario_id INTEGER,
                                      FOREIGN KEY (usuario_id) REFERENCES usuario(id)
);

CREATE TABLE transacao_financeira (
                                      id SERIAL PRIMARY KEY,
                                      usuario_id INTEGER,
                                      descricao VARCHAR(255),
                                      categoria_id INTEGER,
                                      valor DECIMAL(19,2),
                                      data DATE,
                                      tipo_transacao VARCHAR(255) NOT NULL,
                                      plano_orcamentario_id INTEGER,
                                      FOREIGN KEY (usuario_id) REFERENCES usuario(id),
                                      FOREIGN KEY (categoria_id) REFERENCES categoria(id),
                                      FOREIGN KEY (plano_orcamentario_id) REFERENCES plano_orcamentario(id)
);