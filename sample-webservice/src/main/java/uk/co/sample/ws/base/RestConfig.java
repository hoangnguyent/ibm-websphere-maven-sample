package uk.co.sample.ws.base;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletConfig;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;

import io.swagger.jaxrs.config.BeanConfig;
import uk.co.sample.ws.AnnouncementApi;

@ApplicationPath("/api")
public class RestConfig extends Application {

    // ***** injection field *****
    // ***** constructor *****
    public RestConfig(@Context ServletConfig servletConfig) {

        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setVersion("1.0.0");
        beanConfig.setSchemes(new String[] { "http" });
        beanConfig.setTitle("Sample project APIs documentation");
        beanConfig.setBasePath("/sample-webservice/api");
        beanConfig.setResourcePackage("uk.co.sample.ws");
        beanConfig.setScan(true);
    }

    // ***** public method *****
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();

        // indicate API
        classes.add(AnnouncementApi.class);

        // indicate ExceptionMapper
        classes.add(RestExceptionMapper.class);

        // indicate Swagger
        classes.add(io.swagger.jaxrs.listing.ApiListingResource.class);
        classes.add(io.swagger.jaxrs.listing.SwaggerSerializers.class);

        return classes;
    }
    // ***** protected method *****
    // ***** private method *****
    // ***** getter and setter *****

}
