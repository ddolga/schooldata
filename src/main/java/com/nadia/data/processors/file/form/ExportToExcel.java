package com.nadia.data.processors.file.form;

import com.nadia.data.api.IProcessFile;
import com.nadia.data.api.ParametersInterface;
import com.nadia.data.processors.AbstractProcessor;
import com.nadia.data.util.Formatters;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class ExportToExcel extends AbstractProcessor {


    Workbook wb;
    FileOutputStream outStream;


    private void createTargetWorkbook(String outFileName) {
        wb = new XSSFWorkbook();
        try {
            outStream = new FileOutputStream(outFileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected IProcessFile getProcessFile() {
        return (String inFileName) -> {

            createTargetWorkbook(params.getTargetFileName());
            File f = new File(inFileName);
            Sheet sheet = wb.createSheet(f.getName());
            try {
                BufferedReader br = new BufferedReader(new FileReader(inFileName));
                String line = br.readLine();
                int rowIdx = 0;
                while (line != null) {
                    Row row = sheet.createRow(rowIdx++);
                    String[] arr = line.split(Formatters.FIELD_DELIMETER);
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
        };
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
