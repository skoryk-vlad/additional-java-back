package org.example.services;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.models.NpmPackage;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Service
public class ExcelService {
    private void writeHeaderLine(XSSFWorkbook workbook) {
        XSSFSheet sheet = workbook.createSheet("Packages");

        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(sheet, row, 0, "ID", style);
        createCell(sheet, row, 1, "Name", style);
        createCell(sheet, row, 2, "Weekly Downloads", style);
        createCell(sheet, row, 3, "Version", style);
        createCell(sheet, row, 4, "Size", style);
        createCell(sheet, row, 5, "Last Publish", style);
    }

    private void createCell(XSSFSheet sheet, Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    private void writeDataLines(XSSFWorkbook workbook, List<NpmPackage> packages) {
        XSSFSheet sheet = workbook.getSheet("Packages");

        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (NpmPackage npmPackage: packages) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(sheet, row, columnCount++, npmPackage.getId(), style);
            createCell(sheet, row, columnCount++, npmPackage.getName(), style);
            createCell(sheet, row, columnCount++, npmPackage.getWeeklyDownloads(), style);
            createCell(sheet, row, columnCount++, npmPackage.getVersion(), style);
            createCell(sheet, row, columnCount++, npmPackage.getSize(), style);
            createCell(sheet, row, columnCount++, npmPackage.getLastPublish(), style);
        }
    }

    public void export(HttpServletResponse response, List<NpmPackage> packages) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();

        writeHeaderLine(workbook);
        writeDataLines(workbook, packages);

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();

        outputStream.close();
    }
}
