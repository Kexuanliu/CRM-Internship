/**
 * @author ZXL
 * @date 2019/7/19 17:49
 */
package com.xuebei.crm.utils;

import java.text.DecimalFormat;

public class CrmDoubleFormateUtils {
    private final static String EMPTY = "";

    public static String doubleFormat(Double input, String pattern) {
        if (input == null || input.equals(Double.NaN)) {
            return EMPTY;
        } else {
            DecimalFormat df = new DecimalFormat(pattern);
            return df.format(input);
        }
    }
}
