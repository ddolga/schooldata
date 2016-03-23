package importers.form;

import api.FormProcessInterface;
import net.sf.junidecode.Junidecode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class FormTransliterator implements FormProcessInterface {

    Logger logger = LoggerFactory.getLogger(FormTransliterator.class);

    private static final String lineSeparator = System.getProperty("line.separator");

    @Override
    public void process(String inFileName, String outFileName, int limit) {

        try {
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
}
