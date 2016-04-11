package com.nadia.data.importers.excel;

import com.nadia.data.api.CellProcessorInterface;
import com.nadia.data.api.WorkbookProcessInterface;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class CombineToCSV implements WorkbookProcessInterface {

    Logger logger = LoggerFactory.getLogger(CombineToCSV.class);

    private static final String FIELD_DELIENATOR = "\t";
    private static final String LINE_DELIENATOR = "\n";

    @Override
    public Workbook process(String inFileName, String outFileName, CellProcessorInterface cellProcessor,int limit) {

        try {
            Workbook wb = WorkbookFactory.create(new File(inFileName));
            logger.info("loaded: " + inFileName);
            FileOutputStream out = new FileOutputStream(outFileName);
            String line;
            Cell cell;

            for (Sheet sheet : wb) {
                for (Row row : sheet) {
                    line = "";
                    boolean isEmpty = true;
                    for (int i = 0; i < row.getLastCellNum() - 1; i++) {
                        cell = row.getCell(i);

                        String val = cellProcessor.processCell(cell);
                        isEmpty = isEmpty && (val == null || val.isEmpty());
                        line = line + val + FIELD_DELIENATOR;
                    }

                    cell = row.getCell(row.getLastCellNum() - 1);
                    String val = cellProcessor.processCell(cell);
                    isEmpty = isEmpty && val.isEmpty();
                    line = line + val + LINE_DELIENATOR;
                    if(!isEmpty){
                        out.write(line.getBytes());
                    }
                }
            }
            out.close();
            wb.close();
            return wb;
        } catch (IOException | InvalidFormatException e) {
            e.printStackTrace();
        }

        return null;
    }


}
