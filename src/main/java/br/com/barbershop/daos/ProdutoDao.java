/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.barbershop.daos;

import br.com.barbershop.modelo.Produto;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * classe Dao associada ao produto. 
 * @author Sniper
 */
@Stateless
public class ProdutoDao extends AbstractDao<Produto>{

    @PersistenceContext
    private EntityManager manager;
    
    /**
     * construtor passando o tipo de classe para o contrutor pai
     */
    public ProdutoDao() {
        super(Produto.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return getManager();
    }

    /**
     * retorna um entityManager.
     * @return EntityManager.
     */
    public EntityManager getManager() {
        return manager;
    }
    
    /**
     * veririca se já existe um produto com esse nome
     * @param nome a ser verificado 
     * @return true ou false.
     */
    public boolean porNome(String nome) {
        Query query = getEntityManager().createQuery("select p from "
                + "Produto p where p.nome = :nome", Produto.class)
                                        .setParameter("nome", nome);
        return !query.getResultList().isEmpty();
    }
}
