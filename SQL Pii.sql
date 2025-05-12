CREATE DATABASE IF NOT EXISTS gamificacao_escolar;
USE gamificacao_escolar;

-- Tabela dos usuarios
CREATE TABLE usuarios (
id_usuario INT auto_increment primary key,
nome VARCHAR(100) NOT NULL, 
email VARCHAR(100) NOT NULL UNIQUE, 
senha VARCHAR(255) NOT NULL,
tipo_usuario ENUM('aluno', 'professor', 'diretor') NOT NULL 
);

-- Tabela das turmas
CREATE TABLE turmas (
id_turma INT AUTO_INCREMENT PRIMARY key,
nome_turma VARCHAR(100) NOT NULL 
); 

-- Quais alunos estão dentro das turmas
CREATE TABLE alunos_turmas(
id_aluno INT,
id_turma INT, 
PRIMARY KEY (id_aluno, id_turma),
FOREIGN KEY (id_aluno) REFERENCES usuarios(id_usuario) ON DELETE CASCADE,
FOREIGN KEY (id_turma) REFERENCES turmas(id_turma) ON DELETE CASCADE
);

-- Quais professores estão em cada turma 
CREATE TABLE professores_turmas(
id_professor INT,
id_turma INT,
PRIMARY KEY (id_professor, id_turma),
FOREIGN KEY (id_professor) REFERENCES usuarios(id_usuario) ON DELETE CASCADE,
FOREIGN KEY (id_turma) REFERENCES turmas(id_turma) ON DELETE CASCADE
);

-- Tabelas de pontuação dos alunos 
CREATE TABLE pontuacoes(
 id_pontuacao INT AUTO_INCREMENT PRIMARY KEY,
    id_aluno INT NOT NULL,
    id_professor INT NOT NULL,
    id_turma INT NOT NULL,
    motivo TEXT NOT NULL,
    pontos INT NOT NULL,
    data_atribuicao DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_aluno) REFERENCES usuarios(id_usuario) ON DELETE CASCADE,
    FOREIGN KEY (id_professor) REFERENCES usuarios(id_usuario) ON DELETE CASCADE,
    FOREIGN KEY (id_turma) REFERENCES turmas(id_turma) ON DELETE CASCADE
);