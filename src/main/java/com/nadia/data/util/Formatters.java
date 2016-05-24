package com.nadia.data.util;

import com.nadia.data.errors.PatternMatchError;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Formatters {

    public static final String FIELD_DELIMETER = ",";

    public static String combineToRow(String[] rowArr) throws PatternMatchError {

        String[] cArr = new String[rowArr.length];
        Pattern pattern = Pattern.compile("([\\d]{2})\\.([\\d]{2})\\.([\\d]{4})");
        for (int i = 0, len = rowArr.length; i < len; i++) {
            String v = rowArr[i];
            switch (getStrType(v)) {
                case VDATE:
                    Matcher m = pattern.matcher(v);
                    if (m.find() && m.groupCount() == 3) {
                        cArr[i] = m.group(3) + "-" + m.group(2) + "-" + m.group(1);
                    } else {
                        throw new PatternMatchError();
                    }
                    break;
                case VSTRING:
                    cArr[i] = "\"" + v + "\"";
                    break;
                case VINTEGER:
                    cArr[i] = v;
                    break;
                case VDECIMAL:
                    DecimalFormat df = new DecimalFormat("#.##");
                    cArr[i] = df.format(Double.parseDouble(v));
                    break;
                case NONE:
                    cArr[i] = "";
                    break;
                default:
                    cArr[i] = v;
            }
        }

        return String.join(FIELD_DELIMETER, cArr);
    }

    public enum ValueType {VSTRING, VDATE, VINTEGER, VDECIMAL, NONE}

    public static String formatOutputFileName(String name, String suffix) {
        String ext = name.substring(name.lastIndexOf(".") + 1);
        return formatOutputFileName(name, suffix, ext);
    }

    public static String formatOutputFileName(String name, String suffix, String ext) {
        String baseName = name.substring(0, name.lastIndexOf("."));
        return baseName + "_" + suffix + "." + ext;
    }

    public static String[] splitFiles(String s) {
        String[] names = s.split("\\,[\\s]*");
        return names;
    }

    public static ValueType getStrType(String str) {

        if (str == null || str.isEmpty())
            return ValueType.NONE;


        if (!str.matches("[\\d\\.]+"))
            return ValueType.VSTRING;

        if (str.matches("([\\d]{2})\\.([\\d]{2})\\.([\\d]{4})")) {
            return ValueType.VDATE;
        }

        if (str.matches("[\\.]"))
            return ValueType.VINTEGER;

        return ValueType.VDECIMAL;
    }

}
