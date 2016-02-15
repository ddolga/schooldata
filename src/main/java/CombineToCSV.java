import api.CellProcessorInterface;
import api.WorkbookProcessInterface;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class CombineToCSV implements WorkbookProcessInterface {

    private static final String FIELD_DELIENATOR = "\t";
    private static final String LINE_DELIENATOR = "\n";

    @Override
    public Workbook process(String inFileName, String outFileName, CellProcessorInterface cellProcessor) {

        try {
            Workbook wb = WorkbookFactory.create(new File(inFileName));
            System.out.println("loaded: " + inFileName);
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
