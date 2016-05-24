package com.nadia.data;

import com.nadia.data.api.FormProcessInterface;
import com.nadia.data.api.ParametersInterface;
import com.nadia.data.processors.file.form.FormExtractorCvs;
import com.nadia.data.processors.file.form.FormTransliterator;
import com.nadia.data.processors.file.form.ConcatenateFiles;
import com.nadia.data.processors.file.form.ExportToExcel;

public class MunicipalityCleaner extends BaseImporter {


    private FormProcessInterface processor;


    public MunicipalityCleaner(ParametersInterface params, int limit) {
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
