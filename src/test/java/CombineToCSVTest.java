import com.nadia.data.importers.excel.CombineToCSV;
import org.junit.Test;
import com.nadia.data.processors.Transliterator;

public class CombineToCSVTest {

    @Test
    public void testProcess() throws Exception {
        CombineToCSV exporter = new CombineToCSV();
        exporter.process("./data/original/Teachers_Test.xlsx","./data/original/Teachers_Test_converted.csv",new Transliterator(),0);
    }
}