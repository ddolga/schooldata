package com.nadia.data.api;


public interface FormProcessInterface {
    void process(String inFileName, int limit);
    void cleanUp();
}
