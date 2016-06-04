package com.nadia.data.processors;

import com.nadia.data.api.IFileIterator;
import com.nadia.data.api.IProcessFile;
import com.nadia.data.api.ParametersInterface;
import com.nadia.data.util.Parameters;

public abstract class AbstractProcessor implements IProcessFile {

    protected ParametersInterface params;

    public void doYourThing(IFileIterator fileIterator, Parameters params) {
        this.params = params;
        fileIterator.iterateOverFiles(params.getFa());
    }

    public void cleanUp(){}

}
