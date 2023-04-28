package uk.co.sample.dao;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;

import uk.co.sample.entity.DemoAnnounceSeries;
import uk.co.sample.jpa.AbstractDao;

/**
 * <H3>
 * DemoAnnounceSeriesDao.
 * </H3>
 *
 * @author AutoGenerate
 * @since 2022/05/27
 */
public class DemoAnnounceSeriesDao extends AbstractDao<DemoAnnounceSeries> {

    @PersistenceContext(unitName = "aePersistenceUnit")
    private EntityManager em;

    @Inject
    private Logger        logger;

    // ***** constructor *****
    public DemoAnnounceSeriesDao() {
        super(DemoAnnounceSeries.class);
    }

    public DemoAnnounceSeriesDao(EntityManager em, Logger logger) {
        super(DemoAnnounceSeries.class);
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
