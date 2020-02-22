import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;

import java.io.FileOutputStream;

public class SplitPDFFile {

    public static final String DEST = "./tmp/splitDocument1_%s.pdf";

    public static final String RESOURCE = "./tmp/Wrong.pdf";
    /**
     * @param args
     */
    public static void main(String[] args) {

        try {
            String inFile = RESOURCE; // args[0].toLowerCase();
            System.out.println ("Reading " + inFile);
            PdfReader reader = new PdfReader(inFile);
            int n = reader.getNumberOfPages();
            System.out.println ("Number of pages : " + n);
            int i = 0;
            while ( i < n ) {
                String outFile = inFile.substring(0, inFile.indexOf(".pdf"))
                        + "-" + String.format("%03d", i + 1) + ".pdf";
                System.out.println ("Writing " + outFile);
                Document document = new Document(reader.getPageSizeWithRotation(1));
                PdfCopy writer = new PdfCopy(document, new FileOutputStream(outFile));
                document.open();
                PdfImportedPage page = writer.getImportedPage(reader, ++i);
                writer.addPage(page);
                document.close();
                writer.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        /* example :
            java SplitPDFFile d:\temp\x\tx.pdf

            Reading d:\temp\x\tx.pdf
            Number of pages : 3
            Writing d:\temp\x\tx-001.pdf
            Writing d:\temp\x\tx-002.pdf
            Writing d:\temp\x\tx-003.pdf
         */

    }
}