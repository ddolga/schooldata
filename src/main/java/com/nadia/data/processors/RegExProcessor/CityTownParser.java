package com.nadia.data.processors.RegExProcessor;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CityTownParser {

    private String city;
    private String type;

    private static Pattern pattern = Pattern.compile(RegexPatterns.CITY_MATCH_STR);

    public CityTownParser(String type, String city) {
        this.city = city;
        this.type = type;
    }

    public static CityTownParser filterCityOrTownField(String str) {

        String type;
        String city;

        Matcher m = pattern.matcher(str);
        if (m.find() && m.groupCount() == 2) {
            String ct = m.group(1);
            if (ct.equals("GR"))
                type = "Town";
            else if (ct.equals("S"))
                type = "Village";
            else type = "Unknown";

            city = m.group(2);

            return new CityTownParser(type, city);
        }

        return new CityTownParser(null, str);
    }

    public String getCity() {
        return city;
    }

    public String getType() {
        return type;
    }
}
