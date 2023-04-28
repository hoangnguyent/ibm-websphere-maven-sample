package uk.co.sample.logic.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.openjpa.persistence.QueryImpl;

import uk.co.log.LogWrapperImpl;
import uk.co.sample.constant.DBFields.ANNOUNCEMENT_SAMPLE;
import uk.co.sample.constant.DBTables;
import uk.co.sample.constant.DeleteStatus;
import uk.co.sample.constant.Status;
import uk.co.sample.dao.AnnouncementSampleDao;
import uk.co.sample.dao.CommonDao;
import uk.co.sample.dao.DemoAnnounceTrainDao;
import uk.co.sample.entity.AnnouncementSample;
import uk.co.sample.entity.DemoAnnounceInst;
import uk.co.sample.entity.DemoAnnounceSeries;
import uk.co.sample.entity.DemoAnnounceText;
import uk.co.sample.entity.DemoAnnounceTrain;
import uk.co.sample.jpa.SQLBuilder;

@Stateless
public class RestAnnouncementListSearchService {

    private static final String   SQL = " SELECT " +
        ANNOUNCEMENT_SAMPLE.ANNOUNCEMENT_SAMPLE_KEY + ", " +
        ANNOUNCEMENT_SAMPLE.STATUS + ", " +
        ANNOUNCEMENT_SAMPLE.TEXT + ", " +
        ANNOUNCEMENT_SAMPLE.VERSION +
        " FROM " +
        DBTables.ANNOUNCEMENT_SAMPLE +
        " WHERE " +
        ANNOUNCEMENT_SAMPLE.STATUS + " = ?";

    // ***** injection field *****
    @Inject
    private LogWrapperImpl        logger;
    @Inject
    private CommonDao             commonDao;
    @Inject
    private AnnouncementSampleDao announcementSampleDao;
    @Inject
    private DemoAnnounceTrainDao  announceTrainDao;

    // ***** constructor *****
    // ***** public method *****
    public RestAnnouncementListSearchReturnDTO doSearch(RestAnnouncementListSearchRequestDTO requestDTO) {

        logger.logStart(requestDTO.getOperator(), new Throwable().getStackTrace()[0].getMethodName());

        try {
            RestAnnouncementListSearchReturnDTO returnDTO = requestDTO.isPagingRequired() ? doPagingNativeSQL(requestDTO) : doSimpleNativeSQL(requestDTO);

            // returnDTO.getAnnouncements().addAll(doSimpleCriteria(requestDTO));
            // returnDTO.getAnnouncements().addAll(doSimpleHQL(requestDTO));
            doComplexJoinWithHQL(requestDTO);
            // doComplexJoinWithCriteria(requestDTO);

            return returnDTO;

        } finally {
            logger.logEnd(requestDTO.getOperator(), new Throwable().getStackTrace()[0].getMethodName());
        }

    }

    // ***** protected method *****
    // ***** private method *****
    private List<RestAnnouncementSearchBean> convertSqlListToListBean(List<Object> listObject) {

        List<RestAnnouncementSearchBean> listResultBean = new ArrayList<>();

        if (CollectionUtils.isEmpty(listObject)) {
            return listResultBean;
        }

        for (Object object : listObject) {
            listResultBean.add(convertSqlRecordToBean(object));
        }

        return listResultBean;
    }

    private RestAnnouncementSearchBean convertSqlRecordToBean(Object result) {

        Object[] arrObj = (Object[]) result;

        int index = 0;
        RestAnnouncementSearchBean returnBean = new RestAnnouncementSearchBean();
        returnBean.setAnnouncementSampleKey((String) arrObj[index++]);
        returnBean.setStatus(Status.valueOf((String) arrObj[index++]));
        returnBean.setText((String) arrObj[index++]);
        returnBean.setVersion(((BigDecimal) arrObj[index]).longValue());

        return returnBean;
    }

    private List<RestAnnouncementSearchBean> convertListEntityToListBean(List<AnnouncementSample> entities) {

        List<RestAnnouncementSearchBean> listResultBean = new ArrayList<>();

        if (CollectionUtils.isEmpty(entities)) {
            return listResultBean;
        }

        for (AnnouncementSample entitie : entities) {
            RestAnnouncementSearchBean returnBean = new RestAnnouncementSearchBean();
            returnBean.setAnnouncementSampleKey(entitie.getAnnouncementSampleKey());
            returnBean.setStatus(Status.valueOf(entitie.getStatus()));
            returnBean.setText(entitie.getText());
            returnBean.setVersion(entitie.getVersion());

            listResultBean.add(returnBean);
        }

        return listResultBean;
    }

