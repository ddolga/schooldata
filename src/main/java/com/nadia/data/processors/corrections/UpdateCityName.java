package com.nadia.data.processors.corrections;

import com.nadia.data.api.SchoolDataInterface;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class UpdateCityName {

    Logger logger = LoggerFactory.getLogger(UpdateCityName.class);

    private SchoolDataInterface schoolDataRepository;

    public UpdateCityName(SchoolDataInterface schoolDataRepository) {
        this.schoolDataRepository = schoolDataRepository;
    }


    private String getCell(Row row, int cellNo) {

        Cell cell = row.getCell(cellNo);
        if (cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK) {
            return cell.getStringCellValue();
        }

        return null;
    }


    public void process(String inFileName) {

        try {
            Workbook wb = WorkbookFactory.create(new File(inFileName));
            for (Sheet sheet : wb) {
                boolean isFirstRow = true;
                for (Row row : sheet) {
                    if (!isFirstRow) {
                        String original = getCell(row, 2);
                        String corrected = getCell(row, 3);
                        if (original != null && corrected != null) {
                            schoolDataRepository.updateCityName(original, corrected);
                            logger.info("Updated: " + original + " --> " + corrected);
                        } else {
                            logger.info("Skipped: " + original + " --> " + corrected);
                        }
                    }
                    isFirstRow = false;

//                        if (cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK) {
                }
            }

            wb.close();
        } catch (IOException | InvalidFormatException e) {
            e.printStackTrace();
        }

    }


}
