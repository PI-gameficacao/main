/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.teste_pi;

/**
 *
 * @author 25.01627-0
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {

    // Método para autenticar e retornar um objeto Usuario
    public int cadastrarUsuario(Usuario usuario) {
    String sql = "INSERT INTO usuario (nome, senha, tipo) VALUES (?, ?, ?)";

    try (Connection conn = Conexao.conectar();
         PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

        stmt.setString(1, usuario.getNome());
        stmt.setString(2, usuario.getSenha());
        stmt.setString(3, usuario.getTipo());
        stmt.executeUpdate();

        ResultSet rs = stmt.getGeneratedKeys();
        if (rs.next()) {
            return rs.getInt(1); // Retorna o ID do usuário cadastrado
        }

    } catch (SQLException e) {
        System.err.println("Erro ao cadastrar usuário: " + e.getMessage());
    }

    return -1; // Retorna -1 se houver erro
}
    
    public Usuario autenticarUsuario(String nome, String senha) {
    String sql = "SELECT id_usuario, nome, tipo FROM usuario WHERE nome = ? AND senha = ?";

    try (Connection conn = Conexao.conectar();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, nome);
        stmt.setString(2, senha);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            return new Usuario(rs.getInt("id_usuario"), rs.getString("nome"), senha, rs.getString("tipo"));
        }

    } catch (SQLException e) {
        System.err.println("Erro ao autenticar usuário: " + e.getMessage());
    }

    return null; // Retorna null se não encontrar o usuário
}
    
    public void removerUsuario(int idUsuario) {
    String sql = "DELETE FROM usuario WHERE id_usuario = ?";

    try (Connection conn = Conexao.conectar();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, idUsuario);
        int rowsAffected = stmt.executeUpdate();

        if (rowsAffected > 0) {
            System.out.println("Usuário removido com sucesso!");
        } else {
            System.out.println("Erro: Usuário não encontrado.");
        }

    } catch (SQLException e) {
        System.err.println("Erro ao remover usuário: " + e.getMessage());
    }
}

}
