package com.nadia.data.processors;

import com.nadia.data.api.IFileIterator;
import com.nadia.data.api.IFileProcessor;
import com.nadia.data.api.IProcessor;
import com.nadia.data.api.ParametersInterface;
import com.nadia.data.util.Parameters;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public abstract class AbstractProcessor implements IProcessor, IFileProcessor {

    Logger logger = LoggerFactory.getLogger(AbstractProcessor.class);

    protected ParametersInterface params;

    IFileIterator fileIterator;

    public AbstractProcessor(IFileIterator fileIterator) {
        this.fileIterator = fileIterator;
    }

    public void doYaThing(Parameters params) {
        this.params = params;
        try {
            fileIterator.iterateOverFiles(params.getFa(), this);
        } catch (FileNotFoundException e) {
            logger.error("Error processing file", e);
        }
    }

    protected void closeOut(String outFileName, Workbook wb) throws IOException {
        if (outFileName != null) {
            try {
                FileOutputStream fileOut = new FileOutputStream(outFileName);
                wb.write(fileOut);
                fileOut.close();
                //this seems to cause to workbook to save back to the original version
//                wb.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            wb.close();
        }
    }


}
