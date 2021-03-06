/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.barbershop.enums;

/**
 * enum com a enumeração de quantidade.
 * @author Sniper
 */
public enum Quantidade {
    Null("Selecione a Quantidade"),UM("1"), DOIS("2"), TRES("3"), QUATRO("4"), CINCO("5"), 
    SEIS("6"), SETE("7"), OITO("8"), NOVE("9"), DEZ("10");
    
    private String nome;

    Quantidade(String nome) {
        this.nome = nome;
    }
    
    /**
     * retorna o nome da quantidade
     * @return nome
     */
    public String getNome() {
        return nome;
    }
    /**
     * seta o nome 
     * @param nome a ser atribuído. 
     */
    public void setNome(String nome) {
        this.nome = nome;
    }
    
}
