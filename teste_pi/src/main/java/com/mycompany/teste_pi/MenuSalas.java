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
    private int idUsuario;
    private boolean isAdmin;
    private JComboBox<String> comboMaterias;
    private JTextArea txtSalas;
    private JButton btnCriarMateria, btnCriarSala, btnEntrarSala, btnVoltar;
    private List<String> salasDisponiveis;

    public MenuSalas(int idUsuario, boolean isAdmin) {
        this.idUsuario = idUsuario;
        this.isAdmin = isAdmin;

        setTitle(isAdmin ? "Administração - Todas as Salas" : "Menu de Salas");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        JLabel lblMensagem = new JLabel(isAdmin ? "Todas as Salas do Sistema:" : "Suas salas:");
        add(lblMensagem);

        txtSalas = new JTextArea(10, 30);
        txtSalas.setEditable(false);
        add(new JScrollPane(txtSalas));

        JLabel lblMaterias = new JLabel(isAdmin ? "Matérias disponíveis:" : "Selecione uma matéria:");
        add(lblMaterias);

        comboMaterias = new JComboBox<>();
        add(comboMaterias);

        carregarMaterias();

        if (isAdmin) {
            btnCriarMateria = new JButton("Criar Matéria");
            add(btnCriarMateria);
            btnCriarMateria.addActionListener(e -> criarMateria());
        }

        btnCriarSala = new JButton("Criar Sala");
        add(btnCriarSala);
        btnCriarSala.addActionListener(e -> criarSala());

        btnEntrarSala = new JButton("Entrar na Sala");
        add(btnEntrarSala);
        btnEntrarSala.addActionListener(e -> entrarNaSala());

        btnVoltar = new JButton("Voltar");
        add(btnVoltar);
        btnVoltar.addActionListener(e -> {
            new LoginUsuario();
            dispose();
        });

        carregarSalas();
        setVisible(true);
    }

    private void carregarMaterias() {
        MateriaDAO materiaDAO = new MateriaDAO();
        List<Materia> materias = isAdmin ? materiaDAO.listarMateriasAdmin() : materiaDAO.listarMateriasDisponiveis();

        comboMaterias.removeAllItems();

        System.out.println("Buscando matérias para preencher o combo");

        if (materias.isEmpty()) {
            System.err.println("Nenhuma matéria disponível.");
        } else {
            for (Materia materia : materias) {
                System.out.println("Adicionando matéria no combo: " + materia.getNome());
                comboMaterias.addItem(materia.getNome());
            }
        }

        comboMaterias.revalidate();
        comboMaterias.repaint();
    }

    private void carregarSalas() {
        SalaDAO salaDAO = new SalaDAO();
        salasDisponiveis = salaDAO.listarSalasPorUsuario(idUsuario, isAdmin);

        txtSalas.setText("");

        System.out.println("Carregando salas para usuário ID: " + idUsuario + " (Admin: " + isAdmin + ")");

        if (salasDisponiveis.isEmpty()) {
            txtSalas.append("Nenhuma sala encontrada.\n");
            System.err.println("Nenhuma sala foi carregada para o usuário com ID: " + idUsuario);
        } else {
            for (String sala : salasDisponiveis) {
                System.out.println("Exibindo sala: " + sala);
                txtSalas.append(sala + "\n");
            }
        }
    }

    private void criarMateria() {
        String nomeMateria = JOptionPane.showInputDialog("Digite o nome da matéria:");
        if (nomeMateria != null && !nomeMateria.isEmpty()) {
            MateriaDAO materiaDAO = new MateriaDAO();
            boolean sucesso = materiaDAO.adicionarMateria(nomeMateria, idUsuario);

            if (sucesso) {
                JOptionPane.showMessageDialog(this, "Matéria criada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                carregarMaterias(); // Atualiza o combo box com a nova matéria
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao criar matéria!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Erro: Nome de matéria inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void criarSala() {
        if (comboMaterias.getItemCount() == 0) {
            JOptionPane.showMessageDialog(this, "Erro: Nenhuma matéria disponível para criar a sala!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String nomeSala = JOptionPane.showInputDialog("Digite o nome da sala:");

        if (nomeSala != null && !nomeSala.isEmpty()) {
            String nomeMateria = (String) comboMaterias.getSelectedItem();
            int idMateria = MateriaDAO.getIdMateriaPorNome(nomeMateria);

            System.out.println("Criando sala com matéria: " + nomeMateria + ", ID matéria: " + idMateria);

            if (idMateria > 0) {
                SalaDAO salaDAO = new SalaDAO();
                salaDAO.adicionarSala(nomeSala, idMateria, idUsuario);
                JOptionPane.showMessageDialog(this, "Sala criada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                carregarSalas();
            } else {
                JOptionPane.showMessageDialog(this, "Erro: Matéria não encontrada!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Erro: Nome da sala inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void entrarNaSala() {
        String nomeSalaSelecionada = JOptionPane.showInputDialog("Digite o nome da sala para entrar:");

        if (nomeSalaSelecionada != null && !nomeSalaSelecionada.isEmpty()) {
            int idSala = SalaDAO.getIdSalaPorNome(nomeSalaSelecionada); // Agora chamando o método correto

            if (idSala > 0) {
                System.out.println("Entrando na sala: " + nomeSalaSelecionada + " (ID: " + idSala + ")");
                new MenuDeSala(idSala); // Agora entramos na sala
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Erro: Sala não encontrada!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Erro: Nome da sala inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}