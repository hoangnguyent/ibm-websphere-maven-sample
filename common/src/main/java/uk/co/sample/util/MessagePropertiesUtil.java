package uk.co.sample.util;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class MessagePropertiesUtil {

    private static final String MESSAGE_PROPERTIES_FILE = "Messages";

    //***** constructor *****
    private MessagePropertiesUtil() {
        // Do nothing
    }

    //***** public method *****
    public static String getMessage(String key, List<String> placeholders, Locale locale) {

        String message = getMessage(key, locale);
        for (int i = 0; i < placeholders.size(); i++) {
            message = message.replace("{" + i + "}", placeholders.get(i));
        }

        return message;
    }

    public static String getMessage(String key, List<String> placeholders) {

        String message = getMessage(key);
        for (int i = 0; i < placeholders.size(); i++) {
            message = message.replace("{" + i + "}", placeholders.get(i));
        }

        return message;
    }

    public static String getMessage(String key, String placeHolder) {
        return getMessage(key, Arrays.asList(placeHolder));
    }

    public static String getMessage(String key) {
        return getMessage(key, Locale.JAPANESE);
    }

    public static String getMessage(String key, Locale locale) {
        ResourceBundle resourceBundle = getResourceBundle(locale);
        return resourceBundle.getString(key);
    }

    //***** protected method *****
    //***** private method *****
    /**
     * getResourceBundle
     * <br>
     * @param locale Locale
     * @return ResourceBundle
     */
    private static ResourceBundle getResourceBundle(Locale locale) {
        return PropertyResourceBundle.getBundle(MESSAGE_PROPERTIES_FILE, locale);
    }

    //***** call back method *****
    //***** getter and setter *****

}
