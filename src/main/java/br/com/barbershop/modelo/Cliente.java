/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.barbershop.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

/**
 *
 * @author Sniper
 */
@Entity
@DiscriminatorValue("CLIENTE")
public class Cliente extends Pessoa implements Serializable{
    
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.REMOVE)
    private List<Atendimento> atendimentos;

//    public Cliente() {
//        setAtendimento(new ArrayList<>());
//    }
//
//    public List<Atendimento> getAtendimento() {
//        return atendimento;
//    }
//
//    public void setAtendimento(List<Atendimento> atendimento) {
//        this.atendimento = atendimento;
//    }
//    
}