    private RestAnnouncementListSearchReturnDTO doSimpleNativeSQL(RestAnnouncementListSearchRequestDTO requestDTO) {

        SQLBuilder builder = new SQLBuilder();
        builder.append(SQL);

        Status status = Status.valueOfIgnoreCase(requestDTO.getStatus());
        builder.bindParam(status.name());

        if (StringUtils.isNotBlank(requestDTO.getText())) {
            builder.append(" AND " + ANNOUNCEMENT_SAMPLE.TEXT + " LIKE ?||'%'");
            builder.bindParam(requestDTO.getText());
        }

        @SuppressWarnings("unchecked")
        List<Object> objects = (List<Object>) commonDao.findByNativeQuery(builder.build(requestDTO.getOperator()));
        List<RestAnnouncementSearchBean> records = convertSqlListToListBean(objects);

        RestAnnouncementListSearchReturnDTO returnDTO = new RestAnnouncementListSearchReturnDTO();
        returnDTO.getAnnouncements().addAll(records);
        returnDTO.setCount(records.size());

        return returnDTO;
    }

    private RestAnnouncementListSearchReturnDTO doPagingNativeSQL(RestAnnouncementListSearchRequestDTO requestDTO) {

        SQLBuilder builder = new SQLBuilder();
        builder.append(SQL);

        Status status = Status.valueOfIgnoreCase(requestDTO.getStatus());
        builder.bindParam(status.name());

        if (StringUtils.isNotBlank(requestDTO.getText())) {
            builder.append(" AND " + ANNOUNCEMENT_SAMPLE.TEXT + " LIKE ?||'%'");
            builder.bindParam(requestDTO.getText());
        }

        int totalCount = commonDao.selectIntNativeQuery(builder.buildCountQuery(requestDTO.getOperator()));
        if (totalCount > 0) {

            @SuppressWarnings("unchecked")
            List<Object> objects = (List<Object>) commonDao.findByNativeQueryRange(builder.build(requestDTO.getOperator()), requestDTO.getOffset() * requestDTO.getPageSize(),
                requestDTO.getPageSize());
            List<RestAnnouncementSearchBean> records = convertSqlListToListBean(objects);

            RestAnnouncementListSearchReturnDTO returnDTO = new RestAnnouncementListSearchReturnDTO();
            returnDTO.getAnnouncements().addAll(records);
            returnDTO.setCount(records.size());
            returnDTO.setTotalCount(totalCount);
            return returnDTO;
        }

        return new RestAnnouncementListSearchReturnDTO();
    }

    private List<RestAnnouncementSearchBean> doSimpleCriteria(RestAnnouncementListSearchRequestDTO requestDTO) {

        List<Predicate> predicates = new ArrayList<>();

        // select from
        CriteriaBuilder criteriaBuilder = announcementSampleDao.getEntityManager().getCriteriaBuilder();
        CriteriaQuery<AnnouncementSample> criteriaQuery = criteriaBuilder.createQuery(AnnouncementSample.class);
        Root<AnnouncementSample> announcementSample = criteriaQuery.from(AnnouncementSample.class);

        // where
        predicates.add(criteriaBuilder.equal(announcementSample.get("status").as(String.class), Status.valueOfIgnoreCase(requestDTO.getStatus()).name()));
        if (StringUtils.isNotBlank(requestDTO.getText())) {
            predicates.add(criteriaBuilder.like(announcementSample.get("text").as(String.class), requestDTO.getText() + "%"));
        }
        criteriaQuery.where(predicates.toArray(new Predicate[] {}));

        // get data
        TypedQuery<AnnouncementSample> query = announcementSampleDao.getEntityManager().createQuery(criteriaQuery);
        logger.debug(requestDTO.getOperator(), new Throwable().getStackTrace()[0].getMethodName(), query.unwrap(QueryImpl.class).getQueryString());
        List<AnnouncementSample> entities = query.getResultList();
        return convertListEntityToListBean(entities);
    }

    private List<RestAnnouncementSearchBean> doSimpleHQL(RestAnnouncementListSearchRequestDTO requestDTO) {

        String sql = "SELECT announcement FROM AnnouncementSample announcement WHERE announcement.status = ?1 AND announcement.text LIKE ?2 ";

        SQLBuilder builder = new SQLBuilder();
        builder.append(sql);

        Status status = Status.valueOfIgnoreCase(requestDTO.getStatus());
        builder.bindParam(status.name());

        if (StringUtils.isNotBlank(requestDTO.getText())) {
            builder.bindParam(requestDTO.getText() + "%");
        }

        List<AnnouncementSample> entities = announcementSampleDao.findByQuery(builder.build(requestDTO.getOperator()));

        return convertListEntityToListBean(entities);
    }

