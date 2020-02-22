import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.BadPdfFormatException;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class CombineTwoFiles {
    public static final String APPEND = "append";
    public static final String MERGE = "merge";

    public static void main(String[] args) throws IOException {
        if (args.length < 3) {
            System.out.println("You must pass two files to run\n");
            return;
        }
        if (!checkFile(args[1])) {
            return;
        }
        if (!checkFile(args[2])) {
            return;
        }

        if (args[0].equalsIgnoreCase(APPEND)) {
            append(args[1], args[2]);
        } else {
            merge(args[1], args[2]);
        }
    }

    private static void append(String inFile1, String inFile2) {
        try {
            String fileOutput = getFileOutput(inFile1, inFile2, "append");

            PdfReader readerFile1 = new PdfReader(inFile1);
            PdfReader readerFile2 = new PdfReader(inFile2);
            int totalPagesFile1 = readerFile1.getNumberOfPages();
            int totalPagesFile2 = readerFile2.getNumberOfPages();
            System.out.println("Total pages file1: " + totalPagesFile1 + " total pages file2: " + totalPagesFile2 + " file output is" + fileOutput);

            Document document = new Document(readerFile1.getPageSizeWithRotation(1));
            PdfCopy writer = new PdfCopy(document, new FileOutputStream(fileOutput));
            document.open();
            addPages(readerFile1, totalPagesFile1, writer);
            addPages(readerFile2, totalPagesFile2, writer);

            document.close();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void merge(String inFile1, String inFile2) {
        try {
            String fileOutput = getFileOutput(inFile1, inFile2, "merge");

            PdfReader readerFile1 = new PdfReader(inFile1);
            PdfReader readerFile2 = new PdfReader(inFile2);
            int totalPagesFile1 = readerFile1.getNumberOfPages();
            int totalPagesFile2 = readerFile2.getNumberOfPages();
            System.out.println("Total pages file1: " + totalPagesFile1 + " total pages file2: " + totalPagesFile2 + " file output is" + fileOutput);

            Document document = new Document(readerFile1.getPageSizeWithRotation(1));
            PdfCopy writer = new PdfCopy(document, new FileOutputStream(fileOutput));
            document.open();

            int maxPages = totalPagesFile1 > totalPagesFile2 ? totalPagesFile1 : totalPagesFile2;
            int page1 = 0;
            int page2 = 0;
            if (totalPagesFile1 > totalPagesFile2) {
                for (int i = 0; i < totalPagesFile1 - totalPagesFile2; i++) {
                    addPage(readerFile1, totalPagesFile1, writer, i);
                    ++page1;
                }
            }
            if (totalPagesFile2 > totalPagesFile1) {
                for (int i = 0; i < totalPagesFile2 - totalPagesFile1; i++) {
                    addPage(readerFile2, totalPagesFile2, writer, i);
                    ++page2;
                }
            }

            for (int n = 0; n < maxPages; n++) {
                addPage(readerFile1, totalPagesFile1, writer, page1++);
                addPage(readerFile2, totalPagesFile2, writer, page2++);
            }

            document.close();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addPage(PdfReader readerFile1, int totalPagesFile1, PdfCopy writer, int n) throws IOException, BadPdfFormatException {
        if (n < totalPagesFile1) {
            PdfImportedPage page1 = writer.getImportedPage(readerFile1, n + 1);
            writer.addPage(page1);
        }
    }

    private static String getFileOutput(String inFile1, String inFile2, String extra) {

        String path = inFile1.substring(0, inFile1.lastIndexOf("/") + 1);
        String fileName1 = inFile1.substring(inFile1.lastIndexOf("/") + 1, inFile1.lastIndexOf("."));
        String fileName2 = inFile2.substring(inFile2.lastIndexOf("/") + 1, inFile2.lastIndexOf("."));

        return path + fileName1 + "-" + fileName2 + "-" + extra + ".pdf";
    }

    private static boolean checkFile(String inFile1) {
        File file1 = new File(inFile1);
        if (!file1.exists()) {
            System.out.println("File " + inFile1 + " does not exist");
            return false;
        }
        return true;
    }

    private static void addPages(PdfReader readerFile1, int totalPagesFile1, PdfCopy writer) throws IOException, BadPdfFormatException {
        for (int i = 0; i < totalPagesFile1; i++) {
            PdfImportedPage page1 = writer.getImportedPage(readerFile1, i + 1);
            writer.addPage(page1);
        }
    }
}
