import com.nadia.data.FileIterator;
import com.nadia.data.processors.excel.CombineToCSV;
import com.nadia.data.translators.TransliteratorForCombine;
import org.junit.Test;

public class CombineToCSVTest {

    @Test
    public void testProcess() throws Exception {
        CombineToCSV exporter = new CombineToCSV(new FileIterator(), new TransliteratorForCombine());
        exporter.process("./data/original/Teachers_Test.xlsx");
    }
}