

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPage;
import com.itextpdf.text.pdf.PdfPages;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.interfaces.PdfDocumentActions;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class PdfReadExample {

    private static final String FILE_NAME = "./tmp/itext.pdf";
    private static final String FILE_NAME_OUT = "./tmp/itextOut.pdf";

    public static void main(String[] args) {

        PdfReader reader;

        Document document = new Document();

        try {

            reader = new PdfReader(FILE_NAME);

            PdfWriter.getInstance(document, new FileOutputStream(new File(FILE_NAME_OUT)));
            document.open();

//            reader = new PdfReader("f:/itext.pdf");

            // pageNumber = 1
            String textFromPage = PdfTextExtractor.getTextFromPage(reader, 1);
            byte[] x = reader.getPageContent(1);

            document.newPage();
            System.out.println(textFromPage);

            reader.close();

        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        }

    }

}
