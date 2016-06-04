package com.nadia.data.processors.excel;

import com.nadia.data.api.CellProcessorInterface;
import com.nadia.data.errors.PatternMatchError;
import com.nadia.data.processors.AbstractProcessor;
import com.nadia.data.util.Formatters;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

import static com.nadia.data.util.Formatters.combineToRow;


public class CombineToCSV extends AbstractProcessor {

    Logger logger = LoggerFactory.getLogger(CombineToCSV.class);

    private CellProcessorInterface cellProcessor;

    public CombineToCSV(CellProcessorInterface cellProcessor) {
        this.cellProcessor = cellProcessor;
    }

    private void writeHeader(BufferedWriter out, String header) throws IOException {
        out.write(header);
        out.newLine();
    }

    @Override
    public void setup() throws FileNotFoundException {

    }

    @Override
    public void process(String inFileName) {

        String outFileName = Formatters.formatOutputFileName(inFileName, "converted", "txt");

        try {
            Workbook wb = WorkbookFactory.create(new File(inFileName));
            logger.info("loaded: " + inFileName);

            BufferedWriter out = new BufferedWriter(new FileWriter(outFileName));

            if (params.hasHeader()) {
                writeHeader(out, params.getHeader());
            }

            Cell cell;

            //process all worksheets
            for (Sheet sheet : wb) {

                //process all rows
                for (Row row : sheet) {

                    boolean isEmpty = true;
                    //process columns
                    int maxCol = row.getLastCellNum();
                    String[] values = new String[maxCol];

                    for (int i = 0; i < maxCol; i++) {
                        cell = row.getCell(i);

                        String val = cellProcessor.processCell(cell);
                        isEmpty = isEmpty && (val == null || val.isEmpty());
                        if (!isEmpty) {
                            values[i] = val;
                        }
                    }

                    if (!isEmpty) {
                        String line = combineToRow(values);
                        out.write(line);
                        out.newLine();
                    }
                }
            }
            out.close();
            wb.close();
        } catch (IOException | InvalidFormatException | PatternMatchError e) {
            e.printStackTrace();
        }

    }

    @Override
    public void cleanUp() {

    }
}
