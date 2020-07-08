package com.example.excel.controller

import com.example.excel.service.DownloadService
import io.mockk.every
import io.mockk.mockk
import org.apache.poi.ss.usermodel.WorkbookFactory
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.http.ContentDisposition
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient
import java.io.File
import java.io.FileInputStream

@ExtendWith(SpringExtension::class)
@WebFluxTest(DownloadController::class)
internal class DownloadControllerTest {
    @Autowired
    lateinit var webTestClient: WebTestClient

    @TestConfiguration
    class Mockks {
        @Bean
        fun downloadService() = mockk<DownloadService>()
    }

    @Autowired
    lateinit var downloadService: DownloadService

    @Test
    fun `downloadFileFromTemplate downloads a file created from a template`() {
        val fileName = "file_template.xlsx"
        val fileInputStream = FileInputStream(File(this.javaClass.classLoader.getResource(fileName)!!.file))
        val excel = WorkbookFactory.create(fileInputStream)

        every { downloadService.template() } returns excel

        webTestClient
            .get()
            .uri("/api/v1/download/template")
            .exchange()
            .expectStatus().isOk
            .expectHeader().contentType(MediaType.APPLICATION_OCTET_STREAM)
            .expectHeader().contentDisposition(ContentDisposition.parse("attachment; filename=file_template.xlsx"))
            .expectBody().consumeWith { response ->
                assertTrue(response.responseBody!!.isNotEmpty())
            }
    }
}
