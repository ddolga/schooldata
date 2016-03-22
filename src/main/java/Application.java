import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Application {

    protected static String[] commands;


    static class Parameters {

        public static int type;
        public static String[] fa;

        public static void processParameters(String[] args,String[] commands) {

            type = 0;
            fa = null;
            if (args.length > 0) {
                type = Integer.parseInt(args[0]);

                fa = new String[args.length - 1];
                for (int i = 1; i < args.length; i++) {
                    fa[i - 1] = args[i];
                }
            } else {
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


