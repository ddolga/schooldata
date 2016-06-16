package com.nadia.data.api;

import com.nadia.data.util.Parameters;

import java.io.FileNotFoundException;

public interface IProcessFile {

    void setup() throws FileNotFoundException;

    void process(String inFileName);

    void cleanUp();
}
