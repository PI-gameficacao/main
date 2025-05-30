/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.teste_pi;

/**
 *
 * @author 24.00357-3
 */
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SalaDeAulaDAO {

    public boolean cadastrarAluno(String nomeAluno) {
        String sqlInsert = "INSERT INTO aluno (nome) VALUES (?)";

        System.out.println("🔍 Tentando cadastrar aluno: " + nomeAluno);

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmtInsert = conn.prepareStatement(sqlInsert)) {

            stmtInsert.setString(1, nomeAluno.trim());
            return stmtInsert.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("❌ Erro ao cadastrar aluno: " + e.getMessage());
            return false;
        }
    }

    public boolean adicionarAlunoNaSala(int idSala, String nomeAluno) {
    String sqlBuscaAluno = "SELECT id_aluno FROM aluno WHERE nome = ?";
    String sqlInsertAlunoSala = "INSERT INTO matricula (id_sala, id_aluno) VALUES (?, ?)";
    String sqlBuscaNotas = "SELECT nome_nota FROM nota WHERE id_sala = ?";
    String sqlVerificaNota = "SELECT COUNT(*) FROM nota WHERE id_aluno = ? AND id_sala = ? AND nome_nota = ?";
    String sqlInsertNotaAluno = "INSERT INTO nota (id_aluno, id_sala, nome_nota, valor) VALUES (?, ?, ?, ?)";

    System.out.println("🔍 Adicionando aluno '" + nomeAluno + "' na sala ID " + idSala);

    try (Connection conn = Conexao.conectar();
         PreparedStatement stmtBuscaAluno = conn.prepareStatement(sqlBuscaAluno)) {

        stmtBuscaAluno.setString(1, nomeAluno.trim());
        ResultSet rsAluno = stmtBuscaAluno.executeQuery();

        if (rsAluno.next()) {
            int idAluno = rsAluno.getInt("id_aluno");

            try (PreparedStatement stmtInsertAlunoSala = conn.prepareStatement(sqlInsertAlunoSala)) {
                stmtInsertAlunoSala.setInt(1, idSala);
                stmtInsertAlunoSala.setInt(2, idAluno);
                stmtInsertAlunoSala.executeUpdate();
            }

            try (PreparedStatement stmtBuscaNotas = conn.prepareStatement(sqlBuscaNotas)) {
                stmtBuscaNotas.setInt(1, idSala);
                ResultSet rsNotas = stmtBuscaNotas.executeQuery();

                try (PreparedStatement stmtVerificaNota = conn.prepareStatement(sqlVerificaNota);
                     PreparedStatement stmtInsertNotaAluno = conn.prepareStatement(sqlInsertNotaAluno)) {

                    while (rsNotas.next()) {
                        String nomeNota = rsNotas.getString("nome_nota");

                        stmtVerificaNota.setInt(1, idAluno);
                        stmtVerificaNota.setInt(2, idSala);
                        stmtVerificaNota.setString(3, nomeNota);
                        ResultSet rsVerifica = stmtVerificaNota.executeQuery();

                        if (rsVerifica.next() && rsVerifica.getInt(1) == 0) {
                            stmtInsertNotaAluno.setInt(1, idAluno);
                            stmtInsertNotaAluno.setInt(2, idSala);
                            stmtInsertNotaAluno.setString(3, nomeNota);
                            stmtInsertNotaAluno.setDouble(4, 0.0);
                            stmtInsertNotaAluno.executeUpdate();
                        }
                    }
                }
            }

            System.out.println("✅ Aluno " + nomeAluno + " adicionado e notas atribuídas corretamente!");
            return true;
        } else {
            System.err.println("🚨 Erro: Aluno não encontrado no banco!");
            return false;
        }

    } catch (SQLException e) {
        System.err.println("❌ Erro ao adicionar aluno na sala: " + e.getMessage());
        return false;
    }
}


    public List<String> listarAlunosDaSala(int idSala) {
        List<String> alunos = new ArrayList<>();
        String sql = "SELECT a.nome FROM aluno a " +
                     "JOIN matricula m ON a.id_aluno = m.id_aluno " +
                     "WHERE m.id_sala = ?";

        System.out.println("🔍 Buscando alunos na sala ID: " + idSala);

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idSala);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                alunos.add(rs.getString("nome"));
            }

        } catch (SQLException e) {
            System.err.println("❌ Erro ao listar alunos da sala: " + e.getMessage());
        }

        return alunos;
    }

    public boolean removerAlunoDaSala(int idSala, String nomeAluno) {
        String sqlBusca = "SELECT id_aluno FROM aluno WHERE nome = ?";
        String sqlDelete = "DELETE FROM matricula WHERE id_sala = ? AND id_aluno = ?";

        System.out.println("🔍 Tentando remover aluno: " + nomeAluno + " da sala ID " + idSala);

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmtBusca = conn.prepareStatement(sqlBusca)) {

            stmtBusca.setString(1, nomeAluno.trim());
            ResultSet rs = stmtBusca.executeQuery();

            if (rs.next()) {
                int idAluno = rs.getInt("id_aluno");
                System.out.println("✅ Aluno encontrado! ID: " + idAluno);

                try (PreparedStatement stmtDelete = conn.prepareStatement(sqlDelete)) {
                    stmtDelete.setInt(1, idSala);
                    stmtDelete.setInt(2, idAluno);
                    return stmtDelete.executeUpdate() > 0;
                }
            }
            return false;

        } catch (SQLException e) {
            System.err.println("❌ Erro ao remover aluno da sala: " + e.getMessage());
            return false;
        }
    }

    public boolean cadastrarNomeNotaNaSala(int idSala, String nomeNota) {
        String sqlInsert = "INSERT INTO nota (id_sala, nome_nota, valor) VALUES (?, ?, ?)";

        System.out.println("🔍 Cadastrando nome da nota '" + nomeNota + "' na sala ID " + idSala);

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmtInsert = conn.prepareStatement(sqlInsert)) {

            stmtInsert.setInt(1, idSala);
            stmtInsert.setString(2, nomeNota.trim());
            stmtInsert.setDouble(3, 0.0);
            return stmtInsert.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("❌ Erro ao cadastrar nome da nota na sala: " + e.getMessage());
            return false;
        }
    }

    public boolean atribuirNotaAosAlunos(int idSala, String nomeNota) {
        String sqlBuscaAlunos = "SELECT id_aluno FROM matricula WHERE id_sala = ?";
        String sqlInserirNotaAluno = "INSERT INTO nota (id_aluno, id_sala, nome_nota, valor) VALUES (?, ?, ?, ?)";

        System.out.println("🔍 Atribuindo nota '" + nomeNota + "' a todos os alunos da sala ID " + idSala);

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmtBuscaAlunos = conn.prepareStatement(sqlBuscaAlunos)) {

            stmtBuscaAlunos.setInt(1, idSala);
            ResultSet rs = stmtBuscaAlunos.executeQuery();

            try (PreparedStatement stmtInserirNotaAluno = conn.prepareStatement(sqlInserirNotaAluno)) {
                while (rs.next()) {
                    stmtInserirNotaAluno.setInt(1, rs.getInt("id_aluno"));
                    stmtInserirNotaAluno.setInt(2, idSala);
                    stmtInserirNotaAluno.setString(3, nomeNota.trim());
                    stmtInserirNotaAluno.setDouble(4, 0.0);
                    stmtInserirNotaAluno.executeUpdate();
                }
            }
            return true;

        } catch (SQLException e) {
            System.err.println("❌ Erro ao atribuir notas aos alunos: " + e.getMessage());
            return false;
        }
    }

    public boolean editarNotaDoAluno(int idAluno, int idSala, String nomeNota, double novoValor) {
        String sqlUpdate = "UPDATE nota SET valor = ? WHERE id_aluno = ? AND id_sala = ? AND nome_nota = ?";

        System.out.println("🔍 Editando nota '" + nomeNota + "' do aluno ID " + idAluno + " na sala ID " + idSala);

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmtUpdate = conn.prepareStatement(sqlUpdate)) {

            stmtUpdate.setDouble(1, novoValor);
            stmtUpdate.setInt(2, idAluno);
            stmtUpdate.setInt(3, idSala);
            stmtUpdate.setString(4, nomeNota.trim());
            return stmtUpdate.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("❌ Erro ao editar nota: " + e.getMessage());
            return false;
        }
    }
    
    public List<String> listarNotas(int idSala) {
    List<String> notas = new ArrayList<>();
    String sql = "SELECT a.nome, n.nome_nota, n.valor FROM nota n " +
                 "JOIN aluno a ON n.id_aluno = a.id_aluno " +
                 "WHERE n.id_sala = ?";

    try (Connection conn = Conexao.conectar();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, idSala);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            String nomeAluno = rs.getString("nome");
            String nomeNota = rs.getString("nome_nota");
            double valorNota = rs.getDouble("valor");
            notas.add(nomeAluno + " → " + nomeNota + ": " + valorNota);
        }

    } catch (SQLException e) {
        System.err.println("❌ Erro ao listar notas: " + e.getMessage());
    }

    return notas;
}

public int buscarIdAluno(String nomeAluno) {
    String sqlBusca = "SELECT id_aluno FROM aluno WHERE nome = ?";
    int idAluno = -1;

    try (Connection conn = Conexao.conectar();
         PreparedStatement stmtBusca = conn.prepareStatement(sqlBusca)) {

        stmtBusca.setString(1, nomeAluno.trim());
        ResultSet rs = stmtBusca.executeQuery();

        if (rs.next()) {
            idAluno = rs.getInt("id_aluno");
        }

    } catch (SQLException e) {
        System.err.println("❌ Erro ao buscar ID do aluno: " + e.getMessage());
    }

    return idAluno;
}
    
}