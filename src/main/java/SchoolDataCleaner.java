import api.WorkbookProcessInterface;
import importers.excel.CombineToCSV;
import importers.excel.CombineToJson;
import importers.excel.ProcessAllCells;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import processors.Transliterator;
import processors.TransliteratorForCombine;

public class SchoolDataCleaner extends Application {

    Logger logger = LoggerFactory.getLogger(SchoolDataCleaner.class);


    private static final String[] commands = {"1. Transliterate", "2. Combine CSV"};


    private WorkbookProcessInterface processor;

    public static void main(String[] args) {
        Parameters.processParameters("Excel Data Converter", args, commands);
        SchoolDataCleaner cleaner = new SchoolDataCleaner(Parameters.type, 0);
        cleaner.iterateOverFiles(Parameters.fa);
    }

    public SchoolDataCleaner(int type, int limit) {
        super(type, limit);

        switch(type){
            case 1:
                processor = new ProcessAllCells();
                break;
            case 2:
                processor = new CombineToCSV();
                break;
        }
    }


    @Override
    protected void processFile(String path) {
        logger.info("Processing: " + path);
        switch(type){
            case 1:
                processor.process(path, _formatOutputFileName(path, "translit"), new Transliterator(), limit);
                break;
            case 2:
                processor.process(path, _formatOutputFileName(path, "converted", "txt"), new TransliteratorForCombine(), limit);
                break;
        }
    }


}
