import com.nadia.data.api.CellProcessorInterface;
import com.nadia.data.api.WorkbookProcessInterface;
import com.nadia.data.processors.file.excel.ProcessAllCells;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.nadia.data.processors.cell.Transliterator;

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

        transliterator.process("./data/original/Teachers_Test.xlsx",processor,0);

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
        Workbook wb = transliterator.process("./data/original/Teachers_Test.xlsx",  new Transliterator(),0);
        String result = wb.getSheetAt(0).getRow(3).getCell(3).getStringCellValue();
        assertEquals("uchitiel", result);
    }
}