package uk.co.sample.logic.service;

import uk.co.sample.base.AbstractBaseRequestDTO;
import uk.co.sample.security.Operator;

public class RestAnnouncementListSearchRequestDTO extends AbstractBaseRequestDTO {

    private static final long serialVersionUID = 5905767758727743069L;

    private String            status;
    private String            text;

    // for paging
    private Integer           offset;
    private Integer           pageSize;

    // ***** constructor *****
    public RestAnnouncementListSearchRequestDTO(Operator operator) {
        super(operator);
    }

    // ***** public method *****
    // ***** protected method *****
    // ***** private method *****
    // ***** call back method *****
    // ***** getter and setter *****
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public boolean isPagingRequired() {
        return !(offset == null || pageSize == null);
    }
}
