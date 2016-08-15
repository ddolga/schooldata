package com.nadia.data.processors.excel;

import com.nadia.data.api.IFileIterator;
import com.nadia.data.processors.AbstractProcessor;
import com.nadia.data.util.Formatters;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class CleanAvailableLabor extends AbstractProcessor {

    public CleanAvailableLabor(IFileIterator fileIterator) {
        super(fileIterator);
    }

    @Override
    public void setup() throws FileNotFoundException {

    }


    private void writeMunicipalityName(Row row, Sheet outSheet, String regionName) {
        String name = row.getCell(0).getStringCellValue();
        Row outRow = outSheet.createRow(outSheet.getLastRowNum() + 1);
        Cell cRegionName = outRow.createCell(0);
        cRegionName.setCellValue(regionName);
        Cell cMuniName = outRow.createCell(1);
        cMuniName.setCellValue(name);
        for (int i = 0; i < 5; i++) {
            Cell nc = outRow.createCell(i + 2);
            nc.setCellValue(row.getCell(i + 1).getNumericCellValue());
        }
    }


    @Override
    public void process(String inFileName) {

        try {
            Workbook wb = WorkbookFactory.create(new File(inFileName));
            Workbook outWb = new XSSFWorkbook();
            for (Sheet sheet : wb) {
                Sheet outSheet = outWb.createSheet(sheet.getSheetName());
                String regionName = null;
                for (Row row : sheet) {
                    int indents = row.getCell(0).getCellStyle().getIndention();
                    switch (indents) {
                        case 0:
                            regionName = row.getCell(0).getStringCellValue();
                            break;
                        case 1:
                            writeMunicipalityName(row, outSheet, regionName);
                            break;
                        default:
                    }
                }
            }

            String outFileName = Formatters.formatOutputFileName(inFileName, "clean");
            closeOut(outFileName, outWb);

        } catch (IOException | InvalidFormatException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void cleanUp() {

    }
}
