package uk.co.sample.util;

public class TextValidateBean extends AbstractValidateBean {

    private static final long serialVersionUID = 8075258931593427878L;
    private final int         minlen;
    private final int         maxlen;
    private final String      charset;
    private final Integer     maxByte;

    //***** constructor *****
    //***** public method *****

    /**
     * Constructor.<br>
     *
     * @param type Validate type.
     * @param minlen Max length.
     * @param maxlen Minimum length.
     */
    public TextValidateBean(String type, int minlen, int maxlen) {
        this(type, minlen, maxlen, null);
    }

    /**
     * Constructor.<br>
     *
     * @param type Validate type.
     * @param minlen Max length.
     * @param maxlen Minimum length.
     * @param charset Charset.
     */
    public TextValidateBean(String type, int minlen, int maxlen, String charset) {
        this(type, minlen, maxlen, charset, null);
    }

    /**
     * Constructor.<br>
     *
     * @param type Validate type.
     * @param minlen Max length.
     * @param maxlen Minimum length.
     * @param charset Charset.
     * @param maxByte Inetegr of max byte count.
     */
    public TextValidateBean(String type, int minlen, int maxlen, String charset, Integer maxByte) {
        super(type);
        this.minlen = minlen;
        this.maxlen = maxlen;
        this.charset = charset;
        this.maxByte = maxByte;
    }

    //***** protected method *****
    //***** private method *****
    //***** call back method *****
    //***** getter and setter *****

    public String getCharset() {
        return charset;
    }

    public Integer getMaxByte() {
        return maxByte;
    }

    public int getMaxlen() {
        return maxlen;
    }

    public int getMinlen() {
        return minlen;
    }

}
