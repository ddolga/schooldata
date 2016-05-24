package com.nadia.data.processors.corrections;

import com.nadia.data.api.IProcessFile;
import com.nadia.data.api.IUpdateRow;
import com.nadia.data.api.SchoolDataInterface;
import com.nadia.data.processors.AbstractProcessor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public abstract class  UpdateFieldAbstract extends AbstractProcessor {

    Logger logger = LoggerFactory.getLogger(UpdateCityName.class);

    protected SchoolDataInterface schoolDataRepository;

    public UpdateFieldAbstract( SchoolDataInterface schoolDataRepository) {
        this.schoolDataRepository = schoolDataRepository;
    }

    protected String getCell(Row row, int cellNo) {

        Cell cell = row.getCell(cellNo);
        if (cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK) {
            return cell.getStringCellValue();
        }

        return null;
    }

    protected void _processFile(String inFileName, IUpdateRow updateRow) {

            try {
                Workbook wb = WorkbookFactory.create(new File(inFileName));
                for (Sheet sheet : wb) {
                    boolean isFirstRow = true;
                    for (Row row : sheet) {
                        if (!isFirstRow) {
                              updateRow.update(row);
                        }
                        isFirstRow = false;
                    }
                }

                wb.close();
            } catch (IOException | InvalidFormatException e) {
                e.printStackTrace();
            }
    }
}
