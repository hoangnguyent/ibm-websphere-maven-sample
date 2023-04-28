package uk.co.sample.logic.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import uk.co.log.LogWrapperImpl;
import uk.co.sample.constant.DBFields.ANNOUNCEMENT_SAMPLE;
import uk.co.sample.constant.DBTables;
import uk.co.sample.dao.CommonDao;
import uk.co.sample.jpa.SQLBuilder;

@Stateless
public class SoapAnnouncementListSearchService {

    private static final String SQL = " SELECT " +
        ANNOUNCEMENT_SAMPLE.ANNOUNCEMENT_SAMPLE_KEY      + ", " +
        ANNOUNCEMENT_SAMPLE.TEXT                         + ", " +
        ANNOUNCEMENT_SAMPLE.VERSION +
    " FROM "  +
        DBTables.ANNOUNCEMENT_SAMPLE +
    " WHERE " +
        ANNOUNCEMENT_SAMPLE.STATUS + " = ?";

    // ***** injection field *****
    @Inject
    private LogWrapperImpl      logger;
    @Inject
    private CommonDao           commonDao;

    // ***** constructor *****
    // ***** public method *****
    @SuppressWarnings("unchecked")
    public SoapAnnouncementListSearchReturnDTO doSearch(SoapAnnouncementListSearchRequestDTO requestDTO) {

        logger.logStart(requestDTO.getOperator(), new Throwable().getStackTrace()[0].getMethodName());
        SQLBuilder builder = createNativeSQLBuilder(requestDTO);

        List<Object> objects = (List<Object>) commonDao.findByNativeQuery(builder.build(requestDTO.getOperator()));

        SoapAnnouncementListSearchReturnDTO returnDTO = new SoapAnnouncementListSearchReturnDTO();
        returnDTO.getAnnouncements().addAll(convertSqlListToListBean(objects));

        logger.logEnd(requestDTO.getOperator(), new Throwable().getStackTrace()[0].getMethodName());
        return returnDTO;
    }

    // ***** protected method *****
    // ***** private method *****
    private SQLBuilder createNativeSQLBuilder(SoapAnnouncementListSearchRequestDTO requestDTO) {
        SQLBuilder builder = new SQLBuilder();
        builder.append(SQL);
        builder.bindParam(requestDTO.getStatus().name());

        if (StringUtils.isNotBlank(requestDTO.getText())) {
            builder.append(" AND " + ANNOUNCEMENT_SAMPLE.TEXT + " LIKE ?||'%'");
            builder.bindParam(requestDTO.getText());
        }

        return builder;
    }

    private List<SoapAnnouncementSearchBean> convertSqlListToListBean(List<Object> listObject) {

        List<SoapAnnouncementSearchBean> listResultBean = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(listObject)) {
            for (Object object : listObject) {
                listResultBean.add(convertSqlRecordToBean(object));
            }
        }

        return listResultBean;
    }

    private SoapAnnouncementSearchBean convertSqlRecordToBean(Object result) {

        Object[] arrObj = (Object[]) result;

        int index = 0;
        SoapAnnouncementSearchBean returnBean = new SoapAnnouncementSearchBean();
        returnBean.setAnnouncementSampleKey((String) arrObj[index++]);
        returnBean.setText((String) arrObj[index++]);
        returnBean.setVersion(((BigDecimal) arrObj[index]).longValue());

        return returnBean;
    }

    // ***** getter and setter *****
}
