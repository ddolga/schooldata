package com.nadia.data.processors.excel;

import com.nadia.data.api.CellProcessorInterface;
import com.nadia.data.api.IFileIterator;
import org.apache.poi.ss.usermodel.Cell;

import java.io.FileNotFoundException;

public class ProcessAllCells extends Cellabrate {

    private CellProcessorInterface cellProcessor;

    public ProcessAllCells(IFileIterator fileIterator, CellProcessorInterface cellProcessor) {
        super(fileIterator);
        this.cellProcessor = cellProcessor;
    }

    @Override
    public void setup() throws FileNotFoundException {

    }

    @Override
    public void process(String inFileName) {

        process(inFileName, "translit",
                (sheet, row) -> {
                    for (Cell cell : row) {
                        if (cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK) {
                            String str = cellProcessor.processCell(cell);
                            if (str != null) {
                                cell.setCellValue(str);
                            }
                        }
                    }
                });
    }

    @Override
    public void cleanUp() {

    }
}
