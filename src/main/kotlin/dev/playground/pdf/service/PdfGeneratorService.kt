package dev.playground.pdf.service

import dev.playground.pdf.template.PdfTemplate
import dev.playground.pdf.template.PdfTemplateFactory
import dev.playground.pdf.template.PdfTemplateType
import org.springframework.stereotype.Service

@Service
class PdfGeneratorService {

    fun <T> generatePdf(type: PdfTemplateType, data: T): ByteArray {
        val pdfTemplate: PdfTemplate<T> = PdfTemplateFactory.getTemplate(type)
        return pdfTemplate.generatePdf(data)
    }
    
}