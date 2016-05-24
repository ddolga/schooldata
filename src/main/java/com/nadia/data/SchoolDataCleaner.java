package com.nadia.data;

import com.nadia.data.api.ParametersInterface;
import com.nadia.data.api.WorkbookProcessInterface;
import com.nadia.data.processors.file.excel.CombineToCSV;
import com.nadia.data.processors.file.excel.ProcessAllCells;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.nadia.data.processors.cell.Transliterator;
import com.nadia.data.processors.cell.TransliteratorForCombine;

public class SchoolDataCleaner extends BaseImporter {

    Logger logger = LoggerFactory.getLogger(SchoolDataCleaner.class);


    private WorkbookProcessInterface processor;


    public SchoolDataCleaner(ParametersInterface params, int limit) {
        super(params, limit);

        switch (type) {
            case 1:
                processor = new ProcessAllCells();
                break;
            case 2:
                processor = new CombineToCSV(params);
                break;
        }
    }


    @Override
    protected void processFile(String path) {
        logger.info("Processing: " + path);
        switch (type) {
            case 1:
                processor.process(path, new Transliterator(), limit);
                break;
            case 2:
                processor.process(path, new TransliteratorForCombine(), limit);
                break;
        }
    }

    @Override
    protected void cleanUp() {
        processor.cleanUp();
    }


}
