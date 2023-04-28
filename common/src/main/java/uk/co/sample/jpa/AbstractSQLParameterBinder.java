package uk.co.sample.jpa;

abstract class AbstractSQLParameterBinder<T> implements IfQueryParameterBinder {

    final int bindNo;
    final T   value;

    //***** injection field *****
    //***** constructor *****

    public AbstractSQLParameterBinder(int bindNo, T value) {
        this.bindNo = bindNo;
        this.value = value;
    }

    //***** public method *****

    @Override
    public String toString() {
        return bindNo + "=" + value;
    }

    //***** protected method *****
    //***** private method *****
    //***** getter and setter *****

    public int getBindNo() {
        return bindNo;
    }
    public T getValue() {
        return value;
    }

}
