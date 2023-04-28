package uk.co.sample.jpa;

import java.util.ArrayList;
import java.util.Collections;

import uk.co.sample.constant.CommonConstant;
import uk.co.sample.security.Operator;

public class SQLBuilder extends AbstractQueryBuilder<SQLBuilder> {

    private int bindCount = 0;

    //***** injection field *****
    //***** constructor *****
    //***** public method *****
    public QueryBean buildCountQuery(Operator operator) {
        String countQuery = "SELECT COUNT(*) FROM (" + getQueryString() + ") NUM";
        return new QueryBean(countQuery, null == paramBinders ? Collections.emptyList() : paramBinders, operator);
    }

    public SQLBuilder append(String sql, Object bindValue) {
        append(sql);
        bindParam(bindValue);
        return this;
    }

    public SQLBuilder bindParam(Object bindValue) {
        if (paramBinders == null) {
            paramBinders = new ArrayList<>();
        }
        paramBinders.add(new BasicSQLParameterBinder(++bindCount, bindValue));
        return this;
    }

    public SQLBuilder appendLikeStartsWith(String fieldName, String value) {
        StringBuilder escapedValue = new StringBuilder();
        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            switch (c) {
            case '%':
            case '％':
            case '_':
            case '＿':
            case '$':
                escapedValue.append('$').append(c);
                break;
            default:
                escapedValue.append(c);
            }
        }
        append(CommonConstant.WHITE_SPACE).append(fieldName).append(" LIKE ? || '%' ESCAPE '$' ").bindParam(escapedValue.toString());
        return this;
    }

    //***** protected method *****
    //***** private method *****
    //***** getter and setter *****

}
