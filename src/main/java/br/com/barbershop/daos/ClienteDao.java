/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.barbershop.daos;

import br.com.barbershop.modelo.Cliente;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * classe Dao associada ao cliente 
 * @author Sniper
 */
@Stateless
public class ClienteDao extends AbstractDao<Cliente>{

    @PersistenceContext
    private EntityManager manager;
    
    /**
     * construtor passando a classe cliente para o construtor pai.
     */
    public ClienteDao() {
        super(Cliente.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return getManager();
    }
    /**
     * retorna um EntityManager.
     * @return EntityManager
     */
    public EntityManager getManager() {
        return manager;
    }
    
    /**
     * veririca se já existe um email no banco de dados.
     * @param email a ser verificado
     * @return true ou false
     */
    public boolean existeEmail(String email) {
        Query query = getEntityManager().createQuery("select c from "
                + "Cliente c where c.email = :pEmail", Cliente.class)
                                        .setParameter("pEmail", email);
        return !query.getResultList().isEmpty();
    }
    
    /**
     * se já existe um número de celular no banco.
     * @param celular a ser pesquisado 
     * @return true ou false.
     */
    public boolean porCelular(String celular) {
        Query query = getEntityManager().createQuery("select c from "
                + "Cliente c where c.celular = :celular", Cliente.class)
                                        .setParameter("celular", celular);
        return !query.getResultList().isEmpty();
    }
    
}
