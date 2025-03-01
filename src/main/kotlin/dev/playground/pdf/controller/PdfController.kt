package dev.playground.pdf.controller

import dev.playground.data.service.DataProvider
import dev.playground.pdf.service.PdfGeneratorService
import dev.playground.pdf.template.PdfTemplateType
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/pdf")
class PdfController(
    private val pdfGeneratorService: PdfGeneratorService,
    private val dataProvider: DataProvider
) {
    
    @GetMapping("/generate")
    fun generatePdf(
        @RequestParam type: PdfTemplateType,
        @RequestParam(required = false) id: Long?,
        response: HttpServletResponse
    ) {
        val data = dataProvider.getData(type, id)
        val pdfBytes = pdfGeneratorService.generatePdf(type, data)
        
        response.contentType = "application/pdf"
        response.setHeader("Content-Disposition", "attachment; filename=report.pdf")
        response.outputStream.write(pdfBytes)
    }

}