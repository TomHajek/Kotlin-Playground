package dev.playground.pdf.template

import dev.playground.data.persistence.dto.TransactionDto
import org.apache.pdfbox.pdmodel.PDPageContentStream
import org.apache.pdfbox.pdmodel.font.PDType1Font
import org.apache.pdfbox.pdmodel.font.Standard14Fonts

class TransactionPdfTemplate : PdfTemplate<List<TransactionDto>>() {
    
    override fun addContent(contentStream: PDPageContentStream, data: List<TransactionDto>) {
        var yOffset = 650f
        var currentAccountId: Long? = null
        
        for (transaction in data) {
            if (transaction.accountId != currentAccountId) {
                currentAccountId = transaction.accountId
                yOffset -= 20f
                contentStream.moveTo(100f, yOffset)
                contentStream.lineTo(500f, yOffset)
                contentStream.stroke()
                yOffset -= 20f
                contentStream.beginText()
                contentStream.setFont(PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 12f)
                contentStream.newLineAtOffset(100f, yOffset)
                contentStream.showText("Account id: ${currentAccountId}")
                contentStream.endText()
                yOffset -= 20f
            }
            contentStream.beginText()
            contentStream.setFont(PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 12f)
            contentStream.newLineAtOffset(100f, yOffset)
            contentStream.showText("${transaction.currency} ${transaction.amount} - ${transaction.description}")
            contentStream.endText()
            yOffset -= 20f
        }
    }
    
}