package com.nadia.data.processors.form;

import com.nadia.data.api.IFileIterator;
import com.nadia.data.processors.AbstractProcessor;
import com.nadia.data.util.Formatters;
import net.sf.junidecode.Junidecode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class FormTransliterator extends AbstractProcessor {

    Logger logger = LoggerFactory.getLogger(FormTransliterator.class);

    private static final String lineSeparator = System.getProperty("line.separator");

    @Autowired
    public FormTransliterator(IFileIterator fileIterator) {
        super(fileIterator);
    }

    @Override
    public void setup() throws FileNotFoundException {

    }

    @Override
    public void process(String inFileName) {

        try {
            String outFileName = Formatters.formatOutputFileName(inFileName,"translit");
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(inFileName), "UTF-16"));
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFileName),"US-ASCII"));
            String line = br.readLine();
            while (line != null) {
                String uniLine = Junidecode.unidecode(line);
                bw.write(uniLine.toUpperCase());
                bw.write(lineSeparator);
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
