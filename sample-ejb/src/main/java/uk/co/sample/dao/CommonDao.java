package uk.co.sample.dao;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;

import uk.co.sample.jpa.IfQueryParameterBinder;
import uk.co.sample.jpa.QueryBean;

public class CommonDao {

    //***** injection field *****
    @PersistenceContext(unitName = "aePersistenceUnitReadOnly")
    private EntityManager em;
    @Inject
    private Logger        logger;

    //***** constructor *****
    //***** public method *****
    public int selectIntNativeQuery(QueryBean queryBean) {
        trace(queryBean);
        Query query = em.createNativeQuery(queryBean.getQueryString());
        for (IfQueryParameterBinder binder : queryBean.getParamBinders()) {
            binder.bind(query);
        }
        return ((Number) query.getSingleResult()).intValue();
    }

    public List<?> findByNativeQueryRange(QueryBean queryBean, int rowIndex, int numberOfRowsToFetch) {
        trace(queryBean);
        Query query = em.createNativeQuery(queryBean.getQueryString());
        for (IfQueryParameterBinder binder : queryBean.getParamBinders()) {
            binder.bind(query);
        }
        query.setFirstResult(rowIndex);
        query.setMaxResults(numberOfRowsToFetch);
        return query.getResultList();
    }

    public List<?> findByNativeQuery(QueryBean queryBean) {
        trace(queryBean);
        Query query = em.createNativeQuery(queryBean.getQueryString());
        for (IfQueryParameterBinder binder : queryBean.getParamBinders()) {
            binder.bind(query);
        }
        return query.getResultList();
    }

    public EntityManager getEntityManager() {
        return em;
    }

    //***** protected method *****
    //***** private method *****
    private void trace(QueryBean queryBean) {
        logger.trace(" {} {}", queryBean.getOperator().getTracingId(), queryBean.getQueryString());
        logger.trace(" {} {}", queryBean.getOperator().getTracingId(), queryBean.getParamString());
    }

    //***** getter and setter *****

}
