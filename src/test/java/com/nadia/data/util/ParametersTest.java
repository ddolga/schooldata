package com.nadia.data.util;

import com.nadia.data.api.ParametersInterface;
import org.junit.Test;

import static org.junit.Assert.*;

public class ParametersTest {


    private final static String[] args1 = {"SomeBeanName", "file1", "file2", "file3"};
    private final static String[] args2 = {"SomeBeanName", "-o", "target1", "file1", "file2", "file3"};
    private final static String[] args3 = {"SomeBeanName", "-o", "target1", "-h", "col1,col2,col3,col4", "file1", "file2", "file3"};


    @Test
    public void getFa() throws Exception {

        ParametersInterface params = new Parameters(args1);

        String[] result = params.getFa();
        assertEquals("file1", result[0]);
        assertEquals("file2", result[1]);
        assertEquals("file3", result[2]);
    }


    @Test
    public void getImporterType() throws Exception {
        ParametersInterface params = new Parameters(args1);
        assertEquals(params.getImporterType(), "SomeBeanName");


        ParametersInterface params2 = new Parameters(args3);
        assertEquals(params2.getImporterType(), "SomeBeanName");
    }

    @Test
    public void getTargetFileName() throws Exception {

        ParametersInterface params = new Parameters(args2);
        assertEquals("target1", params.getTargetFileName());


        ParametersInterface params2 = new Parameters(args3);
        assertEquals("target1", params2.getTargetFileName());
    }

    @Test
    public void getHeader() throws Exception {
        ParametersInterface params = new Parameters(args3);
        assertEquals("col1,col2,col3,col4", params.getHeader());
    }

    @Test
    public void hasHeader() throws Exception {
        ParametersInterface params = new Parameters(args3);
        assertTrue(params.hasHeader());

        ParametersInterface params2 = new Parameters(args1);
        assertFalse(params2.hasHeader());
    }


}