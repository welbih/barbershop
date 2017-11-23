/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.barbershop.daos;

import br.com.barbershop.modelo.Atendimento;
import br.com.barbershop.modelo.Usuario;
import java.math.BigDecimal;
import java.time.LocalDate;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

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
    
    public BigDecimal totalVendas(LocalDate dataInicial, LocalDate dataFinal) {
        TypedQuery<BigDecimal> query = getEntityManager().createQuery("select sum(a.valorTotal) from Atendimento a "
                    + "where data between :dataInicial and :dataFinal", BigDecimal.class)
                                .setParameter("dataInicial", dataInicial)
                                .setParameter("dataFinal", dataFinal);
        
        return query.getSingleResult();
    }
    
    public BigDecimal totalVendas(LocalDate dataInicial, LocalDate dataFinal, Usuario usuario) {
        TypedQuery<BigDecimal> query = getEntityManager().createQuery("select sum(a.valorTotal) from Atendimento a "
                    + "where data between :dataInicial and :dataFinal and a.usuario = :usuario", BigDecimal.class)
                                .setParameter("dataInicial", dataInicial)
                                .setParameter("dataFinal", dataFinal)
                                .setParameter("usuario", usuario);
        
        return query.getSingleResult();
    }
    
}
