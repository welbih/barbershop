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
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

/**
 *
 * @author darkSniper
 */
@Singleton
@Startup
public class InicializacaoController {
    
    @Inject
    private UsuarioDao usuarioDao;
    
    @PostConstruct
    public void iniciar() {
        this.criarUsuarios();
    }
    
    private void criarUsuarios(){
        String adm  = "310.733.171-61";
        boolean adminstrador = usuarioDao.existeCpf(adm);
        
        if(!adminstrador) {
            Usuario usuario = new Usuario();
            usuario.setAcesso(Acesso.ADMINISTRADOR);
            usuario.setCelular("(61) 99999-8888");
            usuario.setCpf(adm);
            usuario.setEmail("admin@admin.com");
            usuario.setNome("Administrador do Sistema");
            
            String senha = "admin";
            usuario.setSenha(Senha.criptografar(senha));
            
            usuarioDao.create(usuario);
        }
        
        String barb  = "828.065.655-37";
        boolean barbeiro = usuarioDao.existeCpf(barb);
        
        if(!barbeiro) {
            Usuario usuario = new Usuario();
            usuario.setAcesso(Acesso.BARBEIRO);
            usuario.setCelular("(61) 95555-7777");
            usuario.setCpf(barb);
            usuario.setEmail("barber@barber.com");
            usuario.setNome("Barbeiro do Sistema");
            
            String senha = "barbershop";
            usuario.setSenha(Senha.criptografar(senha));
            usuarioDao.create(usuario);
        }
               
    }
    
}