    private void doComplexJoinWithHQL(RestAnnouncementListSearchRequestDTO requestDTO) {
        String query = "SELECT DISTINCT train FROM DemoAnnounceTrain train"
            + " INNER JOIN train.series series"
            + " LEFT JOIN series.tocs toc"
            + " INNER JOIN train.texts text"
            + " INNER JOIN text.instances inst"
            + " WHERE train.deleteStatus = ?";

        SQLBuilder builder = new SQLBuilder();
        builder.append(query);

        int parameterIndex = 1;
        builder.append(String.valueOf(parameterIndex++));
        builder.bindParam(DeleteStatus.ACCEPTED.getValue());

        if (StringUtils.isNotBlank(requestDTO.getText())) {
            builder.append(" AND text.announceText LIKE ?" + parameterIndex++);
            builder.bindParam(requestDTO.getText() + "%");
        }

        if (StringUtils.isNotBlank(requestDTO.getStatus())) {
            Status status = Status.valueOfIgnoreCase(requestDTO.getStatus());
            builder.append(" AND inst.status = ?" + parameterIndex);
            builder.bindParam(status.name());
        }

        // get data
        List<DemoAnnounceTrain> entities = announceTrainDao.findByQuery(builder.build(requestDTO.getOperator()));

        // System.out
        System.out.println("Complex HQL result: " + entities.size());
        for (DemoAnnounceTrain trainEntity : entities) {
            System.out.println("Series Id:" + trainEntity.getSeries().getId().getSeriesId());
            System.out.println("Tocs size:" + trainEntity.getSeries().getTocs().size());
            for (DemoAnnounceText textEntity : trainEntity.getTexts()) {
                System.out.println("Text Id: " + textEntity.getId().getTextId() + ", value: " + textEntity.getAnnounceText());
            }
        }
    }

    private void doComplexJoinWithCriteria(RestAnnouncementListSearchRequestDTO requestDTO) {

        List<Predicate> predicates = new ArrayList<>();

        // select from
        CriteriaBuilder criteriaBuilder = announceTrainDao.getEntityManager().getCriteriaBuilder();
        CriteriaQuery<DemoAnnounceTrain> criteriaQuery = criteriaBuilder.createQuery(DemoAnnounceTrain.class);
        criteriaQuery.distinct(true);
        Root<DemoAnnounceTrain> set = criteriaQuery.from(DemoAnnounceTrain.class);

        // join
        Join<DemoAnnounceTrain, DemoAnnounceSeries> series = set.join("series", JoinType.INNER);
        series.join("tocs", JoinType.LEFT);
        Join<DemoAnnounceTrain, DemoAnnounceText> text = set.join("texts", JoinType.INNER);
        Join<DemoAnnounceText, DemoAnnounceInst> instance = text.join("instances", JoinType.INNER);

        // where
        predicates.add(criteriaBuilder.equal(set.get("deleteStatus").as(String.class), DeleteStatus.ACCEPTED.getValue()));

        if (StringUtils.isNotBlank(requestDTO.getText())) {
            predicates.add(criteriaBuilder.like(text.get("announceText").as(String.class), requestDTO.getText() + "%"));
        }

        if (StringUtils.isNotBlank(requestDTO.getStatus())) {
            predicates.add(criteriaBuilder.equal(instance.get("status").as(String.class), Status.valueOfIgnoreCase(requestDTO.getStatus()).name()));
        }

        criteriaQuery.where(predicates.toArray(new Predicate[] {}));

        // get data
        TypedQuery<DemoAnnounceTrain> query = announcementSampleDao.getEntityManager().createQuery(criteriaQuery);
        logger.debug(requestDTO.getOperator(), new Throwable().getStackTrace()[0].getMethodName(), query.unwrap(org.apache.openjpa.persistence.QueryImpl.class).getQueryString());
        List<DemoAnnounceTrain> entities = query.getResultList();

        // System.out
        System.out.println("Complex Criteria result: " + entities.size());
        for (DemoAnnounceTrain trainEntity : entities) {
            System.out.println("Series Id:" + trainEntity.getSeries().getId().getSeriesId());
            for (DemoAnnounceText textEntity : trainEntity.getTexts()) {
                System.out.println("Text Id: " + textEntity.getId().getTextId() + ", value: " + textEntity.getAnnounceText());
            }
        }
    }
    // ***** getter and setter *****
}
