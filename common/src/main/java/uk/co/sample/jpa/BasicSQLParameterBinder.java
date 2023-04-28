package uk.co.sample.jpa;

import javax.persistence.Query;

public class BasicSQLParameterBinder extends AbstractSQLParameterBinder<Object> {

    //***** injection field *****
    //***** constructor *****

    public BasicSQLParameterBinder(int bindNo, Object value) {
        super(bindNo, value);
    }

    //***** public method *****

    @Override
    public void bind(Query query) {
        query.setParameter(getBindNo(), getValue());
    }

    //***** protected method *****
    //***** private method *****
    //***** getter and setter *****

}
