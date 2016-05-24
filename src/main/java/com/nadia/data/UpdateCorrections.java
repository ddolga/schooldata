package com.nadia.data;

import com.nadia.data.api.ParametersInterface;
import com.nadia.data.api.SchoolDataInterface;
import com.nadia.data.processors.corrections.UpdateCityName;
import org.springframework.beans.factory.annotation.Autowired;


public class UpdateCorrections extends BaseImporter {

    private SchoolDataInterface schoolData;


    public void setSchoolData(SchoolDataInterface schoolData){
        this.schoolData = schoolData;
    }

    public UpdateCorrections(ParametersInterface params, int limit) {
        super(params, limit);
    }

    @Override
    protected void processFile(String path) {

        switch (type) {
            case 0:
                UpdateCityName updateCityName = new UpdateCityName(schoolData);
                updateCityName.process(path);
                break;
        }
    }

    @Override
    protected void cleanUp() {

    }
}
