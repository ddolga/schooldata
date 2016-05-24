package com.nadia.data.processors.cell;

import com.nadia.data.api.CellProcessorInterface;
import net.sf.junidecode.Junidecode;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.stereotype.Component;

@Component
public class Transliterator implements CellProcessorInterface {

    @Override
    public String processCell(Cell cell) {
        if (cell.getCellType() == Cell.CELL_TYPE_STRING)
            return Junidecode.unidecode(cell.getStringCellValue()).trim();

        return null;
    }
}
