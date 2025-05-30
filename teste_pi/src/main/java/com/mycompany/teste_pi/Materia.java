/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.teste_pi;

/**
 *
 * @author 24.00357-3
 */
public class Materia {
    private int idMateria;
    private String nome;
    private int idUsuario; // Professor vinculado à matéria

    public Materia(int idMateria, String nome, int idUsuario) {
        this.idMateria = idMateria;
        this.nome = nome;
        this.idUsuario = idUsuario;
    }

    public String getNome(){
        return nome;
    }
}
