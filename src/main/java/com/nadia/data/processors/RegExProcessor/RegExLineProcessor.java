package com.nadia.data.processors.RegExProcessor;

import com.nadia.data.MunicipalityCleaner;
import com.nadia.data.Util;
import com.nadia.data.api.RegExLineProcessorInterface;
import com.nadia.data.errors.PatternMatchError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.nadia.data.processors.RegExProcessor.RegexPatterns.*;

public class RegExLineProcessor implements RegExLineProcessorInterface {

    Logger logger = LoggerFactory.getLogger(RegExLineProcessor.class);

    private String region;
    private String municipality;
    private String date;
    private int[] subtotal_check_arr = new int[8];
    private int row_count_check;
    private int row_id = -1;
    private int parent_id = 0;


    private LinePatternMatcherAbstract titlePatternMatcher = new LinePatternMatcherAbstract(TITLE_MATCH_STR) {
        @Override
        public String parse() throws PatternMatchError {

            if (matcher.groupCount() != 2)
                throw new PatternMatchError();

            region = matcher.group(1).trim();
            municipality = matcher.group(2).trim();

            return null;
        }
    };

    private LinePatternMatcherAbstract oblastPatternMatcher = new LinePatternMatcherAbstract(OBLAST_MATCH_STR) {
        @Override
        public String parse() throws PatternMatchError {

            if (matcher.groupCount() != 1)
                throw new PatternMatchError();

            region = matcher.group(1).trim();

            return null;
        }
    };

    private LinePatternMatcherAbstract obshchinaPatternMatcher = new LinePatternMatcherAbstract(OBSHCHINA_MATCH_STR) {
        @Override
        public String parse() throws PatternMatchError {

            if (matcher.groupCount() != 1)
                throw new PatternMatchError();

            municipality = matcher.group(1).trim();

            return null;
        }
    };

    private LinePatternMatcherAbstract datePatternMatcher = new LinePatternMatcherAbstract(DATE_MATCH_STR) {
        @Override
        public String parse() throws PatternMatchError {

            if (matcher.groupCount() != 1)
                throw new PatternMatchError();

            date = matcher.group(1);

            return null;
        }
    };


    private LinePatternMatcherAbstract rowPatternMatcher = new LinePatternMatcherAbstract(ROW_MATCH_STR) {


        private final int MAX_COLUMNS = 14;
        private final int COL_CITY = 5;
        private final int COL_START_NUMBERS = 6;
        private final int MAX_GROUPS = 9;


        @Override
        public String parse() throws PatternMatchError {

            if (matcher.groupCount() != MAX_GROUPS)
                throw new PatternMatchError();

            String[] rowArr = new String[MAX_COLUMNS];
            rowArr[0] = Integer.toString(++row_id);
            rowArr[1] = date;
            rowArr[2] = region;
            rowArr[3] = municipality;
            parent_id = row_id;

            try {

                CityTownParser cp = CityTownParser.filterCityOrTownField(matcher.group(1).trim());
                rowArr[COL_CITY - 1] = cp.getType();
                rowArr[COL_CITY] = cp.getCity();

                for (int i = 1; i < MAX_GROUPS; i++) {
                    rowArr[COL_CITY + i] = matcher.group(i + 1).trim();
                }

                for (int j = 0; j < subtotal_check_arr.length; j++) {
                    String str = rowArr[COL_START_NUMBERS + j];
                    if (!str.isEmpty()) {
                        subtotal_check_arr[j] += Integer.parseInt(str);
                    }
                }
                row_count_check++;

                return combineToRow(rowArr);
            } catch (Exception e) {
                throw new PatternMatchError();
            }
        }
    };


    private LinePatternMatcherAbstract subRowPatternMatcher = new LinePatternMatcherAbstract(SUB_ROW_MATCH_STR) {

        private final int MAX_COLUMNS = 14;
        private final int COL_CITY = 5;
        private final int MAX_GROUPS = 9;

        @Override
        public String parse() throws PatternMatchError {

            if (matcher.groupCount() != MAX_GROUPS)
                throw new PatternMatchError();

            String[] rowArr = new String[MAX_COLUMNS];
            rowArr[0] = Integer.toString(++row_id);
            rowArr[1] = date;
            rowArr[2] = region;
            rowArr[3] = municipality;
            rowArr[13] = Integer.toString(parent_id);

            rowArr[COL_CITY] = matcher.group(1).trim();

            try {
                for (int i = 1; i < MAX_GROUPS; i++) {
                    rowArr[COL_CITY + i] = matcher.group(i + 1).trim();
                }

                return combineToRow(rowArr);
            } catch (Exception e) {
                throw new PatternMatchError();
            }
        }
    };

    private LinePatternMatcherAbstract totalPatternMatcher = new LinePatternMatcherAbstract(TOTAL_MATCH_STR) {
        @Override
        public String parse() throws PatternMatchError {

            if (matcher.groupCount() != 9)
                throw new PatternMatchError();

            int[] totals = new int[subtotal_check_arr.length];
            for (int j = 0; j < totals.length; j++) {
                String col = matcher.group(j + 2).trim();
                totals[j] = Integer.parseInt(col);
            }

            boolean match = true;
            for (int i = 0; i < subtotal_check_arr.length; i++) {
                match = match && (subtotal_check_arr[i] == totals[i]);
            }
            if (!match)
                logger.error(String.format("Totals did not match: %s, %s: %s", region, municipality, row_count_check));

            for (int i = 0; i < subtotal_check_arr.length; i++) {
                subtotal_check_arr[i] = 0;
            }
            row_count_check = 0;

            return null;
        }
    };

    private LinePatternMatcherAbstract[] LinePatternMatcherAbstracts = {datePatternMatcher, oblastPatternMatcher,
            obshchinaPatternMatcher, titlePatternMatcher, rowPatternMatcher, subRowPatternMatcher,
            totalPatternMatcher};


    @Override
    public String process(String line) {

        for (LinePatternMatcherAbstract LinePatternMatcherAbstract : LinePatternMatcherAbstracts) {
            if (LinePatternMatcherAbstract.match(line)) {
                try {
                    return LinePatternMatcherAbstract.parse();
                } catch (PatternMatchError patternMatchError) {
                    logger.error("Error at line:" + line);
                }
            }
        }

        return null;
    }


    private String combineToRow(String[] rowArr) throws PatternMatchError {

        String[] cArr = new String[rowArr.length];
        Pattern pattern = Pattern.compile("([\\d]{2})\\.([\\d]{2})\\.([\\d]{4})");
        for (int i = 0, len = rowArr.length; i < len; i++) {
            String v = rowArr[i];
            switch (Util.getStrType(v)) {
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
                case VDECIMAL:
                    cArr[i] = v;
                    break;
                case NONE:
                    cArr[i] = "";
                    break;
                default:
                    cArr[i] = v;
            }
        }

        return String.join(MunicipalityCleaner.FIELD_DELIMETER, cArr);
    }
}
