/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.barbershop.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

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
    
    /**
     * Retorna os acessos, exceto o informado.
     * @param acesso a retirar
     * @return conjunto de acessos exceto o informado
     */
    public static Acesso[] exceto(Acesso acesso)
    {
        Collection<Acesso> acessos = new ArrayList<>(Arrays.asList(values()));
        acessos.remove(acesso);
        return acessos.toArray(new Acesso[0]);
    }
    
}
