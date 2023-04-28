package uk.co.sample.ws.base;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ibm.websphere.jaxrs.server.IBMRestServlet;

@WebServlet(
    description = "Servlet definition using annotations",
    asyncSupported = true,
    name = "RestServlet",
    loadOnStartup = 1,
    urlPatterns = {"/sample-webservice/api/*"}
    )
public class RestServlet extends IBMRestServlet {

    private static final long   serialVersionUID  = 4940717275226089832L;

    private static final String HTTP_METHOD_PATCH = "PATCH";

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {

        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        String method = httpRequest.getMethod();
        if (HTTP_METHOD_PATCH.equals(method)) {
            doPatch(httpRequest, httpResponse);
        } else {
            super.service(servletRequest, servletResponse);
        }
    }

    protected void doPatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handleRequest(request, response);
    }

    //***** constructor *****
    //***** public method *****
    //***** protected method *****
    //***** private method *****
    //***** call back method *****
    //***** getter and setter *****

}
