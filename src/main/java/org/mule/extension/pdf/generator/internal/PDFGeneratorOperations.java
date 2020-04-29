package org.mule.extension.pdf.generator.internal;

import static org.mule.runtime.extension.api.annotation.param.MediaType.ANY;
import static org.mule.runtime.extension.api.annotation.param.Optional.PAYLOAD;

import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.MediaType;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;

import java.io.*;

import org.apache.poi.util.IOUtils;
import org.apache.poi.xwpf.converter.pdf.PdfConverter;
import org.apache.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.PdfWriter;


public class PDFGeneratorOperations {

    @DisplayName("Convert DOCX to PDF")
    @MediaType(value = ANY, strict = false)
    public InputStream docxToPDF(@Optional(defaultValue = PAYLOAD) InputStream body) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            XWPFDocument document = new XWPFDocument(body);
            PdfOptions options = PdfOptions.create();
            PdfConverter.getInstance().convert(document, baos, options);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        byte[] bytes = baos.toByteArray();

        return new ByteArrayInputStream(bytes);
    }

    @DisplayName("Convert Image to PDF")
    @MediaType(value = ANY, strict = false)
    public InputStream imageToPDF(@Optional(defaultValue = PAYLOAD) InputStream body) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {

            Document document = new Document();
            PdfWriter.getInstance(document, baos);
            document.open();
            document.newPage();

            Image image = Image.getInstance(IOUtils.toByteArray(body));
            image.setAbsolutePosition(0, 0);
            image.setBorderWidth(0);

            document.add(image);
            document.close();

        } catch (IOException | DocumentException ex) {
            System.out.println(ex.getMessage());
        }
        byte[] bytes = baos.toByteArray();

        return new ByteArrayInputStream(bytes);
    }
}
