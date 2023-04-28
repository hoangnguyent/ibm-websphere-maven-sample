package uk.co.sample.dao;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;

import uk.co.sample.entity.DemoAnnounceSeriesToc;
import uk.co.sample.jpa.AbstractDao;

/**
 * <H3>
 * DemoAnnounceSeriesTocDao.
 * </H3>
 *
 * @author AutoGenerate
 * @since 2022/05/27
 */
public class DemoAnnounceSeriesTocDao extends AbstractDao<DemoAnnounceSeriesToc> {

    @PersistenceContext(unitName = "aePersistenceUnit")
    private EntityManager em;

    @Inject
    private Logger        logger;

    // ***** constructor *****
    public DemoAnnounceSeriesTocDao() {
        super(DemoAnnounceSeriesToc.class);
    }

    public DemoAnnounceSeriesTocDao(EntityManager em, Logger logger) {
        super(DemoAnnounceSeriesToc.class);
        this.em = em;
        this.logger = logger;
    }

    // ***** public method *****
    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    //***** protected method *****
    @Override
    protected Logger getLogger() {
        return logger;
    }
    //***** call back method *****
    //***** getter and setter *****

}
