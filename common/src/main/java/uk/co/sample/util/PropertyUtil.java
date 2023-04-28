package uk.co.sample.util;

import static uk.co.sample.constant.CommonConstant.DOT;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.commons.lang3.math.NumberUtils;

import uk.co.sample.constant.SystemErrorCodeConstant;
import uk.co.sample.exception.ApplicationSystemException;

public class PropertyUtil {

    private final ResourceBundle bundle;

    public PropertyUtil(final String propertyName) {
        try {
            bundle = ResourceBundle.getBundle(propertyName);
        } catch (Exception e) {
            throw new ApplicationSystemException(SystemErrorCodeConstant.ERROR_LOAD_PROPERTY, new String[] { propertyName });
        }
    }

    public PropertyUtil(final String propertyName, final String languageCode) {
        try {
            if (languageCode == null || languageCode.matches("\\s*")) {
                bundle = ResourceBundle.getBundle(propertyName);
                return;
            }
            bundle = ResourceBundle.getBundle(propertyName, new Locale(languageCode));
        } catch (Exception e) {
            throw new ApplicationSystemException(SystemErrorCodeConstant.ERROR_LOAD_PROPERTY, new String[] { propertyName });
        }
    }

    public PropertyUtil(final String propertyName, final Locale locale) {
        try {
            bundle = ResourceBundle.getBundle(propertyName, locale);
        } catch (Exception e) {
            throw new ApplicationSystemException(SystemErrorCodeConstant.ERROR_LOAD_PROPERTY, new String[] { propertyName });
        }
    }

    /**
     * read property.
     * <br>
     * @param key property key
     * @param defaultName default name for return.
     * @return if exist property value then property value else defalutName.
     */
    public String readProperty(final String key, final String defaultName) {
        if (bundle == null) {
            return defaultName;
        }
        try {
            final String readName = bundle.getString(key);
            if (readName == null || readName.length() == 0) {
                return defaultName;
            }
            return readName;
        } catch (RuntimeException e) {
            return defaultName;
        }
    }

    /**
     * read property.<br>
     * if property is not exists, throw exception.
     * @param key property key
     * @return value String
     * @throws ApplicationSystemException - if key or property is not exists.
     */
    public String readProperty(final String key) {
        try {
            return bundle.getString(key);
        } catch (MissingResourceException e) {
            throw new ApplicationSystemException(SystemErrorCodeConstant.ERROR_READ_PROPERTY, new String[] { key });
        }
    }

    /**
     * readNumberProperty.<br>
     * if property is not exists, throw exception.
     * if property is not Number, throw exception.
     * @param key property key
     * @return value String
     * @throws ApplicationSystemException - if key or property is not exists.
     */
    public BigDecimal readNumberProperty(final String key) {
        try {
            String strValue = bundle.getString(key);
            if (!NumberUtils.isCreatable(strValue)) {
                throw new ApplicationSystemException(SystemErrorCodeConstant.ERROR_READ_PROPERTY, new String[] { key });
            }

            return NumberUtils.createBigDecimal(strValue);

        } catch (MissingResourceException e) {
            throw new ApplicationSystemException(SystemErrorCodeConstant.ERROR_READ_PROPERTY, new String[] { key });
        }
    }

    /**
     * read property.
     * <br>
     * key is concat prefix, SEPALETOR, key.
     * @param prefix key prefix
     * @param key property key
     * @param defaultName default name for return.
     * @return if exist property value then property value else defalutName.
     */
    public String readProperty(final String prefix, final String key, final String defaultName) {
        return readProperty(prefix + DOT + key, defaultName);
    }

    /**
     * read property.
     * <br>
     * key is concat prefix, SEPALETOR, key.
     * @param prefix1 key first prefix
     * @param prefix2 key second prefix
     * @param key property key
     * @param defaultName default name for return.
     * @return if exist property value then property value else defalutName.
     */
    public String readProperty(final String prefix1, final String prefix2, final String key, final String defaultName) {
        return readProperty(prefix1 + DOT + prefix2 + DOT + key, defaultName);
    }

    public ResourceBundle getBundle() {
        return bundle;
    }
}
