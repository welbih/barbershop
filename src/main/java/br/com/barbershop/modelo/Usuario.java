/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.barbershop.modelo;

import br.com.barbershop.enums.Acesso;
import br.com.barbershop.security.Senhas;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 *
 * @author Sniper
 */
@Entity
@DiscriminatorValue("USUARIO")
public class Usuario extends Pessoa implements Serializable{

    @Column(length = 14)
    private String cpf;
    private String senha;
    @Enumerated(EnumType.STRING)
    private Acesso acesso;
    
    public static int TAMANHO_SENHA = 8;
    
    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Acesso getAcesso() {
        return acesso;
    }

    public void setAcesso(Acesso acesso) {
        this.acesso = acesso;
    }
    
    public boolean isAdministrador() {
        return getAcesso().ADMINISTRADOR.equals(Acesso.ADMINISTRADOR);
    }
    /**
     * Gera uma nova senha encriptada.
     * @param novaSenha
     */
    public void gerarSenha(String novaSenha)
    {
        setSenha(Senhas.criptografar(novaSenha));
    }
}
