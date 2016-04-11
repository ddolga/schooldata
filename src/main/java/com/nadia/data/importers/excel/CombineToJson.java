package com.nadia.data.importers.excel;

import com.nadia.data.api.CellProcessorInterface;
import com.nadia.data.api.WorkbookProcessInterface;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class CombineToJson implements WorkbookProcessInterface {

    Logger logger = LoggerFactory.getLogger(CombineToJson.class);


    private String[] readHeaderRow(Row row, CellProcessorInterface cellProcessor) {

        int size = row.getLastCellNum();
        String[] fieldNames = new String[size];
        for (int i = 0; i < size; i++) {
            Cell cell = row.getCell(i);

            String str = cellProcessor.processCell(cell);
            fieldNames[i] = str;
        }

        return fieldNames;
    }


    @Override
    public Workbook process(String inFileName, String outFileName, CellProcessorInterface cellProcessor,int limit) {

        String[] fieldNames = null;

        try {
            Workbook wb = WorkbookFactory.create(new File(inFileName));
            logger.info("loaded: " + inFileName);

            JSONArray jsonArray = new JSONArray();

            boolean isHeader = true;
            JSONObject line;
            int maxCol = 0;
            int rowCount = 0;
            boolean stop = false;
            for (Sheet sheet : wb) {
                for (Row row : sheet) {

                    if (isHeader) {
                        fieldNames = readHeaderRow(row, cellProcessor);
                        maxCol = fieldNames.length;
                        isHeader = false;
                    } else {
//                        line = new JSONObject();
                        Map map = new LinkedHashMap<String,String>();

                        boolean isEmpty = true;
                        for (int i = 0; i < maxCol; i++) {
                            Cell cell = row.getCell(i);

                            String str = cellProcessor.processCell(cell);
                            isEmpty = isEmpty && (str == null || str.isEmpty());

//                            line.put(fieldNames[i], str);
                            map.put(fieldNames[i],str);
                        }

                        if (!isEmpty) {
                            jsonArray.add(map);
                        }

                        rowCount++;
                        if(limit > 0 && rowCount == limit){
                            stop = true;
                            break;
                        }
                    }
                }
                if(stop)
                    break;
            }

            wb.close();
            FileWriter writer = new FileWriter(outFileName);
            jsonArray.writeJSONString(writer);
            writer.close();

            return wb;
        } catch (IOException | InvalidFormatException e) {
            e.printStackTrace();
        }

        return null;
    }


}
