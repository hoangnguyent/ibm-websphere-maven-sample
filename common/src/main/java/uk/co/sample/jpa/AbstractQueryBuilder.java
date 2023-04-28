package uk.co.sample.jpa;

import java.util.Collections;
import java.util.List;

import uk.co.sample.security.Operator;

abstract class AbstractQueryBuilder<T extends AbstractQueryBuilder<T>> {

    private final StringBuilder queryBuffer = new StringBuilder();
    List<IfQueryParameterBinder> paramBinders;

    //***** injection field *****
    //***** constructor *****
    //***** public method *****

    public QueryBean build(Operator operator) {
        return new QueryBean(queryBuffer.toString(), paramBinders == null ? Collections.<IfQueryParameterBinder>emptyList() : paramBinders, operator);
    }

    @SuppressWarnings("unchecked")
    public T append(String sql) {
        this.queryBuffer.append(sql);
        return (T) this;
    }

    //***** protected method *****

    protected String getQueryString() {
        return queryBuffer.toString();
    }

    //***** private method *****
    //***** getter and setter *****

}
