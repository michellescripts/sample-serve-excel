package com.example.excel.service

import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy.CREATE_NULL_AS_BLANK
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service

@Service
class DownloadService {
    fun template(): Workbook {
        val workbook = XSSFWorkbook(ClassPathResource("file_template.xlsx").inputStream)
        val firstSheet = workbook.getSheetAt(0)

        for (i in 1..2) {
            println(i)
            val currentRow: Row = firstSheet.getRow(i) ?: firstSheet.createRow(i)

            currentRow.getCell(0, CREATE_NULL_AS_BLANK).setCellValue("header1-row${i}")
            currentRow.getCell(1, CREATE_NULL_AS_BLANK).setCellValue("header2-row${i}")
            currentRow.getCell(2, CREATE_NULL_AS_BLANK).setCellValue("header3-row${i}")
            currentRow.getCell(3, CREATE_NULL_AS_BLANK).setCellValue("header4-row${i}")
            currentRow.getCell(4, CREATE_NULL_AS_BLANK).setCellValue("header5-row${i}")
        }

        return workbook
    }
}
