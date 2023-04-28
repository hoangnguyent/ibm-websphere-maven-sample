package uk.co.sample.ws.util;

import static uk.co.sample.constant.SystemErrorCodeConstant.ERROR_LOGIC;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.EntityResolver;

import uk.co.sample.constant.CommonConstant;
import uk.co.sample.constant.SystemErrorCodeConstant;
import uk.co.sample.exception.ApplicationLogicException;
import uk.co.sample.util.MessagePropertiesUtil;
import uk.co.sample.util.PropertyUtil;
import uk.co.sample.ws.base.ErrorData;

public class ErrorDataConvertUtil {

    private static final PropertyUtil PROPERTIES    = new PropertyUtil("Messages");
    private static final String       WS_ERROR_CODE = "E00006JP";

    //***** constructor *****
    private ErrorDataConvertUtil() {
        // Do nothing
    }
    //***** public method *****

    /**
     * return Set&lt;ErrorData&gt; of ErrorData<br>
     * @param messageDataText formatted message
     * @return Set&lt;ErrorData&gt;
     */
    public static Set<ErrorData> convertStringToErrorData(String messageDataText) {
        Set<ErrorData> result = new LinkedHashSet<>();
        if (StringUtils.isBlank(messageDataText)) {
            return result;
        }
        Map<String, String> errorMap = doParse(messageDataText);
        Map<String, String[]> errorArgsMap = doParseToArgsMap(messageDataText);

        for (Map.Entry<String, String> _entry : errorMap.entrySet()) {
            String code = _entry.getKey();
            String resourceMessage = PROPERTIES.readProperty(code, null);
            if (resourceMessage != null) {
                result.add(new ErrorData(code, errorArgsMap.get(code)));
            } else {
                result.add(new ErrorData(WS_ERROR_CODE, _entry.getValue()));
            }
        }
        return result;
    }

    /**
     * return formatted error message for WebService.<br>
     * @param errorDataSet Set&lt;ErrorData&gt;
     * @return String
     */
    public static String convertErrorDataToString(Set<ErrorData> errorDataSet) {

        if (errorDataSet.isEmpty()) {
            return CommonConstant.EMPTY;
        }

        StringBuilder result = new StringBuilder();
        result.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
        result.append("<errors>");
        for (ErrorData _errorData : errorDataSet) {

            String code = _errorData.getErrorCode();
            String dmsg = MessagePropertiesUtil.getMessage(code, Arrays.asList(_errorData.getParams()));
            if (StringUtils.isBlank(dmsg)) {
                code = SystemErrorCodeConstant.ERROR_READ_PROPERTY;
                dmsg = MessagePropertiesUtil.getMessage(SystemErrorCodeConstant.ERROR_READ_PROPERTY, Arrays.asList(code));
            }
            result.append("<error code=\"").append(code).append("\" args=\"").append(convertToArgsText(_errorData)).append("\">");
            result.append(escapeSimpleXML(dmsg));
            result.append("</error>");
        }
        result.append("</errors>");
        return result.toString();
    }

    //***** protected method *****
    //***** private method *****
    private static String convertToArgsText(ErrorData errorData) {
        String src = StringUtils.join(errorData.getParams(), "|");
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < src.length(); i++) {
            char c = src.charAt(i);
            switch (c) {
            case '"':
                result.append("&quot;");
                break;
            case '&':
                result.append("&amp;");
                break;

            default:
                result.append(c);
                break;
            }
        }
        return result.toString();

    }

    private static String escapeSimpleXML(String src) {
        if (src == null) {
            return null;
        }
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < src.length(); i++) {
            char c = src.charAt(i);
            switch (c) {
            case '<':
                result.append("&lt;");
                break;
            case '>':
                result.append("&gt;");
                break;
            case '&':
                result.append("&amp;");
                break;

            default:
                result.append(c);
                break;
            }
        }
        return result.toString();
    }

    private static Map<String, String> doParse(String messageDataText) {
        if (messageDataText == null || messageDataText.length() == 0) {
            return Collections.emptyMap();
        }

        Map<String, String> result = new LinkedHashMap<>();

        Document doc = buildDocument(messageDataText);
        NodeList nl = doc.getElementsByTagName("error");
        if (null == nl) {
            return result;
        }

        for (int i = 0; i < nl.getLength(); i++) {
            Element elem = (Element) nl.item(i);
            String code = elem.getAttribute("code");
            String text = elem.getTextContent();
            result.put(code, text);
        }
        return result;
    }

    private static Map<String, String[]> doParseToArgsMap(String messageDataText) {
        if (messageDataText == null || messageDataText.length() == 0) {
            return Collections.emptyMap();
        }

        Map<String, String[]> result = new LinkedHashMap<>();

        Document doc = buildDocument(messageDataText);
        NodeList nl = doc.getElementsByTagName("error");

        for (int i = 0; i < nl.getLength(); i++) {
            Element elem = (Element) nl.item(i);
            String code = elem.getAttribute("code");
            if (elem.hasAttribute("args")) {
                String argsText = elem.getAttribute("args");
                String[] args = argsText.split("\\|");
                result.put(code, args);
            } else {
                result.put(code, new String[0]);
            }
        }
        return result;
    }

    private static Document buildDocument(String messageDataText) {
        byte[] bytes = messageDataText.getBytes(StandardCharsets.UTF_8);
        try (
            InputStream in = new ByteArrayInputStream(bytes);) {
            return buildDocument(in);
        } catch (IOException e) {
            return createEmptyDocument();
        }
    }

    private static Document buildDocument(InputStream inputStream) {
        return buildDocument(inputStream, null);
    }

    private static Document buildDocument(InputStream inputStream, EntityResolver entityResolver) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
            DocumentBuilder builder = factory.newDocumentBuilder();
            if (entityResolver != null) {
                builder.setEntityResolver(entityResolver);
            }
            return builder.parse(inputStream);
        } catch (Exception ex) {
            throw new ApplicationLogicException(ERROR_LOGIC, ex);
        }
    }

    private static Document createEmptyDocument() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
            DocumentBuilder builder = factory.newDocumentBuilder();
            return builder.newDocument();
        } catch (Exception ex) {
            throw new ApplicationLogicException(ERROR_LOGIC, ex);
        }
    }
    //***** call back method *****
    //***** getter and setter *****

}
