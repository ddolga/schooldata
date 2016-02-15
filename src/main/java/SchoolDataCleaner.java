import api.WorkbookProcessInterface;
import processors.Transliterator;
import processors.TransliteratorForCombine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SchoolDataCleaner {


    private static final String[] commands = {"1. Transliterate", "2. Combine", "3. Import to SQL"};


    public static void main(String[] args) {

        int type = 0;
        String[] fa = null;
        if(args.length > 0){
            type = Integer.parseInt(args[0]);

            fa = new String[args.length - 1];
            for (int i = 1; i < args.length; i++) {
               fa[i - 1] = args[i];
            }
        }else{
            System.out.println("School Data Cleaner:");
            for (String command : commands) {
                System.out.println(command);
            }
            System.out.print("Select process:");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            try {
                String str = br.readLine();
                type = Integer.parseInt(str);

                System.out.println("Enter file(s) to process [file1[, file2, file3,...]:");
                String files = br.readLine();
                fa = splitFiles(files);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        SchoolDataCleaner cleaner = new SchoolDataCleaner();
        cleaner.cleanData(type, fa);

    }


    public void cleanData(int type, String[] fa) {

            switch (type) {
                case 1:
                    //********  TRANSLITERATE **********//
                    WorkbookProcessInterface translit = new ProcessAllCells();
                    for (String s : fa) {
                        System.out.println("Processing: " + s);
                        translit.process(s, _formatOutputFileName(s, "translit"), new Transliterator());
                    }
                    break;
                case 2:
                    //********  COMBINE **********//
                    WorkbookProcessInterface combine = new CombineToCSV();
                    for (String s : fa) {
                        System.out.println("Processing: " + s);
                        combine.process(s, _formatOutputFileName(s, "converted","txt"),new TransliteratorForCombine());
                    }
                    break;
                case 3:
                    //********  IMPORT INTO SQL **********//
                    break;
            }

    }

    public String _formatOutputFileName(String name, String suffix) {
        String ext = name.substring(name.lastIndexOf(".") + 1);
        return _formatOutputFileName(name, suffix, ext);
    }

    public String _formatOutputFileName(String name, String suffix, String ext) {
        String baseName = name.substring(0, name.lastIndexOf("."));
        return baseName + "_" + suffix + "." + ext;
    }

    public static String[] splitFiles(String s) {
        String[] names = s.split("\\,[\\s]*");
        return names;
    }


}
