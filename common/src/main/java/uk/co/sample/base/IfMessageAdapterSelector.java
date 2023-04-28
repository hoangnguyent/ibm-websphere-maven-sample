package uk.co.sample.base;

import java.util.Date;

import uk.co.sample.security.Operator;

public interface IfMessageAdapterSelector<T> {
    IfMessageAdapter<T> getAdapter(T record, Operator operator, Date startDate);
}
