package com.nadia.data.api;

import com.nadia.data.BaseImporter;

public interface ParametersInterface {
    int getType();

    String[] getFa();

    String getTargetFileName();

    String getHeader();

    boolean hasHeader();

    String getImporterType();
}
