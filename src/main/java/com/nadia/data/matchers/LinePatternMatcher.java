package com.nadia.data.matchers;

import com.nadia.data.errors.PatternMatchError;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LinePatternMatcher {

    Pattern pattern;
    Matcher matcher;


    @FunctionalInterface
    interface IParser {
        String[] regexParse(Matcher matcher) throws PatternMatchError;
    }

    private IParser parser;

    public LinePatternMatcher(String regEx, IParser parser) {
        this.parser = parser;
        pattern = Pattern.compile(regEx);
    }

    public boolean match(String line) {
        matcher = pattern.matcher(line);
        return matcher.find();
    }

    public String[] parse() throws PatternMatchError {
        return parser.regexParse(matcher);
    }

}
