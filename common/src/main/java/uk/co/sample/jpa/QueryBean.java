package uk.co.sample.jpa;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import uk.co.sample.constant.CommonConstant;
import uk.co.sample.security.Operator;

public class QueryBean {

    private final String queryString;
    private final List<IfQueryParameterBinder> paramBinders;
    private final Operator operator;

    //***** injection field *****
    //***** constructor *****

    QueryBean(String queryString, List<IfQueryParameterBinder> paramBinders, Operator operator) {
        this.queryString = queryString;
        this.paramBinders = paramBinders;
        this.operator = operator;
    }

    //***** public method *****

    @Override
    public String toString() {
        return new StringBuilder(queryString).append("\n").append(getParamString()).toString();
    }

    public String getParamString() {
        if (paramBinders == null || paramBinders.isEmpty()) {
            return "[no parameter]";
        } else {
            return "[" + StringUtils.join(paramBinders, CommonConstant.COMMA) + "]";
        }
    }

    //***** protected method *****
    //***** private method *****
    //***** getter and setter *****

    public List<IfQueryParameterBinder> getParamBinders() {
        return paramBinders;
    }

    public String getQueryString() {
        return queryString;
    }

    public Operator getOperator() {
        return operator;
    }

}
