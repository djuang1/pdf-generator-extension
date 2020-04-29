package org.mule.extension.pdf.generator.internal;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.PdfWriter;
import org.apache.poi.xwpf.converter.pdf.PdfConverter;
import org.apache.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

public class PDFGeneratorTest {

    public static void main(String[] args) {

        PDFGeneratorOperations testOperation = new PDFGeneratorOperations();

        PDFGeneratorTest cwoWord = new PDFGeneratorTest();
        cwoWord.ConvertDocxToPDF("/Users/dejim.juang/Test.docx", "/Users/dejim.juang/Test.pdf");

        cwoWord.ConvertFileToPDF("/Users/dejim.juang/icon.png", "/Users/dejim.juang/boats.pdf");

        //InputStream is = new ByteArrayInputStream(obj.toString().getBytes());
    }

    public void ConvertDocxToPDF(String docPath, String pdfPath) {
        try {
            InputStream doc = new FileInputStream(new File(docPath));
            XWPFDocument document = new XWPFDocument(doc);

            PdfOptions options = PdfOptions.create();
            OutputStream out = new FileOutputStream(new File(pdfPath));
            PdfConverter.getInstance().convert(document, out, options);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void ConvertFileToPDF(String docPath, String pdfPath) {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(new File(pdfPath)));
            document.open();
            document.newPage();
            Image image = Image.getInstance(new File(docPath).getAbsolutePath());
            image.setAbsolutePosition(0, 0);
            image.setBorderWidth(0);
            document.add(image);
            document.close();

        } catch (IOException | DocumentException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
