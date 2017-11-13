/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.barbershop.daos;

import br.com.barbershop.modelo.AtendimentoServico;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Sniper
 */
@Stateless
public class AtendimentoServicoDao extends AbstractDao<AtendimentoServico>{

    @PersistenceContext
    private EntityManager manager;
    
    public AtendimentoServicoDao() {
        super(AtendimentoServico.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return getManager();
    }

    public EntityManager getManager() {
        return manager;
    }
    
}
