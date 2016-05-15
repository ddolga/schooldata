package com.nadia.data.api;

import org.apache.poi.ss.usermodel.Workbook;

public interface WorkbookProcessInterface {

    Workbook process(String inFileName, CellProcessorInterface cellProcessor, int limit);

    void cleanUp();
}
