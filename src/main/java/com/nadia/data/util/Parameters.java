package com.nadia.data.util;

import com.nadia.data.api.ParametersInterface;

import java.io.FileNotFoundException;
import java.util.ArrayList;


/* Process arguments passed on the command line, identifying the following parameters:
    name of the process to run
    target file name
    column headars
    files to process
 */


public class Parameters implements ParametersInterface {

    private String[] fa;
    private String targetFileName;
    private String header;
    private String importerType;


    private static final int IMPORTER_TYPE_IDX = 0;
    private int skippedRows = 0;


    public Parameters(String[] args) {
        parseParameters(args);
    }

    //proceesorName [-o targetFileName][-h "colHeader1,colHEader2,colHeaderN"][--springArguments]sourceFile1[,sourceFile2,sourceFIleN]
    private void parseParameters(String[] args) {

        if (args.length > 0) {
            ArrayList<String> fa = new ArrayList<>();

            //type of import processor
            this.importerType = args[IMPORTER_TYPE_IDX];

            int len = args.length;
            int nextIdx = IMPORTER_TYPE_IDX + 1;
            //iterate over remaining parameters
            while (nextIdx < len) {
                if (args[nextIdx].equals("-o")) {
                    this.setTargetFileName(args[nextIdx + 1]);
                    nextIdx = nextIdx + 2;
                    //space delineated list of files to process
                } else if (args[nextIdx].equals("-h")) {
                    this.setHeader(args[nextIdx + 1]);
                    nextIdx = nextIdx + 2;
                } else if (args[nextIdx].startsWith("-s")) {
                    this.skippedRows = Integer.parseInt(args[nextIdx + 1]);
                    nextIdx = nextIdx + 2;
                } else if (args[nextIdx].startsWith("--")) {
                    //skip spring arguments
                    nextIdx++;
                } else {
                    //collect files to process
                    fa.add(args[nextIdx++]);
                }
            }
            this.setFa(fa.toArray(new String[0]));
        }

    }

    public String[] getFa() {
        return fa;
    }

    private void setFa(String[] fa) {
        this.fa = fa;
    }

    public String getTargetFileName() throws FileNotFoundException {
        if (targetFileName == null)
            throw new FileNotFoundException("Target file not specified");

        return targetFileName;
    }

    private void setTargetFileName(String targetFileName) {
        this.targetFileName = targetFileName;
    }

    public String getHeader() {
        return header;
    }

    public boolean hasHeader() {
        return header != null && !header.isEmpty();
    }

    public String getImporterType() {
        return importerType;
    }

    public int getSkippedRows() {
        return skippedRows;
    }

    private void setHeader(String header) {
        this.header = header;
    }

}
