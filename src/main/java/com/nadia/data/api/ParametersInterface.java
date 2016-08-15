package com.nadia.data.api;

import java.io.FileNotFoundException;

public interface ParametersInterface {

    String[] getFa();

    String getTargetFileName() throws FileNotFoundException;

    String getHeader();

    boolean hasHeader();

    String getImporterType();

    int getSkippedRows();
}
