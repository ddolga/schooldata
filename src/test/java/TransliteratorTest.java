import org.apache.poi.ss.formula.FormulaParseException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.junit.Test;
import com.nadia.data.processors.Transliterator;

import java.util.Calendar;
import java.util.Date;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class TransliteratorTest {



    @Test
    public void testProcessCell() throws Exception {
        Transliterator tranny = new Transliterator();
        String result = tranny.processCell(new TestCell<Integer>(Cell.CELL_TYPE_NUMERIC, 2));
        assertNull(result);
    }


    @Test
    public void testProcessCell2() throws Exception {
        Transliterator tranny = new Transliterator();
        String result = tranny.processCell(new TestCell<String>(Cell.CELL_TYPE_STRING, "Test"));
        assertEquals("Test", result);
    }

    @Test
    public void testProcessCell3() throws Exception {
        Transliterator tranny = new Transliterator();
        String result = tranny.processCell(new TestCell<String>(Cell.CELL_TYPE_STRING, "възпитател"));
        assertEquals("vzpitatiel", result);
    }
}

class TestCell<T> implements Cell {


    T cellValue;

    public TestCell(int cellType, T value) {
        this.setCellType(cellType);
        this.setCellValue(value);
    }

    public TestCell(int cellType, int value) {
        this.setCellType(cellType);
        this.setCellValue(value);
    }


    private int cellType;

    @Override
    public int getColumnIndex() {
        return 0;
    }

    @Override
    public int getRowIndex() {
        return 0;
    }

    @Override
    public Sheet getSheet() {
        return null;
    }

    @Override
    public Row getRow() {
        return null;
    }

    @Override
    public void setCellType(int i) {
        cellType = i;
    }

    @Override
    public int getCellType() {
        return cellType;
    }

    @Override
    public int getCachedFormulaResultType() {
        return 0;
    }

    public void setCellValue(T v) {
       cellValue = v;
    }

    @Override
    public void setCellValue(double v) {

    }

    @Override
    public void setCellValue(Date date) {

    }

    @Override
    public void setCellValue(Calendar calendar) {

    }

    @Override
    public void setCellValue(RichTextString richTextString) {

    }

    @Override
    public void setCellValue(String s) {

    }

    @Override
    public void setCellFormula(String s) throws FormulaParseException {

    }

    @Override
    public String getCellFormula() {
        return null;
    }

    @Override
    public double getNumericCellValue() {
        return Double.parseDouble(cellValue.toString());
    }

    @Override
    public Date getDateCellValue() {
        return null;
    }

    @Override
    public RichTextString getRichStringCellValue() {
        return null;
    }

    @Override
    public String getStringCellValue() {
        return cellValue.toString();
    }

    @Override
    public void setCellValue(boolean b) {

    }

    @Override
    public void setCellErrorValue(byte b) {

    }

    @Override
    public boolean getBooleanCellValue() {
        return false;
    }

    @Override
    public byte getErrorCellValue() {
        return 0;
    }

    @Override
    public void setCellStyle(CellStyle cellStyle) {

    }

    @Override
    public CellStyle getCellStyle() {
        return null;
    }

    @Override
    public void setAsActiveCell() {

    }

    @Override
    public void setCellComment(Comment comment) {

    }

    @Override
    public Comment getCellComment() {
        return null;
    }

    @Override
    public void removeCellComment() {

    }

    @Override
    public Hyperlink getHyperlink() {
        return null;
    }

    @Override
    public void setHyperlink(Hyperlink hyperlink) {

    }

    @Override
    public void removeHyperlink() {

    }

    @Override
    public CellRangeAddress getArrayFormulaRange() {
        return null;
    }

    @Override
    public boolean isPartOfArrayFormulaGroup() {
        return false;
    }
}
