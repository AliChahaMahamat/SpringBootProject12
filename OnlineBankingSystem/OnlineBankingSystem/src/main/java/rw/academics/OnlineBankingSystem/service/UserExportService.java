package rw.academics.OnlineBankingSystem.service;


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import rw.academics.OnlineBankingSystem.model.MyAppUser;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class UserExportService {

    public ByteArrayInputStream exportUsersToExcel(List<MyAppUser> users) throws IOException {
        String[] columns = { "ID", "Username", "Email", "Full Name", "Date of Birth", "ID Type", "ID Number", "Phone Number" };

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Users");

            // Header row
            Row headerRow = sheet.createRow(0);
            for (int col = 0; col < columns.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(columns[col]);
                cell.setCellStyle(createHeaderCellStyle(workbook));
            }

            // Data rows
            int rowIdx = 1;
            for (MyAppUser user : users) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(user.getId());
                row.createCell(1).setCellValue(user.getUsername());
                row.createCell(2).setCellValue(user.getEmail());
                row.createCell(3).setCellValue(user.getFullName());
                // Check for null Date of Birth
                if (user.getDateOfBirth() != null) {
                    row.createCell(4).setCellValue(user.getDateOfBirth().toString());
                } else {
                    row.createCell(4).setCellValue("N/A"); // Or any placeholder for missing date
                }
                row.createCell(5).setCellValue(user.getIdType());
                row.createCell(6).setCellValue(user.getIdNumber());
                row.createCell(7).setCellValue(user.getPhoneNumber());
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }

    private CellStyle createHeaderCellStyle(Workbook workbook) {
        CellStyle headerStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        headerStyle.setFont(font);
        return headerStyle;
    }
}
