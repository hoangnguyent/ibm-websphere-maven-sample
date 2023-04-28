package uk.co.sample.jpa;

import javax.persistence.Query;

public interface IfQueryParameterBinder {

    void bind(Query query);

}
