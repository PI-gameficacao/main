CREATE DATABASE gamificacao_escolar;
USE gamificacao_escolar;

CREATE TABLE usuario (  
    id_usuario INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    senha VARCHAR(255) NOT NULL,
    tipo ENUM('Professor', 'Administrador') NOT NULL
);


CREATE TABLE materia (
    id_materia INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    id_usuario INT,
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE sala (
    id_sala INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    id_materia INT,
    FOREIGN KEY (id_materia) REFERENCES materia(id_materia) ON DELETE CASCADE ON UPDATE CASCADE
);

ALTER TABLE sala ADD COLUMN id_usuario INT;

CREATE TABLE aluno (
    id_aluno INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL
);

CREATE TABLE matricula (
    id_matricula INT PRIMARY KEY AUTO_INCREMENT,
    id_aluno INT,
    id_sala INT,
    FOREIGN KEY (id_aluno) REFERENCES aluno(id_aluno) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (id_sala) REFERENCES sala(id_sala) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT unique_matricula UNIQUE (id_aluno, id_sala)
);


CREATE TABLE nota (
    id_nota INT PRIMARY KEY AUTO_INCREMENT,
    id_aluno INT,
    id_sala INT,
    valor DECIMAL(5,2) NOT NULL,
    nome_nota VARCHAR(100) NOT NULL,
    FOREIGN KEY (id_aluno) REFERENCES aluno(id_aluno) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (id_sala) REFERENCES sala(id_sala) ON DELETE CASCADE ON UPDATE CASCADE
);

-- Comandos opcionais para verificação
-- SELECT * FROM usuario;
-- SELECT * FROM materia;
-- SELECT * FROM sala;
-- SELECT id_materia, nome FROM materia;
-- INSERT INTO sala (nome, id_materia) VALUES ('Sala de Teste', 1);
-- DESCRIBE sala;



DESC nota;