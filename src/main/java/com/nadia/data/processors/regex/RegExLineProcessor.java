package com.nadia.data.processors.regex;

import com.nadia.data.api.RegExLineProcessorInterface;
import com.nadia.data.errors.PatternMatchError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import static com.nadia.data.processors.regex.RegexPatterns.*;
import static com.nadia.data.util.Formatters.combineToRow;

@Component
public class RegExLineProcessor implements RegExLineProcessorInterface {

    Logger logger = LoggerFactory.getLogger(RegExLineProcessor.class);

    private String region;
    private String municipality;
    private String date;
    private int[] subtotal_check_arr = new int[8];
    private int row_count_check;
    private int row_id = -1;
    private int parent_id = 0;


    private LinePatternMatcher titlePatternMatcher = new LinePatternMatcher(TITLE_MATCH_STR,
            (matcher) -> {
                if (matcher.groupCount() != 2)
                    throw new PatternMatchError();

                region = matcher.group(1).trim();
                municipality = matcher.group(2).trim();

                return null;
            }
    );

    private LinePatternMatcher oblastPatternMatcher = new LinePatternMatcher(OBLAST_MATCH_STR,
            (matcher) -> {
                if (matcher.groupCount() != 1)
                    throw new PatternMatchError();
                region = matcher.group(1).trim();
                return null;
            });

    private LinePatternMatcher obshchinaPatternMatcher = new LinePatternMatcher(OBSHCHINA_MATCH_STR,
            (matcher) -> {

                if (matcher.groupCount() != 1)
                    throw new PatternMatchError();

                municipality = matcher.group(1).trim();

                return null;
            });

    private LinePatternMatcher datePatternMatcher = new LinePatternMatcher(DATE_MATCH_STR,
            (matcher) -> {
                if (matcher.groupCount() != 1)
                    throw new PatternMatchError();

                date = matcher.group(1);

                return null;
            });


    private LinePatternMatcher rowPatternMatcher = new LinePatternMatcher(ROW_MATCH_STR, (
            (matcher) -> {
                final int MAX_COLUMNS = 14;
                final int COL_CITY = 5;
                final int COL_START_NUMBERS = 6;
                final int MAX_GROUPS = 9;

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
    ));


    private LinePatternMatcher subRowPatternMatcher = new LinePatternMatcher(SUB_ROW_MATCH_STR, (
            (matcher) -> {
                final int MAX_COLUMNS = 15;
                final int COL_CITY = 5;
                final int MAX_GROUPS = 9;

                if (matcher.groupCount() != MAX_GROUPS)
                    throw new PatternMatchError();

                String[] rowArr = new String[MAX_COLUMNS];
                rowArr[0] = Integer.toString(++row_id);
                rowArr[1] = date;
                rowArr[2] = region;
                rowArr[3] = municipality;
                rowArr[4] = "NA";
                rowArr[MAX_COLUMNS - 1] = Integer.toString(parent_id);

                rowArr[COL_CITY] = matcher.group(1).trim();

                try {
                    for (int i = 1; i < MAX_GROUPS; i++) {
                        String val = matcher.group(i + 1).trim();
                        rowArr[COL_CITY + i] = val.isEmpty() ? "0" : val;
                    }

                    return combineToRow(rowArr);
                } catch (Exception e) {
                    throw new PatternMatchError();
                }
            }
    ));

    private LinePatternMatcher totalPatternMatcher = new LinePatternMatcher(TOTAL_MATCH_STR, (

            (matcher) -> {
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
    ));

    private LinePatternMatcher[] LinePatternMatcherAbstracts = {datePatternMatcher, oblastPatternMatcher,
            obshchinaPatternMatcher, titlePatternMatcher, rowPatternMatcher, subRowPatternMatcher,
            totalPatternMatcher};


    @Override
    public String process(String line) {

        for (LinePatternMatcher LinePatternMatcherAbstract : LinePatternMatcherAbstracts) {
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

}
