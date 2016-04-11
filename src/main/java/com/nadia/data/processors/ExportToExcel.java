package com.nadia.data.processors;

import com.nadia.data.Parameters;
import com.nadia.data.api.FormProcessInterface;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;

public class ExportToExcel implements FormProcessInterface {


    private final static String FIELD_DELIMETER = "\t";

    Workbook wb;
    FileOutputStream outStream;

    public ExportToExcel(Parameters params) {
        createTargetWorkbook(params.getTargetFileName());
    }

    private void createTargetWorkbook(String outFileName) {
        wb = new XSSFWorkbook();
        try {
            outStream = new FileOutputStream(outFileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void process(String inFileName, int limit) {
        File f = new File(inFileName);
        Sheet sheet = wb.createSheet(f.getName());
        try {
            BufferedReader br = new BufferedReader(new FileReader(inFileName));
            String line = br.readLine();
            int rowIdx = 0;
            while (line != null) {
                Row row = sheet.createRow(rowIdx++);
                String[] arr = line.split(FIELD_DELIMETER);
                for (int i = 0; i < arr.length; i++) {
                    Cell cell = row.createCell(i);
                    cell.setCellValue(arr[i]);
                }
                line = br.readLine();
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void cleanUp() {
        try {
            wb.write(outStream);
            outStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
