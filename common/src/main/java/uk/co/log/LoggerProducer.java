package uk.co.log;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class LoggerProducer {

    //***** injection field *****
    //***** constructor *****
    //***** public method *****

    @Produces
    public Logger getLogger(InjectionPoint injectionPoint) {

        return LoggerFactory.getLogger(injectionPoint.getMember().getDeclaringClass().getName());
    }

    //***** protected method *****
    //***** private method *****
    //***** getter and setter *****

}
