/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.barbershop.controllers;

import java.io.Serializable;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author darkSniper
 */
@Named
@ViewScoped
public class ListagemController implements Serializable{
    
     /**
     * Retorna a condição da tabela estar na primeira página.
     * @param hdt tabela
     * @return false caso seja a primeira página ou true caso não seja
     */
    public boolean isPrimeiraPagina(HtmlDataTable hdt)
    {
        return hdt.getFirst() == 0;
    }
    /**
     * Retorna a condição da tabela estar na última página.
     * @param hdt tabela
     * @return false caso seja a última página ou true caso seja
     */
    public boolean isUltimaPagina(HtmlDataTable hdt)
    {
        return hdt.getFirst() + hdt.getRows() >= hdt.getRowCount();
    }
    /**
     * Avança uma tabela para a próxima página.
     * @param hdt tabela
     */
    public void avancar(HtmlDataTable hdt)
    {
        hdt.setFirst(hdt.getFirst() + hdt.getRows());
    }
    /**
     * Retrocede uma tabela para a página anterior.
     * @param hdt tabela
     */
    public void retornar(HtmlDataTable hdt)
    {
        hdt.setFirst(hdt.getFirst() - hdt.getRows());
    }
    
}
