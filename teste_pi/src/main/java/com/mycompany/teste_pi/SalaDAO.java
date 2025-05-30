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

public class SalaDAO {

    public List<String> listarSalasPorUsuario(int idUsuario, boolean isAdmin) {
    List<String> salas = new ArrayList<>();
    String sql;

    if (isAdmin) {
        sql = "SELECT nome FROM sala"; // Administradores veem todas as salas
    } else {
        sql = "SELECT nome FROM sala WHERE id_usuario = ?"; // Professores veem apenas as salas que criaram
    }

    try (Connection conn = Conexao.conectar();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        if (!isAdmin) {
            stmt.setInt(1, idUsuario);
        }

        ResultSet rs = stmt.executeQuery();

        System.out.println("Consultando salas para usuário ID: " + idUsuario + " (Admin: " + isAdmin + ")");

        while (rs.next()) {
            String nomeSala = rs.getString("nome");
            salas.add(nomeSala);
            System.out.println("Sala encontrada: " + nomeSala);
        }

    } catch (SQLException e) {
        System.err.println("Erro ao listar salas do usuário: " + e.getMessage());
    }

    if (salas.isEmpty()) {
        System.err.println("Nenhuma sala encontrada para o usuário com ID: " + idUsuario);
    }

    return salas;
}






    public void adicionarSala(String nomeSala, int idMateria, int idUsuario) {
    String sql = "INSERT INTO sala (nome, id_materia, id_usuario) VALUES (?, ?, ?)";

    try (Connection conn = Conexao.conectar();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, nomeSala);
        stmt.setInt(2, idMateria);
        stmt.setInt(3, idUsuario);

        int rowsAffected = stmt.executeUpdate();

        if (rowsAffected > 0) {
            System.out.println("✅ Sala criada com sucesso! Nome: " + nomeSala);
        } else {
            System.err.println("❌ Erro ao adicionar a sala.");
        }

    } catch (SQLException e) {
        System.err.println("Erro ao adicionar sala: " + e.getMessage());
    }
}
    
    public static int getIdSalaPorNome(String nomeSala) {
    String sql = "SELECT id_sala FROM sala WHERE LOWER(nome) = LOWER(?)";

    try (Connection conn = Conexao.conectar();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        System.out.println("Buscando ID da sala com nome: '" + nomeSala + "'");
        stmt.setString(1, nomeSala.trim().toLowerCase());

        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            int idSala = rs.getInt("id_sala");
            System.out.println("Sala encontrada! ID: " + idSala);
            return idSala;
        } else {
            System.err.println("Nenhuma sala encontrada com o nome: " + nomeSala);
        }

    } catch (SQLException e) {
        System.err.println("Erro ao recuperar ID da sala: " + e.getMessage());
    }

    return -1; // Retorna -1 caso não encontre a sala
}

    
}

