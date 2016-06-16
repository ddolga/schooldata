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
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public abstract class Cellabrate extends AbstractProcessor {


    public Cellabrate(IFileIterator fileIterator) {
        super(fileIterator);
    }

    public void process(String inFileName, String prefix, IRowProcessor rowProcessor) {

        String outFileName = Formatters.formatOutputFileName(inFileName, prefix);
        try {
            Workbook wb = WorkbookFactory.create(new File(inFileName));
            for (Sheet sheet : wb) {
                for (Row row : sheet) {
                    rowProcessor.doRow(sheet, row);
                }
            }

            if (outFileName != null) {
                try {
                    FileOutputStream fileOut = new FileOutputStream(outFileName);
                    wb.write(fileOut);
                    fileOut.close();
                    //this seems to cause to workbook to save back to the original version
//                    wb.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                wb.close();
            }

        } catch (IOException | InvalidFormatException e) {
            e.printStackTrace();
        }
    }
}
