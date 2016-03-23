package importers.form;

import api.FormProcessInterface;
import api.RegExLineProcessorInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import processors.RegExLineProcessor;

import java.io.*;

public class FormExtractorCvs implements FormProcessInterface {

    Logger logger = LoggerFactory.getLogger(FormExtractorCvs.class);

    @Override
    public void process(String inFileName, String outFileName, int limit) {

        try {
            BufferedReader br = new BufferedReader(new FileReader(inFileName));
            BufferedWriter bw = new BufferedWriter(new FileWriter(outFileName));

            RegExLineProcessorInterface lineProcessor = new RegExLineProcessor();

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
