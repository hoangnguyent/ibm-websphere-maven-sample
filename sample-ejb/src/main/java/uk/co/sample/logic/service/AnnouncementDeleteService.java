package uk.co.sample.logic.service;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;

import uk.co.log.LogWrapperImpl;
import uk.co.sample.constant.CommonConstant;
import uk.co.sample.constant.DBTables;
import uk.co.sample.constant.DBFields.ANNOUNCEMENT_SAMPLE;
import uk.co.sample.dao.CommonDao;

@Stateless
public class AnnouncementDeleteService {

    private static final String SQL = " DELETE " +
        DBTables.ANNOUNCEMENT_SAMPLE +
    " WHERE " + 
        ANNOUNCEMENT_SAMPLE.ANNOUNCEMENT_SAMPLE_KEY + " IN (%s)";

    // ***** injection field *****
    @Inject
    private LogWrapperImpl      logger;
    @Inject
    private CommonDao           commonDao;

    // ***** constructor *****
    // ***** public method *****
    public void execute(AnnouncementDeleteServiceRequestDTO requestDTO) {

        logger.logStart(requestDTO.getOperator(), new Throwable().getStackTrace()[0].getMethodName());

        if (requestDTO.getKeys().isEmpty()) {
            logger.debug(requestDTO.getOperator(), new Throwable().getStackTrace()[0].getMethodName(), "No Announcement to delete.");
            return;
        }

        Query query = createQuery(requestDTO);
        query.executeUpdate();

        logger.logEnd(requestDTO.getOperator(), new Throwable().getStackTrace()[0].getMethodName());
    }

    // ***** protected method *****
    // ***** private method *****
    private Query createQuery(AnnouncementDeleteServiceRequestDTO requestDTO) {
        String parameter = CommonConstant.SINGLE_QUOTE + StringUtils.join(requestDTO.getKeys(), CommonConstant.SINGLE_QUOTE + CommonConstant.COMMA + CommonConstant.SINGLE_QUOTE) + CommonConstant.SINGLE_QUOTE;
        return commonDao.getEntityManager().createNativeQuery(String.format(SQL, parameter));
    }

    // ***** getter and setter *****
}
