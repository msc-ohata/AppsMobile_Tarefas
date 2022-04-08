package com.example.aula_2022_03_25.model;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Personagem implements Serializable {
    private String nome;
    private String nascimento;
    private String altura;
    private int id = 0;

    public Personagem(String nome, String nascimento, String altura){
        this.nome = nome;
        this.nascimento = nascimento;
        this.altura = altura;
    }

    public Personagem(){

    }

    public void setNome(String nome){this.nome = nome;}
    public void setNascimento(String nascimento){this.nascimento = nascimento;}
    public void setAltura(String altura){this.altura = altura;}
    public String getNome(){return nome;}
    public String getNascimento(){return nascimento;}
    public String getAltura(){return altura;}

    @NonNull
    @Override
    public String toString(){return nome;}
    public void setId(int id){this.id = id;}
    public int getId(){return id;}
    public boolean IdValido(){return id > 0;}
}
