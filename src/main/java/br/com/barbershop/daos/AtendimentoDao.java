/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.barbershop.daos;

import br.com.barbershop.modelo.Atendimento;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Sniper
 */
@Stateless
public class AtendimentoDao extends AbstractDao<Atendimento>{

    @PersistenceContext
    private EntityManager manager;
    
    public AtendimentoDao() {
        super(Atendimento.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return getManager();
    }

    public EntityManager getManager() {
        return manager;
    }
    
}
