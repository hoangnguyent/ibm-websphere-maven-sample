package uk.co.sample.util;

import static uk.co.sample.constant.SystemErrorCodeConstant.ERROR_DATA;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import uk.co.sample.exception.ApplicationDataException;

public class XmlUtil {

    //***** constructor *****
    private XmlUtil() {
        // Do nothing
    }

    //***** public method *****
    public static String marshalXmlObjectToString(Object ojectInXmlFormat) {

        try {
            JAXBContext contextObj = JAXBContext.newInstance(ojectInXmlFormat.getClass());
            Marshaller marshallerObj = contextObj.createMarshaller();
            marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            StringWriter xmlContent = new StringWriter();
            marshallerObj.marshal(ojectInXmlFormat, xmlContent);

            return xmlContent.toString();
        } catch (Exception e) {
            throw new ApplicationDataException(ERROR_DATA);
        }
    }

    @SuppressWarnings("unchecked")
    public static <I> I unmarshalXmlStringToObject(String xmlString, Class<I> xmlFormatClass) {

        try {
            StringReader sr = new StringReader(xmlString);
            JAXBContext jaxbContext = JAXBContext.newInstance(xmlFormatClass);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            return (I) unmarshaller.unmarshal(sr);

        } catch (Exception e) {
            throw new ApplicationDataException(ERROR_DATA);
        }
    }

    //***** protected method *****
    //***** private method *****
    //***** call back method *****
    //***** getter and setter *****
}
