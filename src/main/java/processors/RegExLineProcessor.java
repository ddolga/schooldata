package processors;

import api.RegExLineProcessorInterface;
import errors.PatternMatchError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegExLineProcessor implements RegExLineProcessorInterface {

    Logger logger = LoggerFactory.getLogger(RegExLineProcessor.class);

    private static final String TITLE_MATCH_STR = "OBLAST ([A-Z]*) OBSHCHINA ([A-Z]*)";
    private static final String DATE_MATCH_STR = "DATA ([0-9]{2}.[0-9]{2}.[0-9]{4})";
    private static final String ROW_MATCH_STR = "\\|([A-Z]*\\.?[A-Z\\s]*)\\|([0-9\\s]*)\\|([0-9\\s]*)\\|([0-9\\s]*)\\|([0-9\\s]*)\\|([0-9\\s]*)\\|([0-9\\s]*)\\|([0-9\\s]*)\\|([0-9\\s]*)\\|";

    private String region;
    private String municipality;
    private String date;


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

            String[] rowArr = new String[12];
            rowArr[0] = date;
            rowArr[1] = region;
            rowArr[2] = municipality;

            for (int i = 0; i < 9; i++) {
                rowArr[3 + i] = matcher.group(i + 1);
            }

            StringBuilder sb = new StringBuilder();
            for (int i = 0, il = rowArr.length; i < il; i++) {
                if (i > 0) {
                    sb.append(",");
                }
                sb.append(rowArr[i].trim());
            }

            return sb.toString();
        }
    };

    private LinePatternMatcher[] linePatternMatchers
            = {datePatternMatcher, titlePatternMatcher, rowPatternMatcher};


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
}
