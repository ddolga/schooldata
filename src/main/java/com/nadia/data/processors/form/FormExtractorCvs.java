package com.nadia.data.processors.form;

import com.nadia.data.api.RegExLineProcessorInterface;
import com.nadia.data.processors.AbstractProcessor;
import com.nadia.data.matchers.RegExLineProcessor;
import com.nadia.data.util.Formatters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class FormExtractorCvs extends AbstractProcessor {

    Logger logger = LoggerFactory.getLogger(FormExtractorCvs.class);

    private final RegExLineProcessorInterface lineProcessor;

    @Autowired
    public FormExtractorCvs(RegExLineProcessor lineProcessor){
       this.lineProcessor = lineProcessor;
    }


    @Override
    public void process(String inFileName) {

        try {
            String outFileName = Formatters.formatOutputFileName(inFileName, "convert");
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
}
