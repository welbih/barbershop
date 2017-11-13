/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.barbershop.enums;

/**
 *
 * @author Sniper
 */
public enum Acesso {
    ADMINISTRADOR("Administrador"),
    BARBEIRO("Barbeiro");
    
    private String nome;
    
    Acesso(String nome) {
        setNome(nome);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    
}
