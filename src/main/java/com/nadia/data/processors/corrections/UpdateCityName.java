package com.nadia.data.processors.corrections;

import com.nadia.data.api.IProcessFile;
import com.nadia.data.api.SchoolDataInterface;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UpdateCityName extends AbstractUpdateField {


    @Autowired
    public UpdateCityName( SchoolDataInterface schoolDataRepository) {
        super(schoolDataRepository);
    }

    protected IProcessFile getProcessFile() {
        return (String inFileName) -> {

            _processFile(inFileName,(Row row) -> {
                String original = getCell(row, 2);
                String corrected = getCell(row, 3);
                if (original != null && corrected != null) {
                    schoolDataRepository.updateCityName(original, corrected);
                    logger.info("Updated: " + original + " --> " + corrected);
                } else {
                    logger.info("Skipped: " + original + " --> " + corrected);
                }
            });
        };
    }
}
