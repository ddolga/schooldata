package com.nadia.data.importers.form;

import com.nadia.data.Util;
import com.nadia.data.api.FormProcessInterface;
import com.nadia.data.api.RegExLineProcessorInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.nadia.data.processors.RegExProcessor.RegExLineProcessor;

import java.io.*;

public class FormExtractorCvs implements FormProcessInterface {

    Logger logger = LoggerFactory.getLogger(FormExtractorCvs.class);
    private final RegExLineProcessorInterface lineProcessor = new RegExLineProcessor();

    @Override
    public void process(String inFileName, int limit) {

        try {
            String outFileName = Util.formatOutputFileName(inFileName,"convert");
            BufferedReader br = new BufferedReader(new FileReader(inFileName));
            BufferedWriter bw = new BufferedWriter(new FileWriter(outFileName));


            String line = br.readLine();
            while (line != null) {
                String convertedStr = lineProcessor.process(line);
                if (convertedStr != null) {
                    bw.write(convertedStr);
                    bw.newLine();
                }
                line = br.readLine();
            }
            br.close();
            bw.close();
        } catch (IOException e) {
            logger.error(e.getLocalizedMessage());
        }
    }

    @Override
    public void cleanUp() {

    }
}
