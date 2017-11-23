/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.barbershop.daos;

import br.com.barbershop.modelo.AtendimentoProduto;
import br.com.barbershop.modelo.AtendimentoServico;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Sniper
 */
@Stateless
public class AtendimentoProdutoDao extends AbstractDao<AtendimentoProduto>{

    @PersistenceContext
    private EntityManager manager;
    
    public AtendimentoProdutoDao() {
        super(AtendimentoProduto.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return getManager();
    }

    public EntityManager getManager() {
        return manager;
    }
    
    public List<AtendimentoProduto> porVenda(Long vendaId) {
        TypedQuery<AtendimentoProduto> query = getEntityManager().createQuery("select a from "
                + "AtendimentoProduto a where a.venda.id = :vendaId"
                , AtendimentoProduto.class)
                .setParameter("vendaId", vendaId);
        
        return query.getResultList();
    }
}
