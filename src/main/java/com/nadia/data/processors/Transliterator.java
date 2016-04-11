package com.nadia.data.processors;

import com.nadia.data.api.CellProcessorInterface;
import net.sf.junidecode.Junidecode;
import org.apache.poi.ss.usermodel.Cell;

public class Transliterator implements CellProcessorInterface {

    @Override
    public String processCell(Cell cell) {
        if(cell.getCellType() == Cell.CELL_TYPE_STRING){
            String str = cell.getStringCellValue();
            return Junidecode.unidecode(str);
        }

        return null;
    }
}
