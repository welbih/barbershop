/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.barbershop.daos;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;
import org.hibernate.Criteria;

/**
 *
 * @author Sniper
 */
abstract public class AbstractDao<T> {
    
    private Class<T> entityClass;
    
    protected abstract EntityManager getEntityManager();
    
    public AbstractDao(Class<T> entityClass) {
        setEntityClass(entityClass);
    }
    
    public void create(T entity) {
        getEntityManager().persist(entity);
    }
    
    public void edit(T entity) {
        getEntityManager().merge(entity);
    }
    
    public T find (Object id) {
        return getEntityManager().find(getEntityClass(), id);
    }
    
    public List<T> findAll() {
        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(getEntityClass()));
        return getEntityManager().createQuery(cq).getResultList();
    }
    
    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public Class<T> getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(Class<T> entityClass) {
        this.entityClass = entityClass;
    }
    
}
