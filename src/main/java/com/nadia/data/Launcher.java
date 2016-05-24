package com.nadia.data;

import com.nadia.data.api.ImporterInterface;
import com.nadia.data.api.SchoolDataInterface;
import com.nadia.data.util.Parameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Launcher {

    private Parameters params;
    private SchoolDataInterface schoolData;

    @Autowired
    public Launcher(Parameters params, SchoolDataInterface schoolData) {
        this.params = params;
        this.schoolData = schoolData;
    }


    public void importData() {

        String importerType = params.getImporterType();

        ImporterInterface importer = null;
        if (importerType.equals("MunicipalityCleaner")) {
            importer = new MunicipalityCleaner(params, 0);
        } else if (importerType.equals("SchoolDataCleaner")) {
            importer = new SchoolDataCleaner(params, 0);
        } else if (importerType.equals("UpdateCorrections")) {
            UpdateCorrections uc = new UpdateCorrections(params, 0);
            uc.setSchoolData(schoolData);
            importer = uc;
        }

        if (importer != null) {
            importer.iterateOverFiles(params.getFa());
        }

    }

}
