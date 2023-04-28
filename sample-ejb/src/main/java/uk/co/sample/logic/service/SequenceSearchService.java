package uk.co.sample.logic.service;

import java.math.BigDecimal;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import uk.co.sample.constant.BaseConstant;
import uk.co.sample.jpa.QueryBean;
import uk.co.sample.jpa.SQLBuilder;
import uk.co.sample.security.Operator;
import uk.co.sample.util.ProcessingTimeUtil;

@Stateless
public class SequenceSearchService {

    private static final int    LENGTH_10                     = 10;
    private static final int    LENGTH_12                     = 12;
    private static final int    LENGTH_15                     = 15;
    private static final String SQL_NEXTVAL                   = "SELECT %s.nextval FROM dual";
    private static final String SQ_DEMO_ANNOUNCE_INST       = "SQ_DEMO_ANNOUNCE_INST";
    private static final String SQ_DEMO_ANNOUNCE_SERIES     = "SQ_DEMO_ANNOUNCE_SERIES";
    private static final String SQ_DEMO_ANNOUNCE_SERIES_TOC = "SQ_DEMO_ANNOUNCE_SERIES_TOC";
    private static final String SQ_DEMO_ANNOUNCE_TEXT       = "SQ_DEMO_ANNOUNCE_TEXT";
    private static final String SQ_DEMO_ANNOUNCE_TRAIN      = "SQ_DEMO_ANNOUNCE_TRAINS";
    private static final String SQ_DEMO_TRAIN_IDX           = "SQ_DEMO_TRAIN_IDX";

    private static final String SQ_ANNOUNCEMENT_SAMPLE_KEY    = "SQ_ANNOUNCEMENT_SAMPLE_KEY";   // TODO: remove this line
    // ***** injection field *****
    @PersistenceContext(unitName = "aePersistenceUnit")
    private EntityManager       em;

    // ***** public method *****
    public String obtainSqNoForAnnouncementSample() {// TODO: remove this method
        return obtainNextval(SQ_ANNOUNCEMENT_SAMPLE_KEY, LENGTH_15);
    }

    public String obtainNextSqDemoAnnounceInst() {
        return obtainNextval(SQ_DEMO_ANNOUNCE_INST, LENGTH_12);
    }

    public String obtainNextSqDemoAnnounceSeries() {
        return obtainNextval(SQ_DEMO_ANNOUNCE_SERIES, LENGTH_15);
    }

    public String obtainNextSqDemoAnnounceSeriesToc() {
        return obtainNextval(SQ_DEMO_ANNOUNCE_SERIES_TOC, LENGTH_15);
    }

    public String obtainNextSqDemoAnnounceText() {
        return obtainNextval(SQ_DEMO_ANNOUNCE_TEXT, LENGTH_12);
    }

    public String obtainNextSqDemoAnnounceTrain() {
        return obtainNextval(SQ_DEMO_ANNOUNCE_TRAIN, LENGTH_15);
    }

    public String obtainNextSqDemoTrainIdx() {
        return obtainNextval(SQ_DEMO_TRAIN_IDX, LENGTH_10);
    }

    // ***** private method *****
    private String obtainNextval(String sequenceName, int digit) {
        return String.format("%0" + digit + "d", obtainNextvalAsLong(sequenceName));
    }

    private long obtainNextvalAsLong(String sequenceName) {

        SQLBuilder builder = new SQLBuilder();
        builder.append(String.format(SQL_NEXTVAL, sequenceName));

        QueryBean queryBean = builder.build(createSystemOperator());

        Query query = em.createNativeQuery(queryBean.getQueryString());

        return ((BigDecimal) query.getSingleResult()).longValue();
    }

    private Operator createSystemOperator() {

        Operator operator = new Operator();
        operator.setTracingId(BaseConstant.SYSTEM_USER);
        operator.setTimeZone(ProcessingTimeUtil.getSystemTimeZone());
        return operator;
    }
    // ***** constructor *****
    // ***** protected method *****
    // ***** getter and setter *****

}
