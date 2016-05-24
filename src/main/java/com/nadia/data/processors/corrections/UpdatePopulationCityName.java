package com.nadia.data.processors.corrections;

import com.nadia.data.api.IProcessFile;
import com.nadia.data.api.SchoolDataInterface;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UpdatePopulationCityName extends UpdateFieldAbstract{

    @Autowired
    public UpdatePopulationCityName(SchoolDataInterface schoolDataRepository) {
        super(schoolDataRepository);
    }

    @Override
    protected IProcessFile getProcessFile() {

        return (String inFileName) -> {

            _processFile(inFileName,(Row row) -> {
                String original = getCell(row, 3);
                String corrected = getCell(row, 4);
                if (original != null && corrected != null) {
                    schoolDataRepository.updatePopulationCityName(original, corrected);
                    logger.info("Updated: " + original + " --> " + corrected);
                } else {
                    logger.info("Skipped: " + original + " --> " + corrected);
                }
            });

        };
    }
}
