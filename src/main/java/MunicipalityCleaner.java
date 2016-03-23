import api.FormProcessInterface;
import importers.form.FormExtractorCvs;
import importers.form.FormTransliterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MunicipalityCleaner extends Application {

    Logger logger = LoggerFactory.getLogger(MunicipalityCleaner.class);

    private final static String[] commands = {"1. Transliterate", "2. Combine CSV"};
    private FormProcessInterface processor;

    public static void main(String[] args) {
        Parameters.processParameters("RegEx Text Extractor", args, commands);
        MunicipalityCleaner municipalityCleaner = new MunicipalityCleaner(Parameters.type, 0);
        municipalityCleaner.iterateOverFiles(Parameters.fa);
    }

    public MunicipalityCleaner(int type, int limit) {
        super(type,limit);

        switch (type) {
            case 1:
                processor = new FormTransliterator();
                break;
            case 2:
                processor = new FormExtractorCvs();
                break;
        }
    }

    @Override
    protected void processFile(String path) {
        processor.process(path, _formatOutputFileName(path, type == 1 ? "translit" : "converted"), limit);
    }

}
