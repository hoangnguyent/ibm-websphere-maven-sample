package test.junit.logic;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import uk.co.log.LogWrapperImpl;
import uk.co.sample.constant.BaseConstant;
import uk.co.sample.dao.AnnouncementSampleDao;
import uk.co.sample.entity.AnnouncementSample;
import uk.co.sample.logic.manager.AnnouncementManager;
import uk.co.sample.logic.manager.AnnouncementSampleEntryRequestDTO;
import uk.co.sample.logic.service.SequenceSearchService;
import uk.co.sample.security.Operator;
import uk.co.sample.util.ProcessingTimeUtil;

@RunWith(MockitoJUnitRunner.class)
public class TestAnnouncementManager {

    @Mock
    LogWrapperImpl                logger;

    @Mock
    SequenceSearchService         sequenceSearchService;

    @Mock
    private AnnouncementSampleDao announcementSampleDao;

    @InjectMocks
    private AnnouncementManager   manager;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void execAnnouncementSampleEntry_shouldNormallyEnd() {

        String mockSequence = "new sequence value";

        // Given/mock
        doNothing().when(logger).debug(any(Operator.class), any(String.class), any(String.class));
        doNothing().when(logger).info(any(Operator.class), any(String.class), any(String.class));
        when(sequenceSearchService.obtainSqNoForAnnouncementSample())
        .thenReturn(mockSequence);
        doNothing().when(announcementSampleDao).create(any(AnnouncementSample.class));

        // Prepare input parameters
        AnnouncementSampleEntryRequestDTO requestDTO = new AnnouncementSampleEntryRequestDTO(createDefaultOperator());
        requestDTO.setText("JUnit Test Announcenent");

        // Verify
        String newPrimaryKey = manager.execAnnouncementSampleEntry(requestDTO);
        Assert.assertEquals(mockSequence, newPrimaryKey);

    }

    private Operator createDefaultOperator() {
        Operator operator = new Operator();
        operator.setTracingId(BaseConstant.SYSTEM_USER);
        operator.setTimeZone(ProcessingTimeUtil.getSystemTimeZone());
        return operator;
    }

}
