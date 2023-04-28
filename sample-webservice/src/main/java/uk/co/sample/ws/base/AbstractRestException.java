package uk.co.sample.ws.base;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.Format;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import uk.co.sample.constant.CommonConstant;
import uk.co.sample.exception.AbstractApplicationRuntimeException;

public abstract class AbstractRestException extends AbstractApplicationRuntimeException {

    private static final long    serialVersionUID = 6539065319636533466L;

    private final Set<ErrorData> errDataSet       = new HashSet<>();

    protected AbstractRestException() {
    }

    protected AbstractRestException(Throwable t) {
        super(t);
    }

    public void addErrorCode(String errCode) {
        errDataSet.add(new ErrorData(errCode));
    }

    public void addErrorCode(String errCode, String detailMessage) {
        errDataSet.add(new ErrorData(errCode, detailMessage));
    }

    public void addErrorCode(String errCode, String[] detailMessages) {
        errDataSet.add(new ErrorData(errCode, detailMessages));
    }

    public void addErrorCode(String errCode, int num) {
        errDataSet.add(new ErrorData(errCode, num));
    }

    public void addErrorCode(String errCode, Integer num) {
        errDataSet.add(new ErrorData(errCode, num));
    }

    public void addErrorCode(String errCode, Integer[] nums) {
        errDataSet.add(new ErrorData(errCode, nums));
    }

    public void addErrorCode(String errCode, long num) {
        errDataSet.add(new ErrorData(errCode, num));
    }

    public void addErrorCode(String errCode, Long num) {
        errDataSet.add(new ErrorData(errCode, num));
    }

    public void addErrorCode(String errCode, Long[] nums) {
        errDataSet.add(new ErrorData(errCode, nums));
    }

    public void addErrorCode(String errCode, boolean bool) {
        errDataSet.add(new ErrorData(errCode, bool));
    }

    public void addErrorCode(String errCode, BigDecimal num) {
        errDataSet.add(new ErrorData(errCode, num));
    }

    public void addErrorCode(String errCode, BigDecimal[] nums) {
        errDataSet.add(new ErrorData(errCode, nums));
    }

    public void addErrorCode(String errCode, BigDecimal num, DecimalFormat formatter) {
        errDataSet.add(new ErrorData(errCode, num, formatter));
    }

    public void addErrorCode(String errCode, BigDecimal[] nums, DecimalFormat formatter) {
        errDataSet.add(new ErrorData(errCode, nums, formatter));
    }

    public void addErrorCode(String errCode, Date date) {
        errDataSet.add(new ErrorData(errCode, date));
    }

    public void addErrorCode(String errCode, Date[] dates) {
        errDataSet.add(new ErrorData(errCode, dates));
    }

    public void addErrorCode(String errCode, Date date, Format formatter) {
        errDataSet.add(new ErrorData(errCode, date, formatter));
    }

    public void addErrorCode(String errCode, Date[] dates, Format formatter) {
        errDataSet.add(new ErrorData(errCode, dates, formatter));
    }

    public void addErrorDatas(Set<ErrorData> errorDatas) {
        addErrorDataSet(errorDatas);
    }

    public void addException(AbstractRestException exception) {
        addErrorDataSet(exception.getErrorDatas());
    }

    private void addErrorDataSet(Set<ErrorData> errorDatas) {
        errDataSet.addAll(errorDatas);
    }

    public List<String> getErrorCode() {
        if (errDataSet.isEmpty()) {
            return new ArrayList<>();
        }

        List<String> list = new ArrayList<>();

        for (ErrorData errorData : errDataSet) {
            list.add(errorData.getErrorCode());

        }

        return list;
    }

    public Set<ErrorData> getErrorDatas() {
        return errDataSet;
    }

    @Override
    public String toString() {
        boolean isFirst = true;
        StringBuffer buffer = new StringBuffer();
        buffer.append(getClass().getName()).append(CommonConstant.COLON);

        for (ErrorData errorData : errDataSet) {
            if (!isFirst) {
                buffer.append(", ");
            }
            isFirst = false;

            buffer.append(errorData.getErrorCode());

            if (errorData.getParams().length != 0) {
                buffer.append("{");
                for (int i = 0; i < errorData.getParams().length; i++) {
                    buffer.append(errorData.getParams()[i]);
                }
                buffer.append("}");
            }

        }

        return buffer.toString();
    }

    public boolean hasError() {
        return !errDataSet.isEmpty();
    }

}
