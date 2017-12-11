/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.barbershop.daos;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;

/**
 * classe abstrata de DAO genérico.
 * @author Sniper
 */
abstract public class AbstractDao<T> {
    
    private Class<T> entityClass;
    
    protected abstract EntityManager getEntityManager();
    
    /**
     * construtor iniciando uma entityClass.
     * @param entityClass 
     */
    public AbstractDao(Class<T> entityClass) {
        setEntityClass(entityClass);
    }
    
    /**
     * salvar um Objeto no banco.
     * @param entity a ser salva
     */
    public void create(T entity) {
        getEntityManager().persist(entity);
    }
    
    /**
     * edita um Objeto no banco 
     * @param entity a ser editada
     */
    public void edit(T entity) {
        getEntityManager().merge(entity);
    }
    
    /**
     * buscar um objeto por id.
     * @param id a ser buscado 
     * @return Objeto 
     */
    public T find (Object id) {
        return getEntityManager().find(getEntityClass(), id);
    }
    
    /**
     * retorna uma lista de objetos.
     * @return List de objetos retornado do banco.
     */
    public List<T> findAll() {
        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(getEntityClass()));
        return getEntityManager().createQuery(cq).getResultList();
    }
    
    /**
     * remove um objeto do bando.
     * @param entity a ser removido.
     */
    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    //<editor-fold defaultstate="collapsed" desc="Getters/Setters">
    public Class<T> getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(Class<T> entityClass) {
        this.entityClass = entityClass;
    }
    //</editor-fold>
}
