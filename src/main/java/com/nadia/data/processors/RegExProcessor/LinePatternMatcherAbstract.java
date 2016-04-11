package com.nadia.data.processors.RegExProcessor;

import com.nadia.data.errors.PatternMatchError;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class LinePatternMatcherAbstract {


    Pattern pattern;
    Matcher matcher;

    public LinePatternMatcherAbstract(String regEx) {
        pattern = Pattern.compile(regEx);
    }

    public boolean match(String line) {
        matcher = pattern.matcher(line);
        return matcher.find();
    }

    abstract public String parse() throws PatternMatchError;



}
