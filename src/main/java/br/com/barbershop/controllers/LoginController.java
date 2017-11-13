/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.barbershop.controllers;

import br.com.barbershop.daos.UsuarioDao;
import br.com.barbershop.enums.Acesso;
import br.com.barbershop.modelo.Usuario;
import br.com.barbershop.security.Senha;
import br.com.barbershop.web.JSF;
import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Sniper
 */
@Named
@RequestScoped
public class LoginController implements Serializable{
    
    private Usuario usuario;
    private String email;
    private String senha;
    
    @Inject
    private UsuarioDao usuarioDao;

    @Inject
    private UsuarioLogadoController usuarioLogado;
    
    public LoginController() {
        setUsuario(new Usuario());
    }
    
    public String efetuaLogin() {
        setSenha(Senha.criptografar(getSenha()));
        boolean loginValido = usuarioDao.autendicado(getEmail(), getSenha());

        if(loginValido) {
            setUsuario(usuarioDao.porEmail(getEmail()));
            usuarioLogado.logar(getUsuario());
            if(getUsuario().getAcesso() == Acesso.BARBEIRO) {
                System.out.println("Redirecionando...");
                return "selecionarCliente?faces-redirect=true";
            } else {
                System.out.println("Redirecionando...");
                return "cadastroUsuario?faces-redirect=true";
            }
        } else {
                usuarioLogado.deslogar();
                this.usuario = new Usuario();
                JSF.addErrorMessage("E-mail ou senha incorreto");
                return "login";
        }

    }
    
    public String deslogar() {
        if(usuarioLogado.isLogado()) {
                usuarioLogado.deslogar();
                return "login?faces-redirect=true";
        }
        return"";
    }
    public Usuario getUsuario() {
        return usuario;
    }
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    
}