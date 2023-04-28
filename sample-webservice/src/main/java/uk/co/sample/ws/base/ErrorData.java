package uk.co.sample.ws.base;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.Format;
import java.util.Date;

public class ErrorData implements Serializable {

    private static final long   serialVersionUID = 3202587446784676840L;

    private static final int    HASH             = 2134424493;
    /**
     * default format for detail message constructing BigDecimal argument without formatter.
     */
    private static final String DISP_17_9_NOZERO = "#######0.#########";
    private final String        errorCode;
    private final String[]      params;

    public ErrorData(String errorCode) {
        this(errorCode, new String[] { null });
    }

    public ErrorData(String errorCode, String detailMessage) {
        this(errorCode, new String[] { detailMessage });
    }

    public ErrorData(String errorCode, String[] params) {
        this.errorCode = errorCode;
        if (null == params) {
            this.params = new String[] { String.valueOf((Object) null) };
            return;
        }
        this.params = new String[params.length];
        for (int i = 0; i < params.length; i++) {
            String message = (null == params[i] ? String.valueOf((Object) null) : params[i]);
            this.params[i] = message;
        }
    }

    public ErrorData(String errorCode, int num) {
        this(errorCode, Integer.toString(num));
    }

    public ErrorData(String errorCode, Integer num) {
        this(errorCode, null == num ? String.valueOf((Object) null) : num.toString());
    }

    public ErrorData(String errorCode, Integer[] nums) {
        this.errorCode = errorCode;
        if (null == nums) {
            this.params = new String[] { String.valueOf((Object) null) };
            return;
        }
        this.params = new String[nums.length];
        for (int i = 0; i < nums.length; i++) {
            this.params[i] = (null == nums[i] ? String.valueOf((Object) null) : nums[i].toString());
        }
    }

    public ErrorData(String errorCode, long num) {
        this(errorCode, Long.toString(num));
    }

    public ErrorData(String errorCode, Long num) {
        this(errorCode, null == num ? String.valueOf((Object) null) : num.toString());
    }

    public ErrorData(String errorCode, Long[] nums) {
        this.errorCode = errorCode;
        if (null == nums) {
            this.params = new String[] { String.valueOf((Object) null) };
            return;
        }
        this.params = new String[nums.length];
        for (int i = 0; i < nums.length; i++) {
            this.params[i] = (null == nums[i] ? String.valueOf((Object) null) : nums[i].toString());
        }
    }

    public ErrorData(String errorCode, boolean bool) {
        this(errorCode, Boolean.toString(bool));
    }

    public ErrorData(String errorCode, BigDecimal num) {
        this(errorCode, num, new DecimalFormat(DISP_17_9_NOZERO));
    }

    public ErrorData(String errorCode, BigDecimal[] nums) {
        this(errorCode, nums, new DecimalFormat(DISP_17_9_NOZERO));
    }

    public ErrorData(String errorCode, BigDecimal num, DecimalFormat formatter) {
        this.errorCode = errorCode;
        if (null == num) {
            this.params = new String[] { String.valueOf((Object) null) };
            return;
        }
        BigDecimal n = num.setScale(formatter.getMaximumFractionDigits(), BigDecimal.ROUND_HALF_UP);
        this.params = new String[] { formatter.format(n) };
    }

    public ErrorData(String errorCode, BigDecimal[] nums, DecimalFormat formatter) {
        this.errorCode = errorCode;
        if (null == nums) {
            this.params = new String[] { String.valueOf((Object) null) };
            return;
        }
        this.params = new String[nums.length];
        for (int i = 0; i < nums.length; i++) {
            if (null == nums[i]) {
                this.params[i] = String.valueOf((Object) null);
            } else {
                BigDecimal n = nums[i].setScale(formatter.getMaximumFractionDigits(), BigDecimal.ROUND_HALF_UP);
                this.params[i] = formatter.format(n);
            }
        }
    }

    public ErrorData(String errorCode, Date date) {
        this(errorCode, date, DateFormat.getInstance());
    }

    public ErrorData(String errorCode, Date[] dates) {
        this(errorCode, dates, DateFormat.getInstance());
    }

    public ErrorData(String errorCode, Date date, Format formatter) {
        this(errorCode, null == date ? String.valueOf((Object) null) : formatter.format(date));
    }

    public ErrorData(String errorCode, Date[] dates, Format formatter) {
        this.errorCode = errorCode;
        if (null == dates) {
            this.params = new String[] { String.valueOf((Object) null) };
            return;
        }
        this.params = new String[dates.length];
        for (int i = 0; i < dates.length; i++) {
            this.params[i] = (null == dates[i] ? String.valueOf((Object) null) : formatter.format(dates[i]));
        }
    }

    public String[] getParams() {
        return params;
    }

    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public int hashCode() {
        int hash = HASH;

        hash = hash * 37 + (null == errorCode ? 71 : errorCode.hashCode());
        for (String param : params) {
            hash = hash * 37 + (null == param ? 71 : param.hashCode());
        }

        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (null == obj) {
            return false;
        }

        if (obj.getClass().equals(getClass())) {

            ErrorData tmp = (ErrorData) obj;
            if ((null == errorCode && tmp.errorCode != null) || (errorCode != null && !errorCode.equals(tmp.errorCode))) {
                return false;
            }
            return java.util.Arrays.equals(params, tmp.params);
        }
        return false;
    }

    /**
     * return true when detail messge exists.<br>
     * @return true when detail messge exists.
     */
    public boolean hasDetailMessage() {
        return (params != null && params.length > 0);
    }

    @Override
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("errorCode:").append(errorCode).append('\n');
        for (int i = 0; i < params.length; i++) {
            buf.append("params[").append(i).append("]:").append(params[i]).append('\n');
        }
        return buf.toString();
    }

}
