package dev.playground.pdf.template

import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDPage
import org.apache.pdfbox.pdmodel.PDPageContentStream
import org.apache.pdfbox.pdmodel.font.PDType1Font
import org.apache.pdfbox.pdmodel.font.Standard14Fonts
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject
import org.springframework.core.io.ClassPathResource
import java.io.ByteArrayOutputStream

abstract class PdfTemplate<T> {
    
    fun generatePdf(data: T): ByteArray {
        PDDocument().use { document ->
            val page = PDPage()
            document.addPage(page)
            
            PDPageContentStream(document, page).use { contentStream ->
                addHeader(contentStream, document)
                addContent(contentStream, data)
                addFooter(contentStream)
            }
            
            ByteArrayOutputStream().use { outputStream ->
                document.save(outputStream)
                return outputStream.toByteArray()
            }
        }
    }
    
    protected abstract fun addContent(contentStream: PDPageContentStream, data: T)
    
    protected open fun addHeader(contentStream: PDPageContentStream, document: PDDocument) {
        val resource = ClassPathResource("logo.jpg")
        val imageStream = resource.inputStream
        val logo = PDImageXObject.createFromByteArray(document, imageStream.readBytes(), "logo")
        
        val pageWidth = document.pages.first().mediaBox.width
        val padding = 20f
        val logoWidth = 100f
        val logoHeight = 50f
        val xPosition = pageWidth - logoWidth - padding
        val yPosition = document.pages.first().mediaBox.upperRightY - logoHeight - padding
        contentStream.drawImage(logo, xPosition, yPosition, logoWidth, logoHeight)
        
        val titleYPosition = yPosition - 20f
        
        contentStream.beginText()
        contentStream.setFont(PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 16f)
        contentStream.newLineAtOffset(100f, titleYPosition)
        contentStream.showText("Report")
        contentStream.endText()
    }
    
    protected open fun addFooter(contentStream: PDPageContentStream) {
        contentStream.beginText()
        contentStream.setFont(PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 10f)
        contentStream.newLineAtOffset(100f, 50f)
        contentStream.showText("Confidential - For internal use only")
        contentStream.endText()
    }
}