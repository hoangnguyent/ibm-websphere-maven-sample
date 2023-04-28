package uk.co.sample.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;

import org.slf4j.Logger;

public abstract class AbstractDao<T> {

    private final Class<T> entityClass;

    //***** injection field *****
    //***** constructor *****

    public AbstractDao(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    //***** public method *****

    public abstract EntityManager getEntityManager();

    public void create(T entity) {
        getLogger().trace(entityClass.getName());
        getEntityManager().persist(entity);
    }

    public void edit(T entity) {
        getLogger().trace(entityClass.getName());
        getEntityManager().merge(entity);
    }

    public void remove(T entity) {
        getLogger().trace(entityClass.getName());
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public boolean exists(Object id) {
        return find(id) != null;
    }

    public T find(Object id) {
        getLogger().trace("{} : id = {}", entityClass.getName(), id);
        T result = getEntityManager().find(entityClass, id, LockModeType.OPTIMISTIC); //LockModeType.OPTIMISTIC_FORCE_INCREMENT);
        if (null == result) {
            getLogger().trace("nothing");
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    public List<T> findAll() {
        @SuppressWarnings("rawtypes")
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public int selectIntNativeQuery(QueryBean queryBean) {
        trace(queryBean);
        Query query = getEntityManager().createNativeQuery(queryBean.getQueryString());
        for (IfQueryParameterBinder binder : queryBean.getParamBinders()) {
            binder.bind(query);
        }
        return ((Number) query.getSingleResult()).intValue();
    }

    public long selectLongNativeQuery(QueryBean queryBean) {
        trace(queryBean);
        Query query = getEntityManager().createNativeQuery(queryBean.getQueryString());
        for (IfQueryParameterBinder binder : queryBean.getParamBinders()) {
            binder.bind(query);
        }
        return ((Number) query.getSingleResult()).longValue();
    }

    @SuppressWarnings("unchecked")
    public List<T> findByNativeQuery(QueryBean queryBean) {
        trace(queryBean);
        Query query = getEntityManager().createNativeQuery(queryBean.getQueryString(), entityClass);
        for (IfQueryParameterBinder binder : queryBean.getParamBinders()) {
            binder.bind(query);
        }
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<T> findByNativeQueryRange(QueryBean queryBean, int firstResult, int maxResult) {
        trace(queryBean);
        Query query = getEntityManager().createNativeQuery(queryBean.getQueryString(), entityClass);
        for (IfQueryParameterBinder binder : queryBean.getParamBinders()) {
            binder.bind(query);
        }
        query.setMaxResults(maxResult);
        query.setFirstResult(firstResult);
        return query.getResultList();
    }

    public int selectIntQuery(QueryBean queryBean) {
        trace(queryBean);
        Query query = getEntityManager().createQuery(queryBean.getQueryString());
        for (IfQueryParameterBinder binder : queryBean.getParamBinders()) {
            binder.bind(query);
        }
        return ((Number) query.getSingleResult()).intValue();
    }

    @SuppressWarnings("unchecked")
    public List<T> findByQuery(QueryBean queryBean) {
        trace(queryBean);
        Query query = getEntityManager().createQuery(queryBean.getQueryString(), entityClass);
        for (IfQueryParameterBinder binder : queryBean.getParamBinders()) {
            binder.bind(query);
        }
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<T> findByQueryRange(QueryBean queryBean, int firstResult, int maxResult) {
        trace(queryBean);
        Query query = getEntityManager().createQuery(queryBean.getQueryString(), entityClass);
        for (IfQueryParameterBinder binder : queryBean.getParamBinders()) {
            binder.bind(query);
        }
        query.setMaxResults(maxResult);
        query.setFirstResult(firstResult);
        return query.getResultList();
    }

    public CriteriaBuilder getCriteriaBuilder() {
        return getEntityManager().getCriteriaBuilder();
    }

    //***** protected method *****

    protected abstract Logger getLogger();

    //***** private method *****

    private void trace(QueryBean queryBean) {
        getLogger().trace(" {} {}", queryBean.getOperator().getTracingId(), queryBean.getQueryString());
        getLogger().trace(" {} {}", queryBean.getOperator().getTracingId(), queryBean.getParamString());
    }

    //***** getter and setter *****

}
