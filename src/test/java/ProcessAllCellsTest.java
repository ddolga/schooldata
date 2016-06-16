import com.nadia.data.FileIterator;
import com.nadia.data.api.CellProcessorInterface;
import com.nadia.data.api.IProcessFile;
import com.nadia.data.processors.excel.ProcessAllCells;
import org.apache.poi.ss.usermodel.Cell;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ProcessAllCellsTest {

    IProcessFile transliterator;

    @Mock
    CellProcessorInterface processor;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        transliterator = new ProcessAllCells(new FileIterator(), processor);
    }

    @Test
    public void testProcess() throws Exception {
        transliterator.process("./data/original/Teachers_Test.xlsx");

        ArgumentMatcher<Cell> matcher = new ArgumentMatcher<Cell>() {
            @Override
            public boolean matches(Object o) {
                return true;
            }
        };
        verify(processor, times(24)).processCell(argThat(matcher));
    }

}