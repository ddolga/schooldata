package com.nadia.data.processors.RegExProcessor;

public class RegexPatterns {

    public static final String FIELD_DELIMETER = "\t";

    public static final String TITLE_MATCH_STR = "OBLAST ([A-Z]*) OBSHCHINA ([A-Z]*)";
    public static final String OBLAST_MATCH_STR = "OBLAST:(.*)T[\\s]+A[\\s]+B";
    public static final String OBSHCHINA_MATCH_STR = "OBSHCHINA:(.*)NA[\\s]NASIELIENIIETO";
    public static final String DATE_MATCH_STR = "DATA ([0-9]{2}.[0-9]{2}.[0-9]{4})";

    public static final String ROW_MATCH_STR = "[\\|!]\\s?(?!\\s?VSICHKO)(?!V T.CH.R-N)([A-Z].*?)[\\|!]([0-9\\s]*)" +
            "[\\|!]([0-9\\s]*)[\\|!]([0-9\\s]*)[\\|!]([0-9\\s]*)[\\|!]([0-9\\s]*)[\\|!]([0-9\\s]*)[\\|!]([0-9\\s]*)[\\|!]([0-9\\s]*)[\\|!]";


    public static final String SUB_ROW_MATCH_STR = "[\\|!]\\s?(V\\sT.CH.R-N\\s.+?)[\\|!]([0-9\\s]*)[\\|!]([0-9\\s]*)" +
            "[\\|!]([0-9\\s]*)[\\|!]([0-9\\s]*)[\\|!]([0-9\\s]*)[\\|!]([0-9\\s]*)[\\|!]([0-9\\s]*)[\\|!]([0-9\\s]*)[\\|!]";

    public static final String TOTAL_MATCH_STR = "[\\|!]\\s?(\\s?VSICHKO ZA OBSHCHINATA)[A-Z]*\\.?[A-Z\\s\\']*" +
            "[\\|!]([0-9\\s]*)[\\|!]([0-9\\s]*)[\\|!]([0-9\\s]*)[\\|!]([0-9\\s]*)" +
            "[\\|!]([0-9\\s]*)[\\|!]([0-9\\s]*)[\\|!]([0-9\\s]*)[\\|!]([0-9\\s]*)[\\|!]";


    public static final String CITY_MATCH_STR = "([\\w]{1,2})\\.(.*)";

}
