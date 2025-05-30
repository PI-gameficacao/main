/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.teste_pi;

/**
 *
 * @author 24.00357-3
 */
public class Sala {
    private int idSala;
    private String nome;
    private int idMateria;
    private int idUsuario; // Quem criou a sala

    public Sala(int idSala, String nome, int idMateria, int idUsuario) {
        this.idSala = idSala;
        this.nome = nome;
        this.idMateria = idMateria;
        this.idUsuario = idUsuario;
    }

    public Sala(String nome, int idMateria, int idUsuario) {
        this.nome = nome;
        this.idMateria = idMateria;
        this.idUsuario = idUsuario;
    }

    public int getIdSala() {
        return idSala;
    }

    public String getNome() {
        return nome;
    }

    public int getIdMateria() {
        return idMateria;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    @Override
    public String toString() {
        return "Sala{" +
                "idSala=" + idSala +
                ", nome='" + nome + '\'' +
                ", idMateria=" + idMateria +
                ", idUsuario=" + idUsuario +
                '}';
    }
}
