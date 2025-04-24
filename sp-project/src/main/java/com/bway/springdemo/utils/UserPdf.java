package com.bway.springdemo.utils;

import java.awt.Color;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.bway.springdemo.model.User;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class UserPdf extends AbstractPdfView {

    @Override
    protected void buildPdfMetadata(Map<String, Object> model, Document document, HttpServletRequest request) {
        HeaderFooter header = new HeaderFooter(new Phrase("USER PDF VIEW"), false);
        header.setAlignment(Element.ALIGN_CENTER);
        document.setHeader(header);

        HeaderFooter footer = new HeaderFooter(new Phrase(new Date() + " (C) bway, Page # "), true);
        footer.setAlignment(Element.ALIGN_CENTER);
        document.setFooter(footer);
    }

    @Override
    protected void buildPdfDocument(
            Map<String, Object> model,
            Document document,
            PdfWriter writer,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        response.addHeader("Content-Disposition", "attachment;filename=user.pdf");

        @SuppressWarnings("unchecked")
        List<User> list = (List<User>) model.get("user");

        Font titleFont = new Font(Font.TIMES_ROMAN, 30, Font.BOLD, Color.RED);
        Paragraph title = new Paragraph("USER DATA", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingBefore(20.0f);
        title.setSpacingAfter(25.0f);
        document.add(title);

        Font tableHead = new Font(Font.TIMES_ROMAN, 12, Font.BOLD, Color.BLUE);
        PdfPTable table = new PdfPTable(5); // Columns: ID, First Name, Last Name, Number, Amount

        table.addCell(new Phrase("ID", tableHead));
        table.addCell(new Phrase("FIRST NAME", tableHead));
        table.addCell(new Phrase("LAST NAME", tableHead));
        table.addCell(new Phrase("NUMBER", tableHead));
        table.addCell(new Phrase("AMOUNT", tableHead));

        for (User u : list) {
            table.addCell(String.valueOf(u.getId()));
            table.addCell(u.getFname());
            table.addCell(u.getLname());
            table.addCell(u.getNumber());
            table.addCell(String.valueOf(u.getAmount()));
        }

        document.add(table);
    }
}