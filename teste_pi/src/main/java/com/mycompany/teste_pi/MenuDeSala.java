/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.teste_pi;

/**
 *
 * @author 24.00357-3
 */
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MenuDeSala extends JFrame {
    private int idSala;
    private JTextArea txtAlunos;
    private JButton btnRemoverAluno, btnAdicionarAluno, btnCadastrarNota, btnEditarNota, btnVoltar;

    public MenuDeSala(int idSala) {
        this.idSala = idSala;

        setTitle("Gerenciamento da Sala");
        setSize(450, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new FlowLayout());

        JLabel lblMensagem = new JLabel("Alunos e Notas da Sala:");
        add(lblMensagem);

        txtAlunos = new JTextArea(12, 40);
        txtAlunos.setEditable(false);
        add(new JScrollPane(txtAlunos));

        btnAdicionarAluno = new JButton("Adicionar Aluno");
        add(btnAdicionarAluno);
        btnAdicionarAluno.addActionListener(e -> adicionarAluno());

        btnRemoverAluno = new JButton("Remover Aluno");
        add(btnRemoverAluno);
        btnRemoverAluno.addActionListener(e -> removerAluno());

        btnCadastrarNota = new JButton("Cadastrar Nota");
        add(btnCadastrarNota);
        btnCadastrarNota.addActionListener(e -> cadastrarNotaNaSala());

        btnEditarNota = new JButton("Editar Nota");
        add(btnEditarNota);
        btnEditarNota.addActionListener(e -> editarNotaAluno());

        btnVoltar = new JButton("Voltar");
        add(btnVoltar);
        btnVoltar.addActionListener(e -> dispose());

        carregarAlunosENotas();
        setVisible(true);
    }

    private void carregarAlunosENotas() {
        SalaDeAulaDAO salaDAO = new SalaDeAulaDAO();
        List<String> alunos = salaDAO.listarAlunosDaSala(idSala);
        List<String> notas = salaDAO.listarNotas(idSala);

        txtAlunos.setText("");

        for (String aluno : alunos) {
            String notasAluno = notas.stream()
                .filter(nota -> nota.startsWith(aluno + " →"))
                .reduce("", (acc, nota) -> acc + nota.replace(aluno + " → ", "") + " | ");

            txtAlunos.append(aluno + " → " + notasAluno + "\n");
        }

        txtAlunos.revalidate();
        txtAlunos.repaint();
    }

    private void adicionarAluno() {
        String nomeAluno = JOptionPane.showInputDialog("Digite o nome do aluno:");

        if (nomeAluno != null && !nomeAluno.isEmpty()) {
            SalaDeAulaDAO salaDAO = new SalaDeAulaDAO();
            boolean alunoCadastrado = salaDAO.cadastrarAluno(nomeAluno);

            if (alunoCadastrado) {
                boolean sucesso = salaDAO.adicionarAlunoNaSala(idSala, nomeAluno);

                if (sucesso) {
                    JOptionPane.showMessageDialog(this, "Aluno adicionado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    carregarAlunosENotas();
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao adicionar aluno à sala!", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao cadastrar aluno!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Nome inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removerAluno() {
        String nomeAluno = JOptionPane.showInputDialog("Digite o nome do aluno para remover:");

        if (nomeAluno != null && !nomeAluno.isEmpty()) {
            SalaDeAulaDAO salaDAO = new SalaDeAulaDAO();
            boolean sucesso = salaDAO.removerAlunoDaSala(idSala, nomeAluno);

            if (sucesso) {
                JOptionPane.showMessageDialog(this, "Aluno removido com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                carregarAlunosENotas();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao remover aluno!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Nome inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cadastrarNotaNaSala() {
        String nomeNota = JOptionPane.showInputDialog("Digite o nome da nota:");

        if (nomeNota != null && !nomeNota.isEmpty()) {
            SalaDeAulaDAO salaDAO = new SalaDeAulaDAO();
            boolean sucesso = salaDAO.cadastrarNomeNotaNaSala(idSala, nomeNota);

            if (sucesso) {
                boolean atribuicaoOk = salaDAO.atribuirNotaAosAlunos(idSala, nomeNota);

                if (atribuicaoOk) {
                    JOptionPane.showMessageDialog(this, "Nota cadastrada e atribuída aos alunos!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    carregarAlunosENotas();
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao atribuir nota aos alunos!", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao cadastrar nome da nota!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Nome da nota inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editarNotaAluno() {
        String nomeAluno = JOptionPane.showInputDialog("Digite o nome do aluno:");
        String nomeNota = JOptionPane.showInputDialog("Digite o nome da nota:");
        double novoValor = Double.parseDouble(JOptionPane.showInputDialog("Digite o novo valor da nota:"));

        SalaDeAulaDAO salaDAO = new SalaDeAulaDAO();
        int idAluno = salaDAO.buscarIdAluno(nomeAluno);

        if (idAluno != -1) {
            boolean sucesso = salaDAO.editarNotaDoAluno(idAluno, idSala, nomeNota, novoValor);

            if (sucesso) {
                JOptionPane.showMessageDialog(this, "Nota editada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                carregarAlunosENotas();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao editar nota!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Aluno não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}