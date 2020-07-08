package com.example.excel.service

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class DownloadServiceTest {

    private val subject: DownloadService = DownloadService()

    @Test
    fun `template generates an excel workbook with data without overwriting the column headers`() {
        val headers = listOf("header 1", "header 2", "header 3", "header 4", "header 5")
        val data = listOf(
            RowData(
                header1 = "header1-row1",
                header2 = "header2-row1",
                header3 = "header3-row1",
                header4 = "header4-row1",
                header5 = "header5-row1"
            ),
            RowData(
                header1 = "header1-row2",
                header2 = "header2-row2",
                header3 = "header3-row2",
                header4 = "header4-row2",
                header5 = "header5-row2"
            )
        )

        val result = subject.template()

        headers.forEachIndexed { i, header ->
            assertEquals(
                header,
                result.getSheetAt(0).getRow(0).getCell(i).stringCellValue.trim(),
                "all headers should match template"
            )
        }

        data.forEachIndexed { i, rowData ->
            val currentRow = result.getSheetAt(0).getRow(i + 1)
            assertEquals(rowData.header1, currentRow.getCell(0).stringCellValue)
            assertEquals(rowData.header2, currentRow.getCell(1).stringCellValue)
            assertEquals(rowData.header3, currentRow.getCell(2).stringCellValue)
            assertEquals(rowData.header4, currentRow.getCell(3).stringCellValue)
            assertEquals(rowData.header5, currentRow.getCell(4).stringCellValue)
        }
    }

    data class RowData(
        var header1: String,
        var header2: String,
        var header3: String,
        var header4: String,
        var header5: String
    )
}
