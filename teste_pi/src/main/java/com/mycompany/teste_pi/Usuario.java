/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.teste_pi;

/**
 *
 * @author 25.01627-0
 */
public class Usuario {
    private int idUsuario;
    private String nome;
    private String senha;
    private String tipo;

    public Usuario(int idUsuario, String nome, String senha, String tipo) {
        this.idUsuario = idUsuario;
        this.nome = nome;
        this.senha = senha;
        this.tipo = tipo;
    }

    public int getIdUsuario() { return idUsuario; } // Agora retorna corretamente
    public String getNome() { return nome; }
    public String getSenha() { return senha; }
    public String getTipo() { return tipo; }

    public void setNome(String nome) { this.nome = nome; }
    public void setSenha(String senha) { this.senha = senha; }
    public void setTipo(String tipo) { this.tipo = tipo; }
}
