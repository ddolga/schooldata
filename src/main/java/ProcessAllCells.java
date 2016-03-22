import api.CellProcessorInterface;
import api.WorkbookProcessInterface;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ProcessAllCells implements WorkbookProcessInterface {

    @Override
    public Workbook process(String inFileName, String outFileName, CellProcessorInterface cellProcessor,int limit) {

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

            return wb;
        } catch (IOException | InvalidFormatException e) {
            e.printStackTrace();
        }

        return null;

    }
}
