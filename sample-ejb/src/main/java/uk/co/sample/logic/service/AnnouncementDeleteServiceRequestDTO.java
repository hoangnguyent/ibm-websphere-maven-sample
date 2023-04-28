package uk.co.sample.logic.service;

import java.util.ArrayList;
import java.util.List;

import uk.co.sample.base.AbstractBaseRequestDTO;

public class AnnouncementDeleteServiceRequestDTO extends AbstractBaseRequestDTO {

    private static final long serialVersionUID = 5954331951204497105L;

    public AnnouncementDeleteServiceRequestDTO(AbstractBaseRequestDTO requestDTO) {
        super(requestDTO);
    }

    private List<String> keys = new ArrayList<>();

    // ***** constructor *****

    // ***** injection field *****
    // ***** public method *****
    // ***** protected method *****
    // ***** private method *****
    // ***** getter and setter *****
    public List<String> getKeys() {
        return keys;
    }

    public void setKeys(List<String> keys) {
        this.keys = keys;
    }

}
