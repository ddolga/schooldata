import importers.pdf.PdfScanner;

public class PdfTextConverter extends Application {

    private final static String[] commands = {"1. PDF to Text"};

    public static void main(String[] args) {
        Parameters.processParameters(args, commands);
        PdfTextConverter pdfTextConverter = new PdfTextConverter();
        pdfTextConverter.cleanData(Parameters.type, Parameters.fa, 0);
    }

    private void cleanData(int type, String[] fa, int limit) {
        switch (type) {
            case 1:
                //CONVERT PDF TO TEXT
                PdfScanner pdf = new PdfScanner();
                for (String s : fa) {
//                    System.out.println("Processing: " + s);
                    pdf.process(s, _formatOutputFileName(s, "extracted"), limit);
                }
                break;
        }
    }

}
