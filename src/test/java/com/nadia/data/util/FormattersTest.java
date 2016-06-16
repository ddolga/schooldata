package com.nadia.data.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class FormattersTest {

    @Test
    public void insertIntoArray() throws Exception {
        String[] testArray = new String[]{"one", "two", "three", "five"};
        String[] resultArr = Formatters.insertIntoArray(testArray, 3, "four");
        assertEquals("four", resultArr[3]);
        assertTrue(resultArr.length == testArray.length + 1);
    }

    @Test
    public void getYearFromFileName() throws Exception {

        String testFileName = "C:/path1/path2/2014.csv";

        String result = Formatters.getYearFromFileName(testFileName);
        assertEquals("2014", result);
    }

}