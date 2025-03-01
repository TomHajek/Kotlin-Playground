package dev.playground.pdf.template

import dev.playground.data.persistence.dto.AccountDto
import org.apache.pdfbox.pdmodel.PDPageContentStream
import org.apache.pdfbox.pdmodel.font.PDType1Font
import org.apache.pdfbox.pdmodel.font.Standard14Fonts

class AccountPdfTemplate : PdfTemplate<AccountDto>() {
    
    override fun addContent(contentStream: PDPageContentStream, data: AccountDto) {
        var yOffset = 650f
        
        // Header
        contentStream.beginText()
        contentStream.setFont(PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 14f)
        contentStream.newLineAtOffset(100f, yOffset)
        contentStream.showText("Account Holder: ${data.firstName} ${data.lastName}")
        contentStream.endText()
        yOffset -= 20f
        
        contentStream.beginText()
        contentStream.setFont(PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 12f)
        contentStream.newLineAtOffset(100f, yOffset)
        contentStream.showText("Email: ${data.email}")
        contentStream.endText()
        yOffset -= 20f
        
        contentStream.beginText()
        contentStream.setFont(PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 12f)
        contentStream.newLineAtOffset(100f, yOffset)
        contentStream.showText("Balance: ${data.currency} ${data.balance}")
        contentStream.endText()
        yOffset -= 30f
        
        // Horizontal Line
        contentStream.moveTo(100f, yOffset)
        contentStream.lineTo(500f, yOffset)
        contentStream.stroke()
        yOffset -= 20f
        
        // Transactions
        for (transaction in data.transactions) {
            contentStream.beginText()
            contentStream.setFont(PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 12f)
            contentStream.newLineAtOffset(100f, yOffset)
            contentStream.showText("${transaction.currency} ${transaction.amount} - ${transaction.description}")
            contentStream.endText()
            yOffset -= 20f
        }
    }
    
}