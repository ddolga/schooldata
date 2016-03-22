import api.WorkbookProcessInterface;
import net.sf.junidecode.App;
import processors.Transliterator;
import processors.TransliteratorForCombine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SchoolDataCleaner extends Application {


    private static final String[] commands = {"1. Transliterate", "2. Combine CSV", "3. Combine JSON"};


    public static void main(String[] args) {
        Parameters.processParameters(args,commands);
        SchoolDataCleaner cleaner = new SchoolDataCleaner();
        cleaner.cleanData(Parameters.type, Parameters.fa,0);

    }


    public void cleanData(int type, String[] fa,int limit) {

            switch (type) {
                case 1:
                    //********  TRANSLITERATE **********//
                    WorkbookProcessInterface translit = new ProcessAllCells();
                    for (String s : fa) {
                        System.out.println("Processing: " + s);
                        translit.process(s, _formatOutputFileName(s, "translit"), new Transliterator(),limit);
                    }
                    break;
                case 2:
                    //********  COMBINE **********//
                    WorkbookProcessInterface combine = new CombineToCSV();
                    for (String s : fa) {
                        System.out.println("Processing: " + s);
                        combine.process(s, _formatOutputFileName(s, "converted","txt"),new TransliteratorForCombine(),limit);
                    }
                    break;
                case 3:
                    //********  IMPORT INTO JSON **********//
                    WorkbookProcessInterface combineJson = new CombineToJson();
                    for (String s : fa) {
                        System.out.println("Processing: " + s);
                        combineJson.process(s, _formatOutputFileName(s, "converted","json"),new TransliteratorForCombine(),limit);
                    }

                    break;
            }

    }


}
