package uk.co.sample.security;

import static uk.co.sample.constant.CommonConstant.AD_AUTH_PROPERTY_FILE;

import uk.co.sample.util.PropertyUtil;

public class ADAuthConstant {

    public static final String   SERVER                = obtainSingleton().readProperty("servers");
    public static final String   DOMAIN_VALUE          = obtainSingleton().readProperty("domains.value");
    public static final String   DOMAIN_BACKUP         = obtainSingleton().readProperty("domain.BACKUP");
    public static final String   AUTHENTICATION_METHOD = obtainSingleton().readProperty("authentication.method");

    // ***** injection field *****
    // ***** constructor *****
    private ADAuthConstant() {

    }
    // ***** public method *****
    // ***** protected method *****
    // ***** private method *****
    private static PropertyUtil obtainSingleton() {
        return Holder.propertyUtil;
    }
    private static final class Holder {
        private static PropertyUtil propertyUtil = new PropertyUtil(AD_AUTH_PROPERTY_FILE);
    }
    // ***** getter and setter *****

}
