/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.barbershop.daos;

import br.com.barbershop.modelo.AtendimentoProduto;
import br.com.barbershop.modelo.Usuario;
import java.math.BigDecimal;
import java.time.LocalDate;
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
    
    public BigDecimal lucroProdutos(Long idAtendimento) {
        TypedQuery<BigDecimal> query = getEntityManager().createQuery("select sum(a.valorVenda - a.valorCusto) from AtendimentoProduto a "
                    + "where a.venda.id = :idAtendimento", BigDecimal.class)
                                .setParameter("idAtendimento", idAtendimento);
        
        return query.getSingleResult();
    }
    
    public BigDecimal valorTotalProdutos(Long idAtendimento) {
        TypedQuery<BigDecimal> query = getEntityManager().createQuery("select sum(a.valorVenda) from AtendimentoProduto a "
                    + "where a.venda.id = :idAtendimento", BigDecimal.class)
                                .setParameter("idAtendimento", idAtendimento);
        
        return query.getSingleResult();
    }
    
    public BigDecimal valorTotalCustoProdutos(Long idAtendimento) {
        TypedQuery<BigDecimal> query = getEntityManager().createQuery("select sum(a.valorCusto) from AtendimentoProduto a "
                    + "where a.venda.id = :idAtendimento", BigDecimal.class)
                                .setParameter("idAtendimento", idAtendimento);
        
        return query.getSingleResult();
    }
    
    public BigDecimal lucroPorDataEId(LocalDate dataInicial, LocalDate dataFinal, Long idAtendimento) {
        TypedQuery<BigDecimal> query = getEntityManager().createQuery("select sum(a.valorVenda - a.valorCusto) from AtendimentoProduto a "
                    + "where data between :dataInicial and :dataFinal and a.venda.id = :id", BigDecimal.class)
                                .setParameter("dataInicial", dataInicial)
                                .setParameter("dataFinal", dataFinal)
                                .setParameter("id", idAtendimento);
        
        return query.getSingleResult();
    }
}
