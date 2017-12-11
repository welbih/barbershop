/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.barbershop.web;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 * classe com metodos estáticos para manipulação do JSF. messages e redirect.
 * @author Sniper
 */
public class JSF {

    /**
     * Adiciona uma mensagem de erro ao contexto.
     * @param message mensagem de erro
     */
    public static void addErrorMessage(String message)
    {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                message, message);
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }
    /**
     * Adiciona uma mensagem de sucesso ao contexto.
     * @param message mensagem de texto
     */
    public static void addSuccessMessage(String message)
    {
        addSuccessMessage(message, message);
    }
    /**
     * Adiciona uma mensagem de sucesso ao contexto.
     * @param detail detalhe da mensagem
     * @param message mensagem de texto
     */
    public static void addSuccessMessage(String detail, String message)
    {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, 
                message, detail);
        FacesContext.getCurrentInstance().addMessage("successInfo", facesMsg);
    }
    /**
     * Adiciona uma mensagem de erro de validação com o contexto atual.
     * @param message mensagem de erro
     */
    public static void addValidationError(String message)
    {
        addValidationError(FacesContext.getCurrentInstance(), message);
    }
    /**
     * Adiciona uma mensagem de erro de validação.
     * @param fc contexto
     * @param message mensagem de erro
     */
    public static void addValidationError(FacesContext fc, String message)
    {
        addErrorMessage(message);
        fc.validationFailed();
    }
    /**
     * Retorna se há erros no contexto do JSF.
     * @return false caso não haja erros ou true caso haja
     */
    public static boolean hasErrors()
    {
        return FacesContext.getCurrentInstance().isValidationFailed();
    }
    /**
     * Redireciona para a URL especificada.
     * @param url URL a navegar
     */
    public static void redirect(String url)
    {
        try
        {
            FacesContext.getCurrentInstance().getExternalContext().
                    redirect(url);
        }
        catch (IOException ex)
        {
            Logger.getLogger(JSF.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
