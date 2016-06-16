package com.nadia.data.processors.form;

import com.nadia.data.api.IFileIterator;
import com.nadia.data.processors.AbstractProcessor;
import com.nadia.data.util.Formatters;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class ExportToExcel extends AbstractProcessor {


    private final Logger logger = LoggerFactory.getLogger(ExportToExcel.class);


    Workbook wb;
    FileOutputStream outStream;

    @Autowired
    public ExportToExcel(IFileIterator fileIterator) {
        super(fileIterator);
    }


    private Workbook createTargetWorkbook(String outFileName) throws FileNotFoundException {
        Workbook wb = new XSSFWorkbook();
        outStream = new FileOutputStream(outFileName);
        return wb;
    }

    @Override
    public void cleanUp() {
        try {
            logger.info("Saving worksheet");
            wb.write(outStream);
            outStream.close();
            logger.info("Export Completed...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setup() throws FileNotFoundException {
        wb = createTargetWorkbook(params.getTargetFileName());
    }


    private void writeRow(String line, Sheet sheet, int rowIdx) {
        Row row = sheet.createRow(rowIdx);
        String[] arr = line.split(Formatters.FIELD_DELIMETER);
        for (String s : arr) {
            for (int i = 0; i < arr.length; i++) {
                Cell cell = row.createCell(i);
                cell.setCellValue(arr[i]);
            }
        }
    }


    @Override
    public void process(String inFileName) {
        try {
            logger.info("Processing: " + inFileName);
            File f = new File(inFileName);
            Sheet sheet = wb.createSheet(f.getName());

            int rowIdx = 0;
            //write header row for each worksheet
            writeRow(params.getHeader(), sheet, rowIdx++);
            BufferedReader br = new BufferedReader(new FileReader(inFileName));
            String line = br.readLine();
            while (line != null) {
                writeRow(line, sheet, rowIdx++);
                line = br.readLine();
            }
            br.close();
            logger.info("Saved: " + inFileName + " with " + rowIdx + " rows.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
