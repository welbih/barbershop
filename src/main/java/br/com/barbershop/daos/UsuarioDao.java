/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.barbershop.daos;

import br.com.barbershop.modelo.Usuario;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author Sniper
 */
@Stateless
public class UsuarioDao extends AbstractDao<Usuario>{

    @PersistenceContext
    private EntityManager manager;
    
    public UsuarioDao() {
        super(Usuario.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return getManager();
    }

    public boolean autendicado(String email, String senha) {

            TypedQuery<Usuario> query = getEntityManager().createQuery("select u from "
                    + "Usuario u where u.email = :pEmail "
                    + "and u.senha = :pSenha", Usuario.class)
                                .setParameter("pEmail", email)
                                .setParameter("pSenha", senha);

            return !query.getResultList().isEmpty();
    }
    
    public Usuario porEmail(String email) {
        TypedQuery<Usuario> query = getEntityManager().createQuery("select u from "
                + "Usuario u where u.email = :pEmail"
                , Usuario.class)
                .setParameter("pEmail", email);
        
        return query.getSingleResult();
    }
    
    public boolean existeEmail(String email) {
        Query query = getEntityManager().createQuery("select u from "
                + "Usuario u where u.email = :pEmail", Usuario.class)
                                        .setParameter("pEmail", email);
        return !query.getResultList().isEmpty();
    }
    
    public boolean existeCpf(String cpf) {
        Query query = getEntityManager().createQuery("select u from "
                + "Usuario u where u.cpf = :pCpf", Usuario.class)
                                        .setParameter("pCpf", cpf);
        return !query.getResultList().isEmpty();
    }
    
    public EntityManager getManager() {
        return manager;
    }
    
}
