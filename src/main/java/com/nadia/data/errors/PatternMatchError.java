package com.nadia.data.errors;


public class PatternMatchError extends Exception {


    public static final String GROUP__COUNT_ERROR = "Group count does not match expected";
    public static final String REGION_MUNI_NOT_SET_ERROR = "Node and/or Municipality have not been set";



    public PatternMatchError(String msg){
        super(msg);
    }

    public PatternMatchError(Exception e){
        super(e);
    }
}
