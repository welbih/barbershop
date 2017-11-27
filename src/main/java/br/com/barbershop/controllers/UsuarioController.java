/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.barbershop.controllers;

import br.com.barbershop.daos.UsuarioDao;
import br.com.barbershop.enums.Acesso;
import br.com.barbershop.modelo.Usuario;
import br.com.barbershop.security.Senhas;
import br.com.barbershop.web.JSF;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Sniper
 */
@Named
@ViewScoped
public class UsuarioController implements Serializable{
    
    private Usuario usuario;
    private String senha, cpf, email, nome;
    private Acesso acesso;
    
    private List<Usuario> usuarios;
    
    @Inject
    private UsuarioDao usuarioDao;
    
    UsuarioController() {
        setUsuario(new Usuario());
        setUsuarios(new ArrayList<>());
    }
    
    public void filtrar()
    {
        if (getNome() == null && getCpf() == null
                    && getAcesso() == null) {
            setUsuarios(getUsuarioDao().findAll());
        }
        else {
            List<Usuario> filtro = new ArrayList<>();
            for (Usuario usuario : getUsuarioDao().findAll())
                if ((getNome() == null || usuario.getNome().
                        toLowerCase().contains(getNome().
                                toLowerCase())) && (getCpf() ==
                        null || usuario.getCpf().contains(
                                getCpf())) && (getAcesso() ==
                        null || usuario.getAcesso().equals(getAcesso()))){
                    filtro.add(usuario);
                }
            setUsuarios(filtro);
        }
    }
    
    public String salvar() {
        if(getUsuarioDao().existeEmail(getUsuario().getEmail()) && getUsuario().getId() == 0) 
            JSF.addValidationError("O sistema já possui um usuário com esse E-mail.");
        
        if(getUsuarioDao().existeCpf(getUsuario().getCpf()) && getUsuario().getId() == null) 
            JSF.addValidationError("O sistema já possui um usuário com esse CPF.");

        if(JSF.hasErrors()) 
            return null;
        
        if(getUsuario().getId() == null) {
            setSenha(Senhas.criptografar(getUsuario().getCpf().replaceAll("\\.", "").replaceAll("-", "")));
            getUsuario().setSenha(getSenha());
            getUsuarioDao().create(getUsuario());
            FacesContext.getCurrentInstance().getExternalContext()
                .getFlash().setKeepMessages(true);
            JSF.addSuccessMessage("Usuário salvo com sucesso.");
            return "usuarios?faces-redirect=true";
        } else {
            getUsuarioDao().edit(getUsuario());
            FacesContext.getCurrentInstance().getExternalContext()
                .getFlash().setKeepMessages(true);
            JSF.addSuccessMessage("Usuário editado com sucesso.");
            return "usuarios?faces-redirect=true";  
        }
    }
    
    public String remover(Usuario usuario) {
        getUsuarioDao().remove(usuario);
        FacesContext.getCurrentInstance().getExternalContext()
                .getFlash().setKeepMessages(true);
        JSF.addSuccessMessage("Usuário removido com sucesso.");
        return "usuarios?faces-redirect=true";
    }
    
    public boolean camposPreenchidos() {
        return true ? getNome() != null 
                    || getCpf() != null 
                    || getAcesso() != null : false;
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

    public UsuarioDao getUsuarioDao() {
        return usuarioDao;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Acesso getAcesso() {
        return acesso;
    }

    public void setAcesso(Acesso acesso) {
        this.acesso = acesso;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
    
    public boolean isEditando() {
        return getUsuario().getId() != null;
    }
}
