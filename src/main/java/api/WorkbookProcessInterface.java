package api;

import org.apache.poi.ss.usermodel.Workbook;

public interface WorkbookProcessInterface {

    Workbook process(String inFileName, String outFileName, CellProcessorInterface cellProcessor);

}
