import api.FormProcessInterface;
import processors.Transliterator;

public class MunicipalityCleaner extends Application {


    private final static String[] commands = {"1. Transliterate", "2. Combine CSV"};


    public static void main(String[] args) {
        Parameters.processParameters(args,commands);
        MunicipalityCleaner municipalityCleaner = new MunicipalityCleaner();
        municipalityCleaner.cleanData(Parameters.type, Parameters.fa, 0);
    }


    public void cleanData(int type, String[] fa, int limit) {

        switch (type) {
            case 1:
                //********  TRANSLITERATE **********//
                FormProcessInterface translit = new FormTransliterator();
                for (String s : fa) {
                    System.out.println("Processing: " + s);
                    translit.process(s, _formatOutputFileName(s, "translit"), new Transliterator(), limit);
                }
                break;
            case 2:
                //********  COMBINE **********//
/*                WorkbookProcessInterface combine = new CombineToCSV();
                for (String s : fa) {
                    System.out.println("Processing: " + s);
                    combine.process(s, _formatOutputFileName(s, "converted", "txt"), new TransliteratorForCombine(), limit);
                }*/
                break;
        }

    }


}
