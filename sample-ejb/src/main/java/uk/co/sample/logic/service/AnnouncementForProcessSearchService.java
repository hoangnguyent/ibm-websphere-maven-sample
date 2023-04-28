package uk.co.sample.logic.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import uk.co.log.LogWrapperImpl;
import uk.co.sample.constant.CommonConstant;
import uk.co.sample.constant.DBFields.ANNOUNCEMENT_SAMPLE;
import uk.co.sample.constant.DBTables;
import uk.co.sample.dao.CommonDao;
import uk.co.sample.jpa.SQLBuilder;

@Stateless
public class AnnouncementForProcessSearchService {

    private static final String SQL =
        " SELECT " +
            ANNOUNCEMENT_SAMPLE.ANNOUNCEMENT_SAMPLE_KEY + ", " +
            ANNOUNCEMENT_SAMPLE.TEXT + ", " +
            ANNOUNCEMENT_SAMPLE.VERSION +
        " FROM " +
            DBTables.ANNOUNCEMENT_SAMPLE;

    // ***** injection field *****
    @Inject
    private LogWrapperImpl      logger;
    @Inject
    private CommonDao           commonDao;

    // ***** constructor *****
    // ***** public method *****
    @SuppressWarnings("unchecked")
    public List<AnnouncementForProcessSearchBean> doSearch(AnnouncementForProcessListSearchRequestDTO requestDTO) {

        logger.logStart(requestDTO.getOperator(), new Throwable().getStackTrace()[0].getMethodName());
        SQLBuilder builder = createNativeSQLBuilder(requestDTO);

        List<Object> objects = (List<Object>) commonDao.findByNativeQuery(builder.build(requestDTO.getOperator()));

        logger.logEnd(requestDTO.getOperator(), new Throwable().getStackTrace()[0].getMethodName());
        return convertSqlListToListBean(objects);
    }

    // ***** protected method *****
    // ***** private method *****
    private SQLBuilder createNativeSQLBuilder(AnnouncementForProcessListSearchRequestDTO requestDTO) {
        SQLBuilder builder = new SQLBuilder();
        builder.append(SQL);
        builder.append(" WHERE ").append(ANNOUNCEMENT_SAMPLE.STATUS).append(" IN ('");
        builder.append(StringUtils.join(requestDTO.getStatus(), CommonConstant.SINGLE_QUOTE + CommonConstant.COMMA + CommonConstant.SINGLE_QUOTE)).append("')");

        return builder;
    }

    private List<AnnouncementForProcessSearchBean> convertSqlListToListBean(List<Object> listObject) {

        List<AnnouncementForProcessSearchBean> listResultBean = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(listObject)) {
            for (Object object : listObject) {
                listResultBean.add(convertSqlRecordToBean(object));
            }
        }

        return listResultBean;
    }

    private AnnouncementForProcessSearchBean convertSqlRecordToBean(Object result) {

        Object[] arrObj = (Object[]) result;

        int index = 0;
        AnnouncementForProcessSearchBean returnBean = new AnnouncementForProcessSearchBean();
        returnBean.setAnnouncementSampleKey((String) arrObj[index++]);
        returnBean.setText((String) arrObj[index++]);
        returnBean.setVersion(((BigDecimal) arrObj[index]).longValue());

        return returnBean;
    }

    // ***** getter and setter *****
}
