package com.nadia.data;

import com.nadia.data.api.WorkbookProcessInterface;
import com.nadia.data.importers.excel.CombineToCSV;
import com.nadia.data.importers.excel.ProcessAllCells;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.nadia.data.processors.Transliterator;
import com.nadia.data.processors.TransliteratorForCombine;

public class SchoolDataCleaner extends MainApp {

    Logger logger = LoggerFactory.getLogger(SchoolDataCleaner.class);


    private static final String[] commands = {"1. Transliterate", "2. Combine CSV"};


    private WorkbookProcessInterface processor;

    public static void main(String[] args) {
        Parameters params = Parameters.processParameters( args);
        SchoolDataCleaner cleaner = new SchoolDataCleaner(params, 0);
        cleaner.iterateOverFiles(params.getFa());
    }

    public SchoolDataCleaner(Parameters params, int limit) {
        super(params, limit);

        switch (params.getType()) {
            case 1:
                processor = new ProcessAllCells();
                break;
            case 2:
                processor = new CombineToCSV();
                break;
        }
    }


    @Override
    protected void processFile(String path) {
        logger.info("Processing: " + path);
        switch (type) {
            case 1:
                processor.process(path, Util.formatOutputFileName(path, "translit"), new Transliterator(), limit);
                break;
            case 2:
                processor.process(path, Util.formatOutputFileName(path, "converted", "txt"), new TransliteratorForCombine(), limit);
                break;
        }
    }

    @Override
    protected void cleanUp() {

    }


}
