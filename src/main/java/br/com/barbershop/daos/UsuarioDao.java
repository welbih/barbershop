/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.barbershop.daos;

import br.com.barbershop.enums.Acesso;
import br.com.barbershop.modelo.Usuario;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 * classe Dao associada ao Usuario
 * @author Sniper
 */
@Stateless
public class UsuarioDao extends AbstractDao<Usuario>{

    @PersistenceContext
    private EntityManager manager;
    
    /**
     * construtor passando o tipo de classe para o construtor pai.
     */
    public UsuarioDao() {
        super(Usuario.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return getManager();
    }

    /**
     * verifica se o usuário é autenticavel ou não.
     * @param email para realizar a verificação
     * @param senha para realizar a verificação 
     * @return true ou false.
     */
    public boolean autendicado(String email, String senha) {

            TypedQuery<Usuario> query = getEntityManager().createQuery("select u from "
                    + "Usuario u where u.email = :pEmail "
                    + "and u.senha = :pSenha", Usuario.class)
                                .setParameter("pEmail", email)
                                .setParameter("pSenha", senha);

            return !query.getResultList().isEmpty();
    }
    
    /**
     * retorna um usuário por email
     * @param email a ser buscado 
     * @return usuario
     */
    public Usuario porEmail(String email) {
        TypedQuery<Usuario> query = getEntityManager().createQuery("select u from "
                + "Usuario u where u.email = :pEmail"
                , Usuario.class)
                .setParameter("pEmail", email);
        
        return query.getSingleResult();
    }
    
    /**
     * verifica se existe um email no banco 
     * @param email a ser verificado 
     * @return true ou false
     */
    public boolean existeEmail(String email) {
        Query query = getEntityManager().createQuery("select u from "
                + "Usuario u where u.email = :pEmail", Usuario.class)
                                        .setParameter("pEmail", email);
        return !query.getResultList().isEmpty();
    }
    
    /**
     * verifica se já existe um cpf no banco 
     * @param cpf a ser verificado 
     * @return true ou false
     */
    public boolean existeCpf(String cpf) {
        Query query = getEntityManager().createQuery("select u from "
                + "Usuario u where u.cpf = :pCpf", Usuario.class)
                                        .setParameter("pCpf", cpf);
        return !query.getResultList().isEmpty();
    }
    
    /**
     * retorna uma lista de usuário de acordo com o tipo de acesso.
     * @param acesso a ser buscado 
     * @return lista de usuários
     */
    public List<Usuario> porAcesso(Acesso acesso) {
        Query query = getEntityManager().createQuery("select u from "
                + "Usuario u where u.acesso = :acesso", Usuario.class)
                                    .setParameter("acesso", acesso);
        return query.getResultList();
    }
    
    /**
     * retorna um entityManagaer
     * @return EntityManager.
     */
    public EntityManager getManager() {
        return manager;
    }
    
}
