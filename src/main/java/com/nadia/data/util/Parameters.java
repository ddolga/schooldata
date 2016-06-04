package com.nadia.data.util;

import com.nadia.data.api.ParametersInterface;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Parameters implements ParametersInterface {

    private String[] fa;
    private String targetFileName;
    private String header;
    private String importerType;


    private static final int IMPORTER_TYPE = 0;


    public Parameters(String[] args) {
        setParameters(args);
    }

    private void setParameters(String[] args) {

        if (args.length > 0) {
            ArrayList<String> fa = new ArrayList<>();

            this.importerType = args[IMPORTER_TYPE];

            int len = args.length;
            int nextIdx = IMPORTER_TYPE + 1;
            while (nextIdx < len) {
                if (args[nextIdx].equals("-o")) {
                    this.setTargetFileName(args[nextIdx + 1]);
                    nextIdx = nextIdx + 2;
                } else if (args[nextIdx].equals("-h")) {
                    this.setHeader(args[nextIdx + 1]);
                    nextIdx = nextIdx + 2;
                } else if (args[nextIdx].startsWith("--")) {
                    //skip spring arguments
                    nextIdx++;
                } else {
                    fa.add(args[nextIdx++]);
                }
            }
            this.setFa(fa.toArray(new String[0]));
        }

    }

    @Override
    public String[] getFa() {
        return fa;
    }

    private void setFa(String[] fa) {
        this.fa = fa;
    }

    @Override
    public String getTargetFileName() throws FileNotFoundException {
        if(targetFileName == null)
            throw new FileNotFoundException("Target file not specified");

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
