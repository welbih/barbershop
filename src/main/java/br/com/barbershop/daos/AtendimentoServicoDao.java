/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.barbershop.daos;

import br.com.barbershop.modelo.AtendimentoServico;
import java.math.BigDecimal;
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
    public List<AtendimentoServico> porVenda(Long vendaId) {
        TypedQuery<AtendimentoServico> query = getEntityManager().createQuery("select a from "
                + "AtendimentoServico a where a.atendimento.id = :vendaId"
                , AtendimentoServico.class)
                .setParameter("vendaId", vendaId);
        
        return query.getResultList();
    }
    
    public BigDecimal totalServicos(Long idAtendimento) {
        TypedQuery<BigDecimal> query = getEntityManager().createQuery("select sum(a.valorServico) from AtendimentoServico a "
                    + "where a.atendimento.id = :idAtendimento", BigDecimal.class)
                                .setParameter("idAtendimento", idAtendimento);
        
        return query.getSingleResult();
    }
}
