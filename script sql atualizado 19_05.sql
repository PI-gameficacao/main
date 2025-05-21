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
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario)
);

CREATE TABLE sala (
    id_sala INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    id_materia INT,
    FOREIGN KEY (id_materia) REFERENCES materia(id_materia)
);

CREATE TABLE aluno (
    id_aluno INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL
);

CREATE TABLE matricula (
    id_matricula INT PRIMARY KEY AUTO_INCREMENT,
    id_aluno INT,
    id_materia INT,
    FOREIGN KEY (id_aluno) REFERENCES aluno(id_aluno),
    FOREIGN KEY (id_materia) REFERENCES materia(id_materia),
    CONSTRAINT unique_matricula UNIQUE (id_aluno, id_materia)
);

CREATE TABLE nota (
    id_nota INT PRIMARY KEY AUTO_INCREMENT,
    id_aluno INT,
    id_sala INT,
    valor DECIMAL(5,2) NOT NULL,
    nome_nota VARCHAR(100) NOT NULL,
    FOREIGN KEY (id_aluno) REFERENCES aluno(id_aluno),
    FOREIGN KEY (id_sala) REFERENCES sala(id_sala)
);


SELECT * FROM usuario;

SELECT id_usuario, nome , tipo FROM  usuario WHERE tipo = 'Administrador';


SELECT id_materia, nome, id_usuario FROM materia;

ALTER TABLE materia ADD CONSTRAINT fk_materia_usuario FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario) ON DELETE CASCADE ON UPDATE CASCADE;