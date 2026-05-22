package com.miempresa.workforce_form.service;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.awt.Color;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {

    // === Generar PDF ===
    public void generateRequestsReport(List<Map<String, Object>> requests, OutputStream outputStream) {
        try {
            Document document = new Document(PageSize.A4, 36, 36, 36, 36);
            PdfWriter.getInstance(document, outputStream);
            document.open();

            // 🔹 Fuente para el título
            com.lowagie.text.Font titleFont =
                    new com.lowagie.text.Font(com.lowagie.text.Font.HELVETICA, 18, com.lowagie.text.Font.BOLD, new Color(0, 60, 130));

            Paragraph title = new Paragraph("Requests Report", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            // 🔹 Subtítulo
            com.lowagie.text.Font subtitleFont =
                    new com.lowagie.text.Font(com.lowagie.text.Font.HELVETICA, 12, com.lowagie.text.Font.ITALIC, new Color(100, 100, 100));

            Paragraph subtitle = new Paragraph("Generated automatically by Workforce Module", subtitleFont);
            subtitle.setAlignment(Element.ALIGN_CENTER);
            subtitle.setSpacingAfter(20);
            document.add(subtitle);

            // 🔹 Tabla PDF
            PdfPTable table = new PdfPTable(6);
            table.setWidthPercentage(100);
            addTableHeader(table);
            addRows(table, requests);
            document.add(table);

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addTableHeader(PdfPTable table) {
        String[] headers = {"ID", "Submitted By", "Scope", "Role", "Type", "Status"};
        for (String columnTitle : headers) {
            PdfPCell header = new PdfPCell();
            header.setBackgroundColor(new Color(0, 60, 130));
            header.setBorderWidth(1);
            header.setPhrase(new Phrase(columnTitle,
                    new com.lowagie.text.Font(com.lowagie.text.Font.HELVETICA, 12, com.lowagie.text.Font.BOLD, Color.WHITE)));
            header.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(header);
        }
    }

    private void addRows(PdfPTable table, List<Map<String, Object>> requests) {
        for (Map<String, Object> row : requests) {
            table.addCell(String.valueOf(row.get("id")));
            table.addCell(String.valueOf(row.get("submittedBy")));
            table.addCell(String.valueOf(row.get("scope")));
            table.addCell(String.valueOf(row.get("role")));
            table.addCell(String.valueOf(row.get("requestType")));
            table.addCell(String.valueOf(row.get("status")));
        }
    }

    // === Generar Excel ===
    public void generateRequestsExcel(List<Map<String, Object>> requests, OutputStream outputStream) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Requests");

        String[] columns = {"ID", "Submitted By", "Scope", "Role", "Type", "Status"};
        org.apache.poi.ss.usermodel.Row headerRow = sheet.createRow(0); // 👈 especificamos ruta completa

        // 🔹 Estilo encabezado Excel
        CellStyle headerStyle = workbook.createCellStyle();
        org.apache.poi.ss.usermodel.Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setColor(IndexedColors.WHITE.getIndex());
        headerStyle.setFont(headerFont);
        headerStyle.setFillForegroundColor(IndexedColors.BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        for (int i = 0; i < columns.length; i++) {
            org.apache.poi.ss.usermodel.Cell cell = headerRow.createCell(i); // 👈 especificamos ruta completa
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerStyle);
        }

        // 🔹 Contenido Excel
        int rowNum = 1;
        for (Map<String, Object> data : requests) {
            org.apache.poi.ss.usermodel.Row row = sheet.createRow(rowNum++); // 👈 ruta completa
            row.createCell(0).setCellValue(String.valueOf(data.get("id")));
            row.createCell(1).setCellValue(String.valueOf(data.get("submittedBy")));
            row.createCell(2).setCellValue(String.valueOf(data.get("scope")));
            row.createCell(3).setCellValue(String.valueOf(data.get("role")));
            row.createCell(4).setCellValue(String.valueOf(data.get("requestType")));
            row.createCell(5).setCellValue(String.valueOf(data.get("status")));
        }

        for (int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }

        workbook.write(outputStream);
        workbook.close();
    }
}
