package uk.co.log;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class LogWrapper {

    //***** injection field *****
    //***** constructor *****
    //***** public method *****

    @Produces
    public LogWrapperImpl getLogger(InjectionPoint injectionPoint) {

        Logger logger = LoggerFactory.getLogger(getClass().getName());
        return new LogWrapperImpl(logger, injectionPoint.getMember().getDeclaringClass().getName());
    }

    //***** protected method *****
    //***** private method *****
    //***** getter and setter *****

}
