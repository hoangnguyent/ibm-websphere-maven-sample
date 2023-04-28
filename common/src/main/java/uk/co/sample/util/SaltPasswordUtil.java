package uk.co.sample.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import uk.co.sample.constant.CommonConstant;
import uk.co.sample.constant.SystemErrorCodeConstant;
import uk.co.sample.exception.ApplicationSystemException;

public class SaltPasswordUtil {

    private static final int    STRETCH_COUNT            = 1000;

    private static final String DIGEST_ALGORITHM_PATTERN = "SHA-256";
    private static final String FORMAT_PATTERN           = "%02x";

    //***** injection field *****
    //***** constructor *****
    private SaltPasswordUtil() {
        // Do nothing
    }
    //***** public method *****

    public static String getStretchedPassword(String password, String userId) {
        String salt = getSha256(userId);
        String hash = CommonConstant.EMPTY;
        for (int i = 0; i < STRETCH_COUNT; i++) {
            hash = getSha256(hash + salt + password);
        }
        return hash;
    }

    public static String getSaltPassword(String password, String userId) {
        String salt = getSha256(userId);
        return getSha256(salt + password);
    }

    //***** protected method *****
    //***** private method *****

    private static String getSha256(String target) {
        MessageDigest md = null;
        StringBuffer sb = new StringBuffer();
        try {
            md = MessageDigest.getInstance(DIGEST_ALGORITHM_PATTERN);
            md.update(target.getBytes());
            byte[] digest = md.digest();
            for (byte element : digest) {
                sb.append(String.format(FORMAT_PATTERN, element));
            }
        } catch (NoSuchAlgorithmException e) {
            throw new ApplicationSystemException(SystemErrorCodeConstant.ERROR_SYSTEM);
        }
        return sb.toString();
    }

    //***** getter and setter *****

}
