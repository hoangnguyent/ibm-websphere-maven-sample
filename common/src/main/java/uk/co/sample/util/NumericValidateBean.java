package uk.co.sample.util;

import java.math.BigDecimal;

public class NumericValidateBean extends AbstractValidateBean {

    private static final long serialVersionUID = -537412864094401670L;
    private final String      length;                                 // ex. "10.0" "12.6"
    private final BigDecimal  minvalue;
    private final BigDecimal  maxvalue;

    //***** constructor *****
    //***** public method *****

    /**
     * Constructor.<br>
     *
     * @param type Validate type.
     * @param length Digit length.
     * @param minval Maximum value.
     * @param maxval Minimum value.
     */
    public NumericValidateBean(String type, String length, BigDecimal minval, BigDecimal maxval) {
        super(type);
        this.length = length;
        this.minvalue = minval;
        this.maxvalue = maxval;
    }

    //***** protected method *****
    //***** private method *****
    //***** call back method *****
    //***** getter and setter *****

    public String getLength() {
        return length;
    }

    public BigDecimal getMaxvalue() {
        return maxvalue;
    }

    public BigDecimal getMinvalue() {
        return minvalue;
    }

}
