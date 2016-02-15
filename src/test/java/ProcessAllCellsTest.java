import api.CellProcessorInterface;
import api.WorkbookProcessInterface;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import processors.Transliterator;

import static org.junit.Assert.*;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ProcessAllCellsTest {

    WorkbookProcessInterface transliterator;

    @Mock
    CellProcessorInterface processor;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        transliterator = new ProcessAllCells();
    }

    @Test
    public void testProcess() throws Exception {

        transliterator.process("./data/original/Teachers_Test.xlsx",null,processor);

        ArgumentMatcher<Cell> matcher = new ArgumentMatcher<Cell>() {
            @Override
            public boolean matches(Object o) {
                return true;
            }
        };
        verify(processor, times(24)).processCell(argThat(matcher));
    }


    @Test
    public void testWalkCells2() throws Exception {
        Workbook wb = transliterator.process("./data/original/Teachers_Test.xlsx", null, new Transliterator());
        String result = wb.getSheetAt(0).getRow(3).getCell(3).getStringCellValue();
        assertEquals("uchitiel", result);
    }
}