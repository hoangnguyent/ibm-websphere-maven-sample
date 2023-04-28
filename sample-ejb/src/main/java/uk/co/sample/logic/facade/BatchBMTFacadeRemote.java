package uk.co.sample.logic.facade;

import uk.co.sample.base.AbstractBatchRequestDTO;
import uk.co.sample.base.Result;

public interface BatchBMTFacadeRemote {

    Result callAnnouncementDelete(AbstractBatchRequestDTO requestDTO);
}
