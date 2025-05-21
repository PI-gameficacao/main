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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CadastroUsuario extends JFrame{
    private JTextField txtNome;
    private JPasswordField txtSenha;
    private JComboBox<String> comboTipo;
    private JButton btnCadastrar;

    public CadastroUsuario() {
        setTitle("Cadastro de Usuário");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 2, 10, 10));

        add(new JLabel("Nome:"));
        txtNome = new JTextField();
        add(txtNome);

        add(new JLabel("Senha:"));
        txtSenha = new JPasswordField();
        add(txtSenha);

        add(new JLabel("Tipo:"));
        comboTipo = new JComboBox<>(new String[]{"Professor", "Administrador"});
        add(comboTipo);

        btnCadastrar = new JButton("Cadastrar");
        add(btnCadastrar);

        btnCadastrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cadastrarUsuario();
            }
        });
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void cadastrarUsuario() {
    String nome = txtNome.getText();
    String senha = new String(txtSenha.getPassword());
    String tipo = (String) comboTipo.getSelectedItem();

    if (nome.isEmpty() || senha.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Preencha todos os campos!");
        return;
    }

    Usuario usuario = new Usuario(0, nome, senha, tipo);
    UsuarioDAO usuarioDAO = new UsuarioDAO();
    int idUsuario = usuarioDAO.cadastrarUsuario(usuario);

    JOptionPane.showMessageDialog(this, "Cadastro realizado com sucesso!");
}

    public static void main(String[] args) {
        new CadastroUsuario();
    }
}

