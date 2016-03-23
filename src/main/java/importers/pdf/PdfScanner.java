package importers.pdf;

import api.FormProcessInterface;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;

public class PdfScanner implements FormProcessInterface {
    @Override
    public void process(String inFileName, String outFileName, int limit) {

        try {
            PDDocument pdfDoc = PDDocument.load(new File(inFileName));
            PDFTextStripper pdfTextStripper = new PDFTextStripper();
            String output = pdfTextStripper.getText(pdfDoc);
            System.out.println(output);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
