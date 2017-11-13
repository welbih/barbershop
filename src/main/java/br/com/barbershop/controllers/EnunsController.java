/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.barbershop.controllers;

import br.com.barbershop.enums.Acesso;
import br.com.barbershop.enums.Quantidade;
import br.com.barbershop.enums.Select;
import br.com.barbershop.enums.SelectServico;
import br.com.barbershop.enums.TipoPagamento;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

/**
 *
 * @author Sniper
 */
@Named
@ApplicationScoped
public class EnunsController {
    
    public Acesso[] getAcesso() {
        return Acesso.values();
    }
    
    public TipoPagamento[] getTipoPagamento() {
        return TipoPagamento.values();
    }
    
    public Quantidade[] getQuantidade() {
        return Quantidade.values();
    }
    
    public SelectServico[] getSelectServico() {
        return SelectServico.values();
    }
    
    public Select[] getSelect() {
        return Select.values();
    }
}
