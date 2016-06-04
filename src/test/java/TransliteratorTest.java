import com.nadia.data.translators.Transliterator;
import org.apache.poi.ss.usermodel.*;
import org.junit.Test;


import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TransliteratorTest {

    private Cell getMockCell(int type, Object value) {
        Cell testCell = mock(Cell.class);
        when(testCell.getCellType()).thenReturn(type);
        when(testCell.getStringCellValue()).thenReturn(value.toString());
        return testCell;
    }


    @Test
    public void testProcessCell() throws Exception {
        Transliterator tranny = new Transliterator();
        Cell testCell = getMockCell(Cell.CELL_TYPE_NUMERIC, "2");

        String result = tranny.processCell(testCell);
        assertNull(result);
    }


    @Test
    public void testProcessCell2() throws Exception {
        Transliterator tranny = new Transliterator();
        Cell testCell = getMockCell(Cell.CELL_TYPE_STRING, "Test");

        String result = tranny.processCell(testCell);
        assertEquals("Test", result);
    }

    @Test
    public void testProcessCell3() throws Exception {
        Transliterator tranny = new Transliterator();
        Cell testCell = getMockCell(Cell.CELL_TYPE_STRING,"възпитател");

        String result = tranny.processCell(testCell);
        assertEquals("vzpitatiel", result);
    }
}

