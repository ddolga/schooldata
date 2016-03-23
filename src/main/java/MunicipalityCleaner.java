import api.FormProcessInterface;
import importers.form.FormExtractorCvs;
import importers.form.FormTransliterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MunicipalityCleaner extends Application {

    Logger logger = LoggerFactory.getLogger(MunicipalityCleaner.class);

    private final static String[] commands = {"1. Transliterate", "2. Combine CSV"};


    public static void main(String[] args) {
        Parameters.processParameters(args, commands);
        MunicipalityCleaner municipalityCleaner = new MunicipalityCleaner();
        municipalityCleaner.cleanData(Parameters.type, Parameters.fa, 0);
    }


    public void cleanData(int type, String[] fa, int limit) {

        switch (type) {
            case 1:
                //********  TRANSLITERATE **********//
                FormProcessInterface translit = new FormTransliterator();
                for (String s : fa) {
                    logger.info("Processing: " + s);
                    translit.process(s, _formatOutputFileName(s, "translit"), limit);
                }
                break;
            case 2:
                //********  COMBINE **********//
                FormProcessInterface extract = new FormExtractorCvs();
                for (String s : fa) {
                    logger.info("Processing: " + s);
                    extract.process(s, _formatOutputFileName(s, "converted"), limit);
                }
                break;
        }

    }


}
