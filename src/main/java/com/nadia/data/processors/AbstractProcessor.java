package com.nadia.data.processors;

import com.nadia.data.AbstractImporter;
import com.nadia.data.api.*;
import com.nadia.data.util.Parameters;

public abstract class AbstractProcessor {

    private ICleanUp cleanUp = () -> this.cleanUp();

    protected ParametersInterface params;

    public void doYourThing(Parameters params) {
        this.params = params;
        ImporterInterface importer = new AbstractImporter(getProcessFile(), cleanUp);
        importer.iterateOverFiles(params.getFa());
    }

    public void cleanUp() {
        //do nothing for now
    }

    protected abstract IProcessFile getProcessFile();
}
