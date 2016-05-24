package com.nadia.data.processors.file.excel;

import com.nadia.data.api.CellProcessorInterface;
import com.nadia.data.api.IProcessFile;
import com.nadia.data.processors.AbstractProcessor;
import com.nadia.data.processors.cell.Transliterator;
import com.nadia.data.util.Formatters;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Component
public class ProcessAllCells extends AbstractProcessor {


    private CellProcessorInterface cellProcessor;

    @Autowired
    public ProcessAllCells(Transliterator cellProcessor) {
        this.cellProcessor = cellProcessor;
    }


    @Override
    protected IProcessFile getProcessFile() {
        return (String inFileName) -> {

            String outFileName = Formatters.formatOutputFileName(inFileName, "translit");
            try {
                Workbook wb = WorkbookFactory.create(new File(inFileName));
                for (Sheet sheet : wb) {
                    for (Row row : sheet) {
                        for (Cell cell : row) {
                            if (cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK) {
                                String str = cellProcessor.processCell(cell);
                                if (str != null) {
                                    cell.setCellValue(str);
                                }
                            }
                        }
                    }
                }

                if (outFileName != null) {
                    try {
                        FileOutputStream fileOut = new FileOutputStream(outFileName);
                        wb.write(fileOut);
                        fileOut.close();
                        wb.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            } catch (IOException | InvalidFormatException e) {
                e.printStackTrace();
            }
        };
    }
}
