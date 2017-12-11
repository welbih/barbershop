/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.barbershop.enums;

/**
 * enum com um select para compôr um um select na interface
 * @author Sniper
 */
public enum Select {
    Null("Selecione");
    
    private String nome;

    /**
     * construtor recebendo um nome
     * @param nome a ser atribuído.
     */
    Select(String nome) {
        setNome(nome);
    }

    /**
     * retorna um nome 
     * @return nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * atribui o nome passado em parametro
     * @param nome a ser atribuído. 
     */
    public void setNome(String nome) {
        this.nome = nome;
    }
    
}
