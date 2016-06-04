package com.nadia.data.processors.excel;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;

@Component
public class CompareCityName extends Cellabrate {

    Logger logger = LoggerFactory.getLogger(CompareCityName.class);

    @Override
    public void setup() throws FileNotFoundException {

    }

    @Override
    public void process(String inFileName) {

        process(inFileName, "distance",
                (sheet, row) -> {
                    String c1 = row.getCell(0).getStringCellValue();
                    String c2 = row.getCell(1).getStringCellValue();
                    double distance = StringUtils.getJaroWinklerDistance(c1, c2);

                    Cell cell3 = row.createCell(3);
                    cell3.setCellValue(distance);

                    boolean h1 = c1.indexOf("'") > 0;
                    boolean h2 = c2.indexOf("'") > 0;

                    if (h1 || h2) {
                        int c = h1 ? 2 : 1;
                        Cell cell4 = row.createCell(4);
                        cell4.setCellValue(c);
                    }

//                    logger.info(c1 + " --> " + c2 + "= " + distance);
                });
    }

    @Override
    public void cleanUp() {

    }
}
