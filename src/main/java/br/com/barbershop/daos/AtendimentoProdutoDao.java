/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.barbershop.daos;

import br.com.barbershop.modelo.AtendimentoProduto;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * classe associada ao produto realizado atendimento.
 * @author Sniper
 */
@Stateless
public class AtendimentoProdutoDao extends AbstractDao<AtendimentoProduto>{

    @PersistenceContext
    private EntityManager manager;
    
    /**
     * construtor passando o tipo de classe para a classe pai.
     */
    public AtendimentoProdutoDao() {
        super(AtendimentoProduto.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return getManager();
    }

    /**
     * retorna um EntityManager
     * @return EntityManager.
     */
    public EntityManager getManager() {
        return manager;
    }
    
    /**
     * retorna uma lista de atendimentoProduto
     * @param vendaId a ser buscado no banco
     * @return lista de atendimentoProduto
     */
    public List<AtendimentoProduto> porVenda(Long vendaId) {
        TypedQuery<AtendimentoProduto> query = getEntityManager().createQuery("select a from "
                + "AtendimentoProduto a where a.atendimento.id = :vendaId"
                , AtendimentoProduto.class)
                .setParameter("vendaId", vendaId);
        
        return query.getResultList();
    }
    
    /**
     * Retorna o lucro dos produtos dado um id do atendimento
     * @param idAtendimento a ser buscado
     * @return valor do lucro dos produtos 
     */
    public BigDecimal lucroProdutos(Long idAtendimento) {
        TypedQuery<BigDecimal> query = getEntityManager().createQuery("select sum(a.valorVenda - a.valorCusto) from AtendimentoProduto a "
                    + "where a.atendimento.id = :idAtendimento", BigDecimal.class)
                                .setParameter("idAtendimento", idAtendimento);
        
        return query.getSingleResult();
    }
    
    /**
     * retorna o valor total dos produtos dado um id do atendimento.
     * @param idAtendimento a ser calculado.
     * @return valor total dos produtos
     */
    public BigDecimal valorTotalProdutos(Long idAtendimento) {
        TypedQuery<BigDecimal> query = getEntityManager().createQuery("select sum(a.valorVenda) from AtendimentoProduto a "
                    + "where a.atendimento.id = :idAtendimento", BigDecimal.class)
                                .setParameter("idAtendimento", idAtendimento);
        
        return query.getSingleResult();
    }
    
    /**
     * retorna o valor total dos custos de venda dos produtos 
     * @param idAtendimento a ser calculado
     * @return valor total dos custos dos produtos.
     */
    public BigDecimal valorTotalCustoProdutos(Long idAtendimento) {
        TypedQuery<BigDecimal> query = getEntityManager().createQuery("select sum(a.valorCusto) from AtendimentoProduto a "
                    + "where a.atendimento.id = :idAtendimento", BigDecimal.class)
                                .setParameter("idAtendimento", idAtendimento);
        
        return query.getSingleResult();
    }
    
    /**
     * retorna o lucro de acordo com a data inicial,final e id do atendimento.
     * @param dataInicial a ser filtrada
     * @param dataFinal a ser filtrada 
     * @param idAtendimento a ser calculado
     * @return valor total do lucro.
     */
    public BigDecimal lucroPorDataEId(LocalDate dataInicial, LocalDate dataFinal, Long idAtendimento) {
        TypedQuery<BigDecimal> query = getEntityManager().createQuery("select sum(a.valorVenda - a.valorCusto) from AtendimentoProduto a "
                    + "where data between :dataInicial and :dataFinal and a.atendimento.id = :id", BigDecimal.class)
                                .setParameter("dataInicial", dataInicial)
                                .setParameter("dataFinal", dataFinal)
                                .setParameter("id", idAtendimento);
        
        return query.getSingleResult();
    }
}
