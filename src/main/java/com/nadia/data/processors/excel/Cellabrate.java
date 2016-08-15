package com.nadia.data.processors.excel;

import com.nadia.data.api.IFileIterator;
import com.nadia.data.api.IRowProcessor;
import com.nadia.data.processors.AbstractProcessor;
import com.nadia.data.util.Formatters;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.File;
import java.io.IOException;

public abstract class Cellabrate extends AbstractProcessor {


    public Cellabrate(IFileIterator fileIterator) {
        super(fileIterator);
    }

    public void process(String inFileName, String prefix, IRowProcessor rowProcessor) {

        String outFileName = Formatters.formatOutputFileName(inFileName, prefix);
        try {
            Workbook wb = WorkbookFactory.create(new File(inFileName));
            int startRow = params.getSkippedRows();
            for (Sheet sheet : wb) {
                sheet.createFreezePane(0, 0); //remove freeze panes
                for (int i = startRow; i < sheet.getLastRowNum(); i++) {
                    Row row = sheet.getRow(i);
                    rowProcessor.doRow(sheet, row);
                }
            }

            closeOut(outFileName, wb);

        } catch (IOException | InvalidFormatException e) {
            e.printStackTrace();
        }
    }

}
