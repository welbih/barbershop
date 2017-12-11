/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.barbershop.controllers;

import br.com.barbershop.enums.Acesso;
import br.com.barbershop.enums.Quantidade;
import br.com.barbershop.enums.Select;
import br.com.barbershop.enums.TipoPagamento;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

/**
 * classe controladora das enuns do sistema. 
 * @author Sniper
 */
@Named
@ApplicationScoped
public class EnunsController {
    
    /**
     * acessos do sistemas
     * @return array dos acessos da enum.
     */
    public Acesso[] getAcesso() {
        return Acesso.values();
    }
    
    /**
     * tipo de pagamentos
     * @return um array com os tipos de pagamentos.
     */
    public TipoPagamento[] getTipoPagamento() {
        return TipoPagamento.values();
    }
    
    /**
     * quantidades
     * @return um array com a quantidade da enum
     */
    public Quantidade[] getQuantidade() {
        return Quantidade.values();
    }
    
    /**
     * Select 
     * @return um array com os valores da enum. 
     */
    public Select[] getSelect() {
        return Select.values();
    }
}
