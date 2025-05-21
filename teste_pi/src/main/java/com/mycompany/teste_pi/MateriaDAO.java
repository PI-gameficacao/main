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

public class MateriaDAO {

    // Método para adicionar uma matéria vinculada ao professor
    public int adicionarMateria(String nomeMateria) {
    int idAdmin = getIdAdministrador(); // Obtém um ID válido de admin
   
    if (idAdmin <= 0) { // Confirma que o ID do admin é válido
        System.err.println("Erro: Nenhum administrador encontrado! Matéria não pode ser criada.");
        return -1;
    }

    String sql = "INSERT INTO materia (nome, id_usuario) VALUES (?, ?)";

    try (Connection conn = Conexao.conectar();
         PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

        stmt.setString(1, nomeMateria);
        stmt.setInt(2, idAdmin);
        stmt.executeUpdate();

        ResultSet rs = stmt.getGeneratedKeys();
        if (rs.next()) {
            return rs.getInt(1);
        }

    } catch (SQLException e) {
        System.err.println("Erro ao adicionar matéria: " + e.getMessage());
    }

    return -1;
}
    
   private int getIdAdministrador() {
    String sql = "SELECT id_usuario FROM usuario WHERE tipo = 'Administrador' LIMIT 1";

    try (Connection conn = Conexao.conectar();
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        if (rs.next()) {
            return rs.getInt("id_usuario");
        }

    } catch (SQLException e) {
        System.err.println("Erro ao buscar administrador: " + e.getMessage());
    }

    return -1; // Retorna -1 se nenhum admin for encontrado
} 
    


// Método para listar matérias cadastradas por um administrador
public List<Materia> listarMateriasAdmin() {
    List<Materia> materias = new ArrayList<>();
    String sql = "SELECT id_materia, nome FROM materia";

    try (Connection conn = Conexao.conectar();
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            materias.add(new Materia(rs.getInt("id_materia"), rs.getString("nome"), 0));
        }

    } catch (SQLException e) {
        System.err.println("Erro ao listar matérias: " + e.getMessage());
    }

    return materias;
}


public List<Materia> listarMateriasDisponiveis() {
    List<Materia> materias = new ArrayList<>();
    String sql = "SELECT id_materia, nome FROM materia WHERE id_materia IN (SELECT id_materia FROM sala)";

    try (Connection conn = Conexao.conectar();
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            materias.add(new Materia(rs.getInt("id_materia"), rs.getString("nome"), 0));
        }

    } catch (SQLException e) {
        System.err.println("Erro ao listar matérias disponíveis: " + e.getMessage());
    }

    return materias;
}


// Método para recuperar o ID da matéria com base no nome
public int getIdMateriaPorNome(String nomeMateria) {
    String sql = "SELECT id_materia FROM materia WHERE nome = ?";

    try (Connection conn = Conexao.conectar();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, nomeMateria);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            return rs.getInt("id_materia"); // Retorna um ID válido
        }

    } catch (SQLException e) {
        System.err.println("Erro ao recuperar ID da matéria: " + e.getMessage());
    }

    return -1; // Retorna -1 se não encontrar a matéria
}

private boolean verificarUsuarioExiste(int idUsuario) {
    String sql = "SELECT id_usuario FROM usuario WHERE id_usuario = ?";

    try (Connection conn = Conexao.conectar();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, idUsuario);
        ResultSet rs = stmt.executeQuery();
        return rs.next(); // Se encontrou o usuário, retorna verdadeiro

    } catch (SQLException e) {
        System.err.println("Erro ao verificar usuário: " + e.getMessage());
    }

    return false;
}



}

