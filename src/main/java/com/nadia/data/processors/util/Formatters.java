package com.nadia.data.processors.util;

public class Formatters {

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
