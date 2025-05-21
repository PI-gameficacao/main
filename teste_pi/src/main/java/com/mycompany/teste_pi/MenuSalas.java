/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.teste_pi;

/**
 *
 * @author 25.01627-0
 */

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MenuSalas extends JFrame {
    private JButton btnVoltar, btnCriarSala, btnCriarMateria;
    private JTextArea txtSalas;
    private JComboBox<String> comboMaterias;
    private boolean isAdmin;

    public MenuSalas(int idUsuario, boolean isAdmin) {
    this.isAdmin = isAdmin;
   
    setTitle(isAdmin ? "Administração - Todas as Salas" : "Menu de Salas");
    setSize(400, 350);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new FlowLayout());

    JLabel lblMensagem = new JLabel(isAdmin ? "Todas as Salas do Sistema:" : "Suas salas:");
    add(lblMensagem);

    txtSalas = new JTextArea(10, 30);
    txtSalas.setEditable(false);
    add(new JScrollPane(txtSalas));

    if (isAdmin) {
        btnCriarMateria = new JButton("Criar Matéria");
        add(btnCriarMateria);
        btnCriarMateria.addActionListener(e -> criarMateria());
    } else {
        JLabel lblMaterias = new JLabel("Selecione uma matéria:");
        add(lblMaterias);
       
        comboMaterias = new JComboBox<>();
        carregarMaterias(isAdmin); // Passamos corretamente o argumento booleano
        add(comboMaterias);
    }

    btnCriarSala = new JButton("Criar Sala");
    add(btnCriarSala);
    btnCriarSala.addActionListener(e -> criarSala(idUsuario));

    btnVoltar = new JButton("Voltar");
    add(btnVoltar);
    btnVoltar.addActionListener(e -> {
        new LoginUsuario();
        dispose();
    });
    setLocationRelativeTo(null);
    carregarSalas(idUsuario);
    setVisible(true);
}


       private void carregarMaterias(boolean isAdmin) { // Agora recebe um argumento booleano
    MateriaDAO materiaDAO = new MateriaDAO();
   
    // Se for administrador, mostra todas as matérias; se for professor, mostra apenas matérias disponíveis
    List<Materia> materias = isAdmin ? materiaDAO.listarMateriasAdmin() : materiaDAO.listarMateriasDisponiveis();

    comboMaterias.removeAllItems();
   
    if (materias.isEmpty()) {
        System.err.println("Erro: Nenhuma matéria encontrada!");
    } else {
        for (Materia materia : materias) {
            comboMaterias.addItem(materia.getNome());
        }
    }
}


    private void carregarSalas(int idUsuario) {
        SalaDAO salaDAO = new SalaDAO();
        List<String> salas = isAdmin ? salaDAO.listarTodasAsSalas() : salaDAO.listarSalasPorProfessor(idUsuario);

        txtSalas.setText("");
        for (String sala : salas) {
            txtSalas.append(sala + "\n");
        }
    }

    private void criarMateria() {
    String nomeMateria = JOptionPane.showInputDialog("Digite o nome da matéria:");
    if (nomeMateria != null && !nomeMateria.isEmpty()) {
        MateriaDAO materiaDAO = new MateriaDAO();
        int idMateria = materiaDAO.adicionarMateria(nomeMateria); // Agora chama corretamente
        if (idMateria != -1) {
            JOptionPane.showMessageDialog(this, "Matéria criada com sucesso: " + nomeMateria);
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao criar matéria!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}

    private void criarSala(int idUsuario) {
    MateriaDAO materiaDAO = new MateriaDAO();
    List<Materia> materias = materiaDAO.listarMateriasAdmin(); // Lista matérias cadastradas pelo admin

    if (materias.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Erro: Nenhuma matéria cadastrada! Sala não pode ser criada.", "Erro", JOptionPane.ERROR_MESSAGE);
        return;
    }

    String[] opcoesMaterias = materias.stream().map(Materia::getNome).toArray(String[]::new);
    String nomeMateriaSelecionada = (String) JOptionPane.showInputDialog(
        this,
        "Escolha a matéria para vincular à sala:",
        "Criar Sala",
        JOptionPane.PLAIN_MESSAGE,
        null,
        opcoesMaterias,
        opcoesMaterias[0]
    );

    if (nomeMateriaSelecionada != null) {
        // Busca o ID da matéria corretamente
        int idMateria = materiaDAO.getIdMateriaPorNome(nomeMateriaSelecionada);

        if (idMateria <= 0) { // Se o ID for inválido, exibe erro
            JOptionPane.showMessageDialog(this, "Erro: Matéria não encontrada no banco de dados!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String nomeSala = JOptionPane.showInputDialog("Digite o nome da sala:");
        if (nomeSala != null && !nomeSala.isEmpty()) {
            SalaDAO salaDAO = new SalaDAO();
            salaDAO.adicionarSala(nomeSala, idMateria);
            carregarSalas(idUsuario);
        } else {
            JOptionPane.showMessageDialog(this, "Erro: Nome da sala inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}

}


