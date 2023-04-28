package uk.co.sample.util;

import uk.co.sample.constant.CommonConstant;

/**
 * <H3>
 *  Convert DB Field Name type String to Java variable type String.
 * </H3>
 */
public class DBFieldNameConvertUtil {

    //***** Constructor *****
    private DBFieldNameConvertUtil() {
        // Do nothing
    }
    //***** Public method *****
    /**
     * Convert String from X_YY_ZZZ (DB Field Name type) to xYyZzz (DB Field Name type).<br>
     *
     * @param dbFieldName String DB field name
     * @return String of Java variable name.
     */
    public static String getVariableName(String dbFieldName) {

        // replace the first character to lower case
        return formatLowerHead(getClassVariable(dbFieldName));
    }

    /**
     * Convert String from X_YY_ZZZ (DB Field Name type) to XYyZzz (Java class type).<br>
     *
     * @param dbFieldName String DB field name
     * @return resultName String Java class name
     */
    public static String getClassVariable(String dbFieldName) {
        String resultName = dbFieldName;
        resultName = replace(resultName, " ", "_");
        resultName = replace(resultName, "__", "_");

        // trim '_'
        resultName = trimLetter(resultName);

        return resultName;
    }

    /**
     * Convert String from X_YY_ZZZ (DB Field Name type) to x.yy.zzz (using for resource).<br>
     *
     * @param dbFieldName String DB field name
     * @return resultName String label name
     */
    public static String getLabelName(String dbFieldName) {
        String resultName = dbFieldName;
        resultName = replace(resultName, " ", "_");
        resultName = replace(resultName, "__", "_");

        // replace '_' with '.'
        resultName = replace(resultName, "_", CommonConstant.DOT);

        // replace the to lower case
        resultName = resultName.toLowerCase();

        return resultName;
    }

    /**
     * Convert String from X_YY_DNAME or X_YY_EN_NAME (DB Field Name type) to X_YY_NAME (DB Field Name type)<br>
     * or Convert String from X_YY_DTEXT or X_YY_EN_TEXT (DB Field Name type) to X_YY_NAME X_YY_TEXT (DB Field Name type).<br>
     *
     * @param dbFieldName String DB field name
     * @return String of solved name.
     */
    public static String getSolvedName(String dbFieldName) {
        String name;
        String suffix;

        // Get "NAME" length with "_DNAME" or "EN_NAME".
        suffix = "_NAME";
        name = "_EN_NAME";
        if (checkEndWith(dbFieldName, name)) {
            return dbFieldName.substring(0, dbFieldName.length() - name.length()) + suffix;
        }

        name = "_DNAME";
        if (checkEndWith(dbFieldName, name)) {
            return dbFieldName.substring(0, dbFieldName.length() - name.length()) + suffix;
        }

        // Get "TEXT" length with "_DTEXT" or "EN_TEXT".
        suffix = "_TEXT";
        name = "_EN_TEXT";
        if (checkEndWith(dbFieldName, name)) {
            return dbFieldName.substring(0, dbFieldName.length() - name.length()) + suffix;
        }

        name = "_DTEXT";
        if (checkEndWith(dbFieldName, name)) {
            return dbFieldName.substring(0, dbFieldName.length() - name.length()) + suffix;
        }
        return dbFieldName;
    }

    //***** Private method *****
    /**
     * Trim '_'.<br>
     * Example : Convert from x_yy_zzz to XYyZzz.
     *
     * @param str String
     * @return str String converted string
     */
	private static String trimLetter(String str) {

        int found = str.indexOf('_');
        if (found > 0) {
            // recursive call to process string before '_' and after.
            return trimLetter(str.substring(0, found)) + trimLetter(str.substring(found + 1, str.length()));
        } else {
            if (str.length() > 1) {
                // convert the first character to upper case and others to lower
                // case
                return str.substring(0, 1).toUpperCase() + str.substring(1, str.length()).toLowerCase();
            } else {
                return str.toUpperCase();
            }
        }
    }

	/**
	 * Replace the first character to lower case.<br>
	 *
	 * @param str String
	 * @return str String the string which of head 1 byte lowered.
	 */
    private static String formatLowerHead(String str) {

        if (str.length() > 1) {
            return str.substring(0, 1).toLowerCase() + str.substring(1, str.length());
        }

        return str;
    }

    /**
     * Replace String.<br>
     *
     * @param str String
     * @param from String
     * @param to String
     * @return s String replaced string.
     */
    private static String replace(String str, String from, String to) {

        String s = str;
        while (s.indexOf(from) > 0) {
            s = s.substring(0, s.indexOf(from)) + to
                    + s.substring(s.indexOf(from) + to.length(), s.length());
        }
        return s;
    }

    /**
     * Compare end of string with suffix.<br>
     *
     * @param src String
     * @param suffix String
     * @return true : if src ends with suffix. / false : if not.
     */
    private static boolean checkEndWith(String src, String suffix) {
        int found = src.indexOf(suffix);
        return src.length() == found + suffix.length();
    }
}
