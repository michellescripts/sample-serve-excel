package com.example.excel.controller

import com.example.excel.service.DownloadService
import org.springframework.core.io.ByteArrayResource
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.io.ByteArrayOutputStream

@RestController
@RequestMapping("/api/v1/download")
class DownloadController(
    val downloadService: DownloadService
) {
    @GetMapping("/template")
    fun downloadFileFromTemplate(): ResponseEntity<ByteArrayResource> {
        val stream = ByteArrayOutputStream()
        val header = HttpHeaders()
        val workbook = downloadService.template()
        val fileName = "file_template.xlsx"

        header.contentType = MediaType.APPLICATION_OCTET_STREAM
        header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=$fileName")
        workbook.write(stream)
        workbook.close()
        return ResponseEntity(
            ByteArrayResource(stream.toByteArray()),
            header,
            HttpStatus.OK
        )
    }
}
