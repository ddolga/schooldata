import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public abstract class Application {

    protected int type;
    protected int limit;

    static class Parameters {

        public static int type;
        public static String[] fa;

        public static void processParameters(String title, String[] args, String[] commands) {

            type = 0;
            fa = null;
            if (args.length > 0) {
                type = Integer.parseInt(args[0]);

                fa = new String[args.length - 1];
                for (int i = 1; i < args.length; i++) {
                    fa[i - 1] = args[i];
                }
            } else {
                System.out.println(title);
                for (String command : commands) {
                    System.out.println(command);
                }
                System.out.print("Select process:");
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                try {
                    String str = br.readLine();
                    type = Integer.parseInt(str);

                    System.out.println("Enter file(s)|directory(s) to process [path[, path2, path3,...]:");
                    String files = br.readLine();
                    fa = splitFiles(files);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public Application(int type, int limit) {
        this.type = type;
        this.limit = limit;
    }

    protected void iterateOverFiles(String[] fa) {

        for (String s : fa) {
            File f = new File(s);
            if (f.isDirectory()) {
                iterateOverFiles(f.listFiles());
            } else {
                processFile(s);
            }
        }
    }

    protected void iterateOverFiles(File[] fa) {
        for (File file : fa) {
            if (file.isDirectory()) {
                iterateOverFiles(file.listFiles());
            } else {
                processFile(file.getAbsolutePath());
            }
        }
    }

    protected abstract void processFile(String path);

    protected String _formatOutputFileName(String name, String suffix) {
        String ext = name.substring(name.lastIndexOf(".") + 1);
        return _formatOutputFileName(name, suffix, ext);
    }

    protected String _formatOutputFileName(String name, String suffix, String ext) {
        String baseName = name.substring(0, name.lastIndexOf("."));
        return baseName + "_" + suffix + "." + ext;
    }

    protected static String[] splitFiles(String s) {
        String[] names = s.split("\\,[\\s]*");
        return names;
    }


}


