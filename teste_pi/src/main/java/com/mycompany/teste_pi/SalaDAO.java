/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.teste_pi;

/**
 *
 * @author 24.00357-3
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SalaDAO {

    // Método para adicionar uma sala vinculada a uma matéria existente
    public void adicionarSala(String nomeSala, int idMateria) {
    // Confirma que a matéria existe antes de criar a sala
    if (!verificarMateriaExiste(idMateria)) {
        System.err.println("Erro: Matéria não encontrada! Sala não pode ser criada.");
        return;
    }

    String sql = "INSERT INTO sala (nome, id_materia) VALUES (?, ?)";

    try (Connection conn = Conexao.conectar();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, nomeSala);
        stmt.setInt(2, idMateria);
        stmt.executeUpdate();

        System.out.println("Sala adicionada com sucesso!");

    } catch (SQLException e) {
        System.err.println("Erro ao adicionar sala: " + e.getMessage());
    }
}



    // Método para verificar se uma matéria existe antes de vincular salas a ela
   private boolean verificarMateriaExiste(int idMateria) {
    String sql = "SELECT id_materia FROM materia WHERE id_materia = ?";
   
    try (Connection conn = Conexao.conectar();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, idMateria);
        ResultSet rs = stmt.executeQuery();
        return rs.next(); // Se encontrou a matéria, retorna verdadeiro

    } catch (SQLException e) {
        System.err.println("Erro ao verificar matéria: " + e.getMessage());
    }

    return false;
}

    
    public List<String> listarSalasPorProfessor(int idUsuario) {
    List<String> salas = new ArrayList<>();
    String sql = "SELECT s.nome FROM sala s " +
                 "JOIN materia m ON s.id_materia = m.id_materia " +
                 "WHERE m.id_usuario = ?";

    try (Connection conn = Conexao.conectar();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, idUsuario);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            salas.add(rs.getString("nome"));
        }

    } catch (SQLException e) {
        System.err.println("Erro ao listar salas do professor: " + e.getMessage());
    }

    return salas;
}
    
    public List<String> listarTodasAsSalas() {
    List<String> salas = new ArrayList<>();
    String sql = "SELECT nome FROM sala";

    try (Connection conn = Conexao.conectar();
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            salas.add(rs.getString("nome"));
        }

    } catch (SQLException e) {
        System.err.println("Erro ao listar todas as salas: " + e.getMessage());
    }

    return salas;
}
    
    
}
