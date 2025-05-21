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

public class LoginUsuario extends JFrame {
    private JTextField txtNome;
    private JPasswordField txtSenha;
    private JButton btnEntrar;

    public LoginUsuario() {
        setTitle("Login do Usuário");
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 2, 10, 10));

        add(new JLabel("Nome:"));
        txtNome = new JTextField();
        add(txtNome);

        add(new JLabel("Senha:"));
        txtSenha = new JPasswordField();
        add(txtSenha);

        btnEntrar = new JButton("Entrar");
        add(btnEntrar);

        btnEntrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                autenticarUsuario();
            }
        });
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void autenticarUsuario() {
    String nome = txtNome.getText();
    String senha = new String(txtSenha.getPassword());

    UsuarioDAO usuarioDAO = new UsuarioDAO();
    Usuario usuario = usuarioDAO.autenticarUsuario(nome, senha);

    if (usuario != null) {
        JOptionPane.showMessageDialog(this, "Login bem-sucedido!");

        String tipoUsuario = usuario.getTipo().trim(); // Remove espaços extras
        if (tipoUsuario.equalsIgnoreCase("Professor")) {
            new MenuSalas(usuario.getIdUsuario(), false); // Professor vê apenas suas salas
        } else if (tipoUsuario.equalsIgnoreCase("Administrador")) { // Agora verifica "Administrador" corretamente
            new MenuSalas(usuario.getIdUsuario(), true); // Admin vê todas as salas e pode criar matérias
        } else {
            JOptionPane.showMessageDialog(this, "Erro: Tipo de usuário desconhecido!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        dispose(); // Fecha a tela de login corretamente
    } else {
        JOptionPane.showMessageDialog(this, "Nome ou senha inválidos!");
    }
}




    public static void main(String[] args) {
        new LoginUsuario();
    }
}
