package com.nadia.data;

import com.nadia.data.errors.PatternMatchError;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class MainApp {


    public static final String FIELD_DELIMETER = ",";

    protected int type;
    protected int limit;

    public MainApp(Parameters params, int limit) {
        this.type = params.getType();
        this.limit = limit;
    }


    protected void iterateOverFiles(String[] fs) {

        File[] fa = new File[fs.length];
        for (int i = 0; i < fs.length; i++) {
            fa[i] = new File(fs[i]);
        }

        iterateOverFiles(fa);
    }

    protected void iterateOverFiles(File[] fa) {
        _iterateOverFiles(fa);
        cleanUp();
    }


    private void _iterateOverFiles(File[] fa) {
        for (File file : fa) {
            if(!file.getName().startsWith(".")){
                if (file.isDirectory()) {
                    _iterateOverFiles(file.listFiles());
                } else {
                    processFile(file.getAbsolutePath());
                }
            }
        }
    }


    static public String combineToRow(String[] rowArr) throws PatternMatchError {

        String[] cArr = new String[rowArr.length];
        Pattern pattern = Pattern.compile("([\\d]{2})\\.([\\d]{2})\\.([\\d]{4})");
        for (int i = 0, len = rowArr.length; i < len; i++) {
            String v = rowArr[i];
            switch (Util.getStrType(v)) {
                case VDATE:
                    Matcher m = pattern.matcher(v);
                    if (m.find() && m.groupCount() == 3) {
                        cArr[i] = m.group(3) + "-" + m.group(2) + "-" + m.group(1);
                    } else {
                        throw new PatternMatchError();
                    }
                    break;
                case VSTRING:
                    cArr[i] = "\"" + v + "\"";
                    break;
                case VINTEGER:
                case VDECIMAL:
                    cArr[i] = v;
                    break;
                case NONE:
                    cArr[i] = "";
                    break;
                default:
                    cArr[i] = v;
            }
        }

        return String.join(MainApp.FIELD_DELIMETER, cArr);
    }

    protected abstract void processFile(String path);

    protected abstract void cleanUp();

}


