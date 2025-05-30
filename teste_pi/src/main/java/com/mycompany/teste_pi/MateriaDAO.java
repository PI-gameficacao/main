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

public class MateriaDAO {

    public boolean adicionarMateria(String nomeMateria, int idUsuario) {
    String sql = "INSERT INTO materia (nome, id_usuario) VALUES (?, ?)";

    try (Connection conn = Conexao.conectar();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, nomeMateria);
        stmt.setInt(2, idUsuario); // Agora passando o ID do usuário dinamicamente

        int rowsAffected = stmt.executeUpdate();

        if (rowsAffected > 0) {
            System.out.println("Matéria '" + nomeMateria + "' adicionada com sucesso pelo usuário ID " + idUsuario);
            return true;
        } else {
            System.err.println("Falha ao adicionar matéria.");
            return false;
        }

    } catch (SQLException e) {
        System.err.println("Erro ao adicionar matéria: " + e.getMessage());
        return false;
    }
}


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
    String sql = "SELECT id_materia, nome FROM materia";

    try (Connection conn = Conexao.conectar();
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        System.out.println("Buscando matérias disponíveis...");

        while (rs.next()) {
            int idMateria = rs.getInt("id_materia");
            String nomeMateria = rs.getString("nome");

            System.out.println("Matéria encontrada: ID=" + idMateria + ", Nome=" + nomeMateria);

            materias.add(new Materia(idMateria, nomeMateria, 1));
        }

    } catch (SQLException e) {
        System.err.println("Erro ao buscar matérias: " + e.getMessage());
    }

    if (materias.isEmpty()) {
        System.err.println("Nenhuma matéria foi encontrada no banco!");
    }

    return materias;
}

    public static int getIdMateriaPorNome(String nomeMateria) {
    String sql = "SELECT id_materia FROM materia WHERE LOWER(nome) = LOWER(?)";

    try (Connection conn = Conexao.conectar();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        System.out.println("Buscando ID da matéria com nome: '" + nomeMateria + "'");
        stmt.setString(1, nomeMateria.trim().toLowerCase()); // Converte para minúsculas e remove espaços extras

        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            int idMateria = rs.getInt("id_materia");
            System.out.println("Matéria encontrada! ID: " + idMateria);
            return idMateria;
        } else {
            System.err.println("Nenhuma matéria encontrada com o nome: " + nomeMateria);
        }

    } catch (SQLException e) {
        System.err.println("Erro ao recuperar ID da matéria: " + e.getMessage());
    }

    return -1;
}

}


