package dev.playground.pdf.template

import dev.playground.pdf.exception.UnsupportedPdfTemplateTypeException

object PdfTemplateFactory {
    
    fun <T> getTemplate(type: PdfTemplateType): PdfTemplate<T> {
        return when (type) {
            PdfTemplateType.ACCOUNT -> AccountPdfTemplate() as PdfTemplate<T>
            PdfTemplateType.TRANSACTION -> TransactionPdfTemplate() as PdfTemplate<T>
            else -> throw UnsupportedPdfTemplateTypeException("Unsupported PdfTemplateType: $type")
        }
    }

}