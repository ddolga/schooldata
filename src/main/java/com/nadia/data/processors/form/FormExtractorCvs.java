package com.nadia.data.processors.form;

import com.nadia.data.api.RegExLineProcessorInterface;
import com.nadia.data.errors.PatternMatchError;
import com.nadia.data.processors.AbstractProcessor;
import com.nadia.data.matchers.RegExLineProcessor;
import com.nadia.data.util.Formatters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Formatter;

@Component
public class FormExtractorCvs extends AbstractProcessor {

    Logger logger = LoggerFactory.getLogger(FormExtractorCvs.class);

    private final RegExLineProcessorInterface lineProcessor;

    @Autowired
    public FormExtractorCvs(RegExLineProcessor lineProcessor) {
        this.lineProcessor = lineProcessor;
    }


    @Override
    public void setup() throws FileNotFoundException {

    }


    @Override
    public void process(String inFileName) {

        try {
            String outFileName = Formatters.formatOutputFileName(inFileName, "convert");
            BufferedReader br = new BufferedReader(new FileReader(inFileName));
            BufferedWriter bw = new BufferedWriter(new FileWriter(outFileName));

            String dateStr = Formatters.getYearFromFileName(inFileName);

            String line = br.readLine();
            while (line != null) {
                String[] strArr = lineProcessor.process(line);
                if (strArr != null && strArr.length > 0) {
//                    String[] strArr2 = Formatters.insertIntoArray(strArr, 1, dateStr);
//                    String convertedStr = Formatters.combineToRow(strArr2);
                    String convertedStr = Formatters.combineToRow(strArr);
                    bw.write(convertedStr);
                    bw.newLine();
                }
                line = br.readLine();
            }
            br.close();
            bw.close();
        } catch (IOException | PatternMatchError e) {
            logger.error(e.getLocalizedMessage());
        }
    }

    @Override
    public void cleanUp() {

    }
}
