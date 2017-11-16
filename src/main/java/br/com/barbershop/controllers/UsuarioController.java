/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.barbershop.controllers;

import br.com.barbershop.daos.UsuarioDao;
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
    private String senha;
    
    private List<Usuario> usuarios;
    
    @Inject
    private UsuarioDao usuarioDao;
    
    UsuarioController() {
        setUsuario(new Usuario());
        setUsuarios(new ArrayList<>());
    }
    
    public void filtrar()
    {
        System.out.println("Nome: " + getUsuario().getNome());
        System.out.println("CPF: " + getUsuario().getCpf());
        System.out.println("Acesso: " + getUsuario().getAcesso());
        if (getUsuario().getNome() == null && getUsuario().getCpf() == null
                    && getUsuario().getAcesso() == null) {
            System.out.println("Passando pelo if...");
            setUsuarios(getUsuarioDao().findAll());
        }
        else {
            System.out.println("Passando pelo else");
            List<Usuario> filtro = new ArrayList<>();
            for (Usuario usuario : getUsuarioDao().findAll())
                if ((getUsuario().getNome() == null || usuario.getNome().
                        toLowerCase().contains(getUsuario().getNome().
                                toLowerCase())) && (getUsuario().getCpf() ==
                        null || usuario.getCpf().contains(getUsuario().
                                getCpf())) && (getUsuario().getAcesso() ==
                        null || usuario.getAcesso().equals(getUsuario().
                                getAcesso()))){
                    System.out.println("Dentro do if do for...");
                    filtro.add(usuario);
                }
            setUsuarios(filtro);
        }
    }
    
    public String salvar() {
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
        return true ? getUsuario().getNome() != null 
                    || getUsuario().getCpf() != null 
                    || getUsuario().getAcesso() != null : false;
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

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
    
    public boolean isEditando() {
        return getUsuario().getId() != null;
    }
}
