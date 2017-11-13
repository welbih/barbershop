/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.barbershop.modelo;

import java.io.Serializable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 *
 * @author Sniper
 */
@Entity
@DiscriminatorValue("CLIENTE")
public class Cliente extends Pessoa implements Serializable{
    
}
