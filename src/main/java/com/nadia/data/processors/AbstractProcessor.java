package com.nadia.data.processors;

import com.nadia.data.api.IFileIterator;
import com.nadia.data.api.IProcessFile;
import com.nadia.data.api.ParametersInterface;
import com.nadia.data.util.Parameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;

public abstract class AbstractProcessor implements IProcessFile {

    Logger logger = LoggerFactory.getLogger(AbstractProcessor.class);

    protected ParametersInterface params;

    public void doYourThing(IFileIterator fileIterator, Parameters params) {
        this.params = params;
        try {
            fileIterator.iterateOverFiles(params.getFa());
        } catch (FileNotFoundException e) {
            logger.error("Error processing file",e);
        }
    }


}
