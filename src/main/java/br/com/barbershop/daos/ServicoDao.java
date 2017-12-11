/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.barbershop.daos;

import br.com.barbershop.modelo.Servico;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * classe dao associada ao Serviço
 * @author Sniper
 */
@Stateless
public class ServicoDao extends AbstractDao<Servico>{

    @PersistenceContext
    private EntityManager manager;
    
    /**
     * construtor passando o tipo da classe para o contrutor pai.
     */
    public ServicoDao() {
        super(Servico.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return getManager();
    }

    /**
     * retorna um EntityManager
     * @return  EntityManager
     */
    public EntityManager getManager() {
        return manager;
    }
    
    /**
     * verifica se o nome já existe no banco de dados.
     * @param nome a ser verificado 
     * @return true ou false.
     */
    public boolean porNome(String nome) {
        Query query = getEntityManager().createQuery("select s from "
                + "Servico s where s.nome = :nome", Servico.class)
                                        .setParameter("nome", nome);
        return !query.getResultList().isEmpty();
    }
}
