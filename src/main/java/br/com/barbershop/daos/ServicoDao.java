/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.barbershop.daos;

import br.com.barbershop.modelo.Servico;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Sniper
 */
@Stateless
public class ServicoDao extends AbstractDao<Servico>{

    @PersistenceContext
    private EntityManager manager;
    
    public ServicoDao() {
        super(Servico.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return getManager();
    }

    public EntityManager getManager() {
        return manager;
    }
    
    public boolean porNome(String nome) {
        Query query = getEntityManager().createQuery("select s from "
                + "Servico s where s.nome = :nome", Servico.class)
                                        .setParameter("nome", nome);
        return !query.getResultList().isEmpty();
    }
}
