/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.barbershop.daos;

import br.com.barbershop.enums.TipoPagamento;
import br.com.barbershop.modelo.Atendimento;
import br.com.barbershop.modelo.Usuario;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * Classe Dao associada ao atendimento.
 * @author Sniper
 */
@Stateless
public class AtendimentoDao extends AbstractDao<Atendimento>{

    @PersistenceContext
    private EntityManager manager;
    
    /**
     * construtor passando o tipo da classe para a classe pai.
     */
    public AtendimentoDao() {
        super(Atendimento.class);
    }

    /**
     * retorna o valor total das vendas de acordo com data inicial e final
     * @param dataInicial a ser filtrada
     * @param dataFinal a ser filtrada
     * @return valor total desse período.
     */
    public BigDecimal totalVendas(LocalDate dataInicial, LocalDate dataFinal) {
        TypedQuery<BigDecimal> query = getEntityManager().createQuery("select sum(a.valorTotal) from Atendimento a "
                    + "where data between :dataInicial and :dataFinal", BigDecimal.class)
                                .setParameter("dataInicial", dataInicial)
                                .setParameter("dataFinal", dataFinal);
        
        return query.getSingleResult();
    }
    
    /**
     * Uma lista de atendimento dos barbeiros de acordo com a data e usuário
     * @param dataInicial para o filtro 
     * @param dataFinal para o filtro 
     * @param usuario para o filtro 
     * @return lista de atendimento para esse usuário. 
     */
    public List<Atendimento> porDataEBarbeiro(LocalDate dataInicial, LocalDate dataFinal, Usuario usuario) {
        TypedQuery<Atendimento> query = getEntityManager().createQuery("select a from Atendimento a "
                    + "where data between :dataInicial and :dataFinal and a.usuario = :usuario", Atendimento.class)
                                .setParameter("dataInicial", dataInicial)
                                .setParameter("dataFinal", dataFinal)
                                .setParameter("usuario", usuario);
        
        return query.getResultList();
    }
    
    /**
     * retorna o total das vendas de acordo com usuário.
     * @param dataInicial para o filtro 
     * @param dataFinal para o filtro
     * @param usuario a ser filtrado 
     * @return valor total das vendas.
     */
    public BigDecimal totalVendas(LocalDate dataInicial, LocalDate dataFinal, Usuario usuario) {
        TypedQuery<BigDecimal> query = getEntityManager().createQuery("select sum(a.valorTotal) from Atendimento a "
                    + "where data between :dataInicial and :dataFinal and a.usuario = :usuario", BigDecimal.class)
                                .setParameter("dataInicial", dataInicial)
                                .setParameter("dataFinal", dataFinal)
                                .setParameter("usuario", usuario);
        
        return query.getSingleResult();
    }
    
    /**
     * retorna o valor total por tipo de pagamento
     * @param dataInicial a ser filtrada
     * @param dataFinal a ser filtrada 
     * @param tipo a ser filtrado
     * @return valor total para esse tipo. 
     */
    public BigDecimal porDataETipoPagamento(LocalDate dataInicial, LocalDate dataFinal, TipoPagamento tipo) {
        TypedQuery<BigDecimal> query = getEntityManager().createQuery("select sum(a.valorTotal) from Atendimento a "
                    + "where data between :dataInicial and :dataFinal and a.tipoPagamento = :tipo", BigDecimal.class)
                                .setParameter("dataInicial", dataInicial)
                                .setParameter("dataFinal", dataFinal)
                                .setParameter("tipo", tipo);
        
        return query.getSingleResult();
    }
    
    /**
     * retorna o valor total por data, usuário e tipo de pagamento 
     * @param dataInicial a ser filtrada
     * @param dataFinal a ser filtrada
     * @param tipo a ser filtrada
     * @param usuario a ser filtrado
     * @return  valor total de acordo com os parametros. 
     */
    public BigDecimal porDataETipoPagamentoEUsuario(LocalDate dataInicial, LocalDate dataFinal, TipoPagamento tipo, Usuario usuario) {
        TypedQuery<BigDecimal> query = getEntityManager().createQuery("select sum(a.valorTotal) from Atendimento a "
                    + "where data between :dataInicial and :dataFinal and a.tipoPagamento = :tipo and a.usuario = :usuario", BigDecimal.class)
                                .setParameter("dataInicial", dataInicial)
                                .setParameter("dataFinal", dataFinal)
                                .setParameter("tipo", tipo)
                                .setParameter("usuario", usuario);
        
        return query.getSingleResult();
    }
    
    /**
     * retorna uma lista de atendimento de acordo com as datas.
     * @param dataInicial a ser filtrada
     * @param dataFinal a ser filtrada
     * @return lista de atendimentos.
     */
    public List<Atendimento> porData(LocalDate dataInicial, LocalDate dataFinal) {
        TypedQuery<Atendimento> query = getEntityManager().createQuery("select a from Atendimento a "
                    + "where data between :dataInicial and :dataFinal", Atendimento.class)
                                .setParameter("dataInicial", dataInicial)
                                .setParameter("dataFinal", dataFinal);
        
        return query.getResultList();
    }
    /**
     * retorna um entity manager.
     * @return entityManager.
     */
    @Override
    protected EntityManager getEntityManager() {
        return getManager();
    }

    /**
     * retorna um entityManager.
     * @return entityManager.
     */
    public EntityManager getManager() {
        return manager;
    }
    
}
