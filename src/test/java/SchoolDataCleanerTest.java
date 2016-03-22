import org.junit.Test;

import static org.junit.Assert.*;


public class SchoolDataCleanerTest {


    @Test
    public void test_formatOutputFileName() throws Exception {
        SchoolDataCleaner cleaner = new SchoolDataCleaner();
        String result = cleaner._formatOutputFileName("c:/users/who/suck/testies.txt","converted");
        assertEquals("c:/users/who/suck/testies_converted.txt",result);
    }

    @Test
    public void test_formatOutputFileName2() throws Exception {
        SchoolDataCleaner cleaner = new SchoolDataCleaner();
        String result = cleaner._formatOutputFileName("c:/users/who/suck/testies.txt","converted","cvs");
        assertEquals("c:/users/who/suck/testies_converted.cvs",result);
    }

    @Test
    public void testSplitFiles() throws Exception {
        String[] result1 = SchoolDataCleaner.splitFiles("File1.txt");
        assertEquals(1,result1.length);
        assertArrayEquals(new String[]{"File1.txt"},result1);
    }


    @Test
    public void testSplitFiles2() throws Exception {
        String[] result1 = SchoolDataCleaner.splitFiles("File1.txt, File2.txt,  File3.txt,File4.txt");
        assertEquals(4,result1.length);
        assertArrayEquals(new String[]{"File1.txt","File2.txt","File3.txt","File4.txt"},result1);
    }
}