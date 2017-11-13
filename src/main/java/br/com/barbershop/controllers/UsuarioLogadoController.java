/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.barbershop.controllers;

import br.com.barbershop.modelo.Usuario;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Sniper
 */
@Named
@SessionScoped
public class UsuarioLogadoController implements Serializable{
    
    private Usuario usuario;
    
    UsuarioLogadoController() {
        setUsuario(new Usuario());
    }
    
    public void logar(Usuario usuario) {
            this.usuario = usuario;
    }

    public void deslogar() {
            this.usuario = null;
    }

    public boolean isLogado() {
            return usuario != null;
    }
    
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}