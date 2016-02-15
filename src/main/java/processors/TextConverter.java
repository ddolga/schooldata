package processors;

import api.CellProcessorInterface;
import org.apache.poi.ss.usermodel.Cell;

public class TextConverter implements CellProcessorInterface {


    @Override
    public String processCell(Cell cell) {
        return cell.toString();
    }
}
