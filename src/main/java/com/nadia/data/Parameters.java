package com.nadia.data;

import java.util.ArrayList;

public class Parameters {

    private int type;
    private String[] fa;
    private String targetFileName;

    public static Parameters processParameters(String title, String[] args, String[] commands) {

        int type = 0;
        ArrayList<String> fa = new ArrayList<>();
        String targetFileName = null;
        if (args.length > 0) {
            type = Integer.parseInt(args[0]);

            int len = args.length;
            int nextIdx = 1;
            while (nextIdx < len) {
                if (args[nextIdx].equals("-o")) {
                    targetFileName = args[nextIdx + 1];
                    nextIdx = nextIdx + 2;
                } else {
                    fa.add(args[nextIdx++]);
                }
            }
        }

        Parameters params = new Parameters();
        params.setFa(fa.toArray(new String[0]));
        params.setType(type);
        params.setTargetFileName(targetFileName);
        return params;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String[] getFa() {
        return fa;
    }

    public void setFa(String[] fa) {
        this.fa = fa;
    }

    public String getTargetFileName() {
        return targetFileName;
    }

    public void setTargetFileName(String targetFileName) {
        this.targetFileName = targetFileName;
    }
}
