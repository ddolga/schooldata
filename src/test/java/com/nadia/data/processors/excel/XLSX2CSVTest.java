package com.nadia.data.processors.excel;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.junit.Test;

import java.io.File;
import java.io.PrintStream;

public class XLSX2CSVTest {

    @Test
    public void TestXLSX2CSV() throws Exception {
        OPCPackage pkg = OPCPackage.open(new File("data/Experience_Simple.xlsx"));
        PrintStream out = new PrintStream(new File("data/Experience_Simple.csv"));
        XLSX2CSV xlsx2CSV = new XLSX2CSV(pkg, out, -1);
        xlsx2CSV.process();
    }


}
