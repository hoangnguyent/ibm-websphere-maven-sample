package uk.co.sample.util;

import static uk.co.sample.constant.CommonConstant.COMMA;

public class DBFieldNameUtil {

    private static final String PROPERTIES = "uk.co.sample.db.DBFieldName";

    // ***** Constructor *****
    private DBFieldNameUtil() {
        // Do nothing
    }
    // ***** Public method *****
    public static int getNumberOfFields(final String tableName) {
        return obtainSingleton().readProperty(tableName).split(COMMA).length;
    }

    public static int getSequenceOfFieldName(final String tableName, final String fieldName) {

        String definition = obtainSingleton().readProperty(tableName);

        return getSequence(definition, fieldName);
    }

    public static int getSequence(final String fieldNamesListText, final String fieldName) {

        String[] fields = fieldNamesListText.split(COMMA);

        return isContains(fields, fieldName);
    }

    // ***** Protected method *****
    // ***** Private method *****

    private static PropertyUtil obtainSingleton() {
        return Holder.propertyUtil;
    }

    private static final class Holder {
        private static PropertyUtil propertyUtil = new PropertyUtil(PROPERTIES);
    }

    private static int isContains(final String[] fields, final String fieldName) {
        for (int i = 0; i < fields.length; i++) {
            if ((fields[i].trim()).equals(fieldName)) {
                return i;
            }
        }
        return -1;
    }

    // ***** call back methods *****
    // ***** getter and Setter *****

}
