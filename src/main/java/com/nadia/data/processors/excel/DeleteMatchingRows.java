package com.nadia.data.processors.excel;


import com.nadia.data.api.IFileIterator;
import com.nadia.data.repository.SchoolDataRepository;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;

@Component
public class DeleteMatchingRows extends Cellabrate {


    private SchoolDataRepository schoolDataRepository;


    @Autowired
    public DeleteMatchingRows(IFileIterator fileIterator,SchoolDataRepository schoolDataRepository) {
        super(fileIterator);
        this.schoolDataRepository = schoolDataRepository;
    }


    private void deleteRow(int id) {
        updateRow(id, "mark", "delete");
    }

    private void updateRow(int id, String field, String newValue) {
//        schoolDataRepository.updateCityName(id,newValue);

    }


    @Override
    public void process(String inFileName) {
        process(inFileName, "pruned",
                (sheet, row) -> {

                    if (row.getCell(7).getStringCellValue().equals("delete")) {
                        deleteRow((int) row.getCell(3).getNumericCellValue());
                    }

                    Cell cell = row.getCell(8);
                    if (cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK) {
                        updateRow((int) row.getCell(1).getNumericCellValue(),
                                "name",
                                row.getCell(8).getStringCellValue());
                    }
                }
        );
    }


    @Override
    public void setup() throws FileNotFoundException {

    }


    @Override
    public void cleanUp() {

    }
}
