package com.nadia.data.processors.cell;

import com.nadia.data.api.CellProcessorInterface;
import org.apache.poi.ss.usermodel.Cell;

public class TransliteratorForCombine implements CellProcessorInterface {

    CellProcessorInterface cellProcessor = new Transliterator();

    @Override
    public String processCell(Cell cell) {

        String str;
        if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK || cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
            return null;
        }

        str = cellProcessor.processCell(cell);
        if (str == null) {
            try {
                str = cell.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return str;
    }
}
