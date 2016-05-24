package com.nadia.data.util;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

public class Parameters implements com.nadia.data.api.ParametersInterface, CommandLineRunner {

    private int type;
    private String[] fa;
    private String targetFileName;
    private String header;
    private String importerType;


    private static final int IMPORTER_TYPE = 0;
    private static final int FUNC_TYPE  = 1;


    @Override
    public void run(String... args) throws Exception {

        if (args.length > 0) {
            ArrayList<String> fa = new ArrayList<>();

            this.importerType = args[IMPORTER_TYPE];

            this.setType(Integer.parseInt(args[FUNC_TYPE]));
            int len = args.length;
            int nextIdx = FUNC_TYPE + 1;
            while (nextIdx < len) {
                if (args[nextIdx].equals("-o")) {
                    this.setTargetFileName(args[nextIdx + 1]);
                    nextIdx = nextIdx + 2;
                } else if (args[nextIdx].equals("-h")) {
                    this.setHeader(args[nextIdx + 1]);
                    nextIdx = nextIdx + 2;
                } else {
                    fa.add(args[nextIdx++]);
                }
            }
            this.setFa(fa.toArray(new String[0]));
        }

    }

    @Override
    public int getType() {
        return type;
    }

    private void setType(int type) {
        this.type = type;
    }

    @Override
    public String[] getFa() {
        return fa;
    }

    private void setFa(String[] fa) {
        this.fa = fa;
    }

    @Override
    public String getTargetFileName() {
        return targetFileName;
    }

    private void setTargetFileName(String targetFileName) {
        this.targetFileName = targetFileName;
    }

    @Override
    public String getHeader() {
        return header;
    }

    @Override
    public boolean hasHeader() {
        return header != null && !header.isEmpty();
    }

    @Override
    public String getImporterType() {
        return importerType;
    }

    private void setHeader(String header) {
        this.header = header;
    }

}
