/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.barbershop.modelo;

import br.com.barbershop.enums.Acesso;
import br.com.barbershop.security.Senhas;
import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import org.hibernate.validator.constraints.br.CPF;

/**
 * classe associada ao usuário
 * @author Sniper
 */
@Entity
@DiscriminatorValue("USUARIO")
public class Usuario extends Pessoa implements Serializable{

    @CPF(message = "CPF inválido, informe um CPF válido.")
    @Column(length = 14)
    private String cpf;
    private String senha;
    @Enumerated(EnumType.STRING)
    private Acesso acesso;
    
    public static int TAMANHO_SENHA = 8;
    
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.REMOVE)
    private List<Atendimento> atendimentos;
    
    //<editor-fold defaultstate="collapsed" desc="Getters/Setters">
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
    //</editor-fold>
    /**
     * verifica se é  um administrador.
     * @return true ou false
     */
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
