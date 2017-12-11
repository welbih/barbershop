/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.barbershop.enums;

/**
 * enum com os tipos de pagamento do sistema
 * @author Sniper
 */
public enum TipoPagamento {
    CARTAO_CREDITO("Cartão de Crédito"),
    CARTAO_DEBITO("Cartão de Débito"),
    DINHEIRO("Dinheiro");
    
    private String nome;

    /**
     * construtor recebendo um nome 
     * @param nome a ser atribuido.
     */
    TipoPagamento(String nome) {
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
     * atribui um nome
     * @param nome a ser atribuido 
     */
    public void setNome(String nome) {
        this.nome = nome;
    }
    
}
