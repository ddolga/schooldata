package com.nadia.data.processors.excel;

import com.nadia.data.api.IFileIterator;
import com.nadia.data.processors.AbstractProcessor;
import com.nadia.data.util.Formatters;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;

import java.io.FileNotFoundException;
import java.io.PrintStream;

/**
 * Created by ddolgachev on 8/4/2016.
 */
public class SAXProcessor extends AbstractProcessor {

    public SAXProcessor(IFileIterator fileIterator) {
        super(fileIterator);
    }

    @Override
    public void setup() throws FileNotFoundException {

    }

    @Override
    public void process(String inFileName) {

        OPCPackage p = null;
        try {
            p = OPCPackage.open(inFileName, PackageAccess.READ);

            String outFileName = Formatters.formatOutputFileName(inFileName, "converted", "csv");
            PrintStream ps = new PrintStream(outFileName);
            XLSX2CSV xlsx2csv = new XLSX2CSV(p, ps, -1);
            xlsx2csv.process();
            p.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void cleanUp() {

    }
}
