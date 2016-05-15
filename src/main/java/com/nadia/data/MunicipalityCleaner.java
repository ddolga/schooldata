package com.nadia.data;

import com.nadia.data.api.FormProcessInterface;
import com.nadia.data.importers.form.FormExtractorCvs;
import com.nadia.data.importers.form.FormTransliterator;
import com.nadia.data.processors.ConcatenateFiles;
import com.nadia.data.processors.ExportToExcel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MunicipalityCleaner extends MainApp {


    Logger logger = LoggerFactory.getLogger(MunicipalityCleaner.class);

    private FormProcessInterface processor;

    public static void main(String[] args) {
        Parameters params = Parameters.processParameters( args);
        MunicipalityCleaner municipalityCleaner = new MunicipalityCleaner(params, 0);
        municipalityCleaner.iterateOverFiles(params.getFa());
    }

    public MunicipalityCleaner(Parameters params, int limit) {
        super(params, limit);

        switch (params.getType()) {
            case 1:
                processor = new FormTransliterator();
                break;
            case 2:
                processor = new FormExtractorCvs();
                break;
            case 3:
                processor = new ConcatenateFiles(params);
                break;
            case 4:
                processor = new ExportToExcel(params);
                break;
        }
    }

    @Override
    protected void processFile(String path) {
        processor.process(path, limit);
    }

    @Override
    protected void cleanUp() {
        processor.cleanUp();
    }

}
