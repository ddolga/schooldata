package com.nadia.data.processors.excel;

import com.nadia.data.api.CellProcessorInterface;
import org.apache.poi.ss.usermodel.Cell;

public class ProcessAllCells extends Cellabrate {

    private CellProcessorInterface cellProcessor;

    public ProcessAllCells(CellProcessorInterface cellProcessor) {
        this.cellProcessor = cellProcessor;
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
}
