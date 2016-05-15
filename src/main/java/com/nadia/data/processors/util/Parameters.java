package com.nadia.data.processors.util;

import java.util.ArrayList;

public class Parameters {

    private int type;
    private String[] fa;
    private String targetFileName;
    private String header;

    public static Parameters processParameters( String[] args) {

        ArrayList<String> fa = new ArrayList<>();
        Parameters params = new Parameters();
        if (args.length > 0) {
            params.setType(Integer.parseInt(args[0]));
            int len = args.length;
            int nextIdx = 1;
            while (nextIdx < len) {
                if (args[nextIdx].equals("-o")) {
                    params.setTargetFileName(args[nextIdx + 1]);
                    nextIdx = nextIdx + 2;
                }else if(args[nextIdx].equals("-h")){
                    params.setHeader(args[nextIdx + 1]);
                    nextIdx = nextIdx + 2;
                } else {
                    fa.add(args[nextIdx++]);
                }
            }
        }

        params.setFa(fa.toArray(new String[0]));
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

    public String getHeader() {
        return header;
    }

    public boolean hasHeader(){
        return header != null && !header.isEmpty();
    }

    public void setHeader(String header) {
        this.header = header;
    }
}
