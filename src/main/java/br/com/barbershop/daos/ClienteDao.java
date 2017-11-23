/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.barbershop.daos;

import br.com.barbershop.modelo.Cliente;
import br.com.barbershop.modelo.Usuario;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Sniper
 */
@Stateless
public class ClienteDao extends AbstractDao<Cliente>{

    @PersistenceContext
    private EntityManager manager;
    
    public ClienteDao() {
        super(Cliente.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return getManager();
    }

    public EntityManager getManager() {
        return manager;
    }
    
    public boolean existeEmail(String email) {
        Query query = getEntityManager().createQuery("select c from "
                + "Cliente c where c.email = :pEmail", Cliente.class)
                                        .setParameter("pEmail", email);
        return !query.getResultList().isEmpty();
    }
    
}
