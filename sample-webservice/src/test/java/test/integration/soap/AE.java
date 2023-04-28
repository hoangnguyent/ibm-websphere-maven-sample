//
// Generated By:JAX-WS RI 2.2.9-b130926.1035 (JAXB RI IBM 2.2.8-b130911.1802)
//


package test.integration.soap;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;

@WebServiceClient(name = "AE", targetNamespace = "http://ws.sample.co.uk/", wsdlLocation = "http://localhost:9080/sample-webservice/AE/AE.wsdl")
public class AE
    extends Service
{

    private final static URL AE_WSDL_LOCATION;
    private final static WebServiceException AE_EXCEPTION;
    private final static QName AE_QNAME = new QName("http://ws.sample.co.uk/", "AE");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://localhost:9080/sample-webservice/AE/AE.wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        AE_WSDL_LOCATION = url;
        AE_EXCEPTION = e;
    }

    public AE() {
        super(__getWsdlLocation(), AE_QNAME);
    }

    public AE(WebServiceFeature... features) {
        super(__getWsdlLocation(), AE_QNAME, features);
    }

    public AE(URL wsdlLocation) {
        super(wsdlLocation, AE_QNAME);
    }

    public AE(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, AE_QNAME, features);
    }

    public AE(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public AE(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns IfSoapWebService
     */
    @WebEndpoint(name = "AnnouncementSoapWebServicePort")
    public IfSoapWebService getAnnouncementSoapWebServicePort() {
        return super.getPort(new QName("http://ws.sample.co.uk/", "AnnouncementSoapWebServicePort"), IfSoapWebService.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns IfSoapWebService
     */
    @WebEndpoint(name = "AnnouncementSoapWebServicePort")
    public IfSoapWebService getAnnouncementSoapWebServicePort(WebServiceFeature... features) {
        return super.getPort(new QName("http://ws.sample.co.uk/", "AnnouncementSoapWebServicePort"), IfSoapWebService.class, features);
    }

    private static URL __getWsdlLocation() {
        if (AE_EXCEPTION!= null) {
            throw AE_EXCEPTION;
        }
        return AE_WSDL_LOCATION;
    }

}