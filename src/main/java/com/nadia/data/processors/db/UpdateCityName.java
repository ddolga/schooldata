package com.nadia.data.processors.db;

import com.nadia.data.api.SchoolDataInterface;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;

@Component
public class UpdateCityName extends AbstractUpdateField {


    @Autowired
    public UpdateCityName( SchoolDataInterface schoolDataRepository) {
        super(schoolDataRepository);
    }

    @Override
    public void setup() throws FileNotFoundException {

    }

    @Override
    public void process(String inFileName) {
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
    }

    @Override
    public void cleanUp() {

    }
}
