package processors;

import api.RegExLineProcessorInterface;
import errors.PatternMatchError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegExLineProcessor implements RegExLineProcessorInterface {

    Logger logger = LoggerFactory.getLogger(RegExLineProcessor.class);

    private static final String FIELD_DELIMETER = "\t";

    private static final String TITLE_MATCH_STR = "OBLAST ([A-Z]*) OBSHCHINA ([A-Z]*)";
    private static final String OBLAST_MATCH_STR = "OBLAST:(.*)T[\\s]+A[\\s]+B";
    private static final String OBSHCHINA_MATCH_STR = "OBSHCHINA:(.*)NA[\\s]NASIELIENIIETO";
    private static final String DATE_MATCH_STR = "DATA ([0-9]{2}.[0-9]{2}.[0-9]{4})";

    private static final String ROW_MATCH_STR = "\\|\\s?(?!\\s?VSICHKO)(?!V T.CH.R-N)([A-Z].*?)\\|([0-9\\s]*)" +
            "\\|([0-9\\s]*)\\|([0-9\\s]*)\\|([0-9\\s]*)\\|([0-9\\s]*)\\|([0-9\\s]*)\\|([0-9\\s]*)\\|([0-9\\s]*)\\|";

    private static final String SUB_ROW_MATCH_STR = "\\|\\s?(V\\sT.CH.R-N\\s.+?)\\|([0-9\\s]*)\\|([0-9\\s]*)" +
            "\\|([0-9\\s]*)\\|([0-9\\s]*)\\|([0-9\\s]*)\\|([0-9\\s]*)\\|([0-9\\s]*)\\|([0-9\\s]*)\\|";

    private static final String TOTAL_MATCH_STR = "\\|\\s?(\\s?VSICHKO ZA OBSHCHINATA)[A-Z]*\\.?[A-Z\\s\\']*" +
            "\\|([0-9\\s]*)\\|([0-9\\s]*)\\|([0-9\\s]*)\\|([0-9\\s]*)" +
            "\\|([0-9\\s]*)\\|([0-9\\s]*)\\|([0-9\\s]*)\\|([0-9\\s]*)\\|";

    private String region;
    private String municipality;
    private String date;
    private int[] subtotal_check_arr = new int[8];
    private int row_count_check;
    private int row_id = -1;
    private int parent_id = 0;

    abstract class LinePatternMatcher {

        Pattern pattern;
        Matcher matcher;

        public LinePatternMatcher(String regEx) {
            pattern = Pattern.compile(regEx);
        }

        public boolean match(String line) {
            matcher = pattern.matcher(line);
            return matcher.find();
        }

        abstract public String parse() throws PatternMatchError;

    }

    private LinePatternMatcher titlePatternMatcher = new LinePatternMatcher(TITLE_MATCH_STR) {
        @Override
        public String parse() throws PatternMatchError {

            if (matcher.groupCount() != 2)
                throw new PatternMatchError();

            region = matcher.group(1);
            municipality = matcher.group(2);

            return null;
        }
    };

    private LinePatternMatcher oblastPatternMatcher = new LinePatternMatcher(OBLAST_MATCH_STR) {
        @Override
        public String parse() throws PatternMatchError {

            if (matcher.groupCount() != 1)
                throw new PatternMatchError();

            region = matcher.group(1);

            return null;
        }
    };

    private LinePatternMatcher obshchinaPatternMatcher = new LinePatternMatcher(OBSHCHINA_MATCH_STR) {
        @Override
        public String parse() throws PatternMatchError {

            if (matcher.groupCount() != 1)
                throw new PatternMatchError();

            municipality = matcher.group(1);

            return null;
        }
    };

    private LinePatternMatcher datePatternMatcher = new LinePatternMatcher(DATE_MATCH_STR) {
        @Override
        public String parse() throws PatternMatchError {

            if (matcher.groupCount() != 1)
                throw new PatternMatchError();

            date = matcher.group(1);

            return null;
        }
    };

    private LinePatternMatcher rowPatternMatcher = new LinePatternMatcher(ROW_MATCH_STR) {
        @Override
        public String parse() throws PatternMatchError {

            if (matcher.groupCount() != 9)
                throw new PatternMatchError();

            String[] rowArr = new String[13];
            rowArr[0] = Integer.toString(++row_id);
            rowArr[1] = date;
            rowArr[2] = region;
            rowArr[3] = municipality;
            parent_id = row_id;

            try {
                for (int i = 0; i < 9; i++) {
                    rowArr[4 + i] = matcher.group(i + 1).trim();
                }

                for (int j = 0; j < subtotal_check_arr.length; j++) {
                    String str = rowArr[5 + j];
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


    private LinePatternMatcher subRowPatternMatcher = new LinePatternMatcher(SUB_ROW_MATCH_STR) {
        @Override
        public String parse() throws PatternMatchError {

            if (matcher.groupCount() != 9)
                throw new PatternMatchError();

            String[] rowArr = new String[14];
            rowArr[0] = Integer.toString(++row_id);
            rowArr[1] = date;
            rowArr[2] = region;
            rowArr[3] = municipality;
            rowArr[13] = Integer.toString(parent_id);

            try {
                for (int i = 0; i < 9; i++) {
                    rowArr[4 + i] = matcher.group(i + 1).trim();
                }

                return combineToRow(rowArr);
            } catch (Exception e) {
                throw new PatternMatchError();
            }
        }
    };

    private LinePatternMatcher totalPatternMatcher = new LinePatternMatcher(TOTAL_MATCH_STR) {
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

    private LinePatternMatcher[] linePatternMatchers = {datePatternMatcher, oblastPatternMatcher,
            obshchinaPatternMatcher, titlePatternMatcher, rowPatternMatcher, subRowPatternMatcher,
            totalPatternMatcher};


    @Override
    public String process(String line) {

        for (LinePatternMatcher linePatternMatcher : linePatternMatchers) {
            if (linePatternMatcher.match(line)) {
                try {
                    return linePatternMatcher.parse();
                } catch (PatternMatchError patternMatchError) {
                    logger.error("Error at line:" + line);
                }
            }
        }

        return null;
    }


    private String combineToRow(String[] rowArr) {
        return String.join(FIELD_DELIMETER, rowArr);
    }
}
