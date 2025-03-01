package dev.playground.data.service

import dev.playground.pdf.template.PdfTemplateType
import org.springframework.stereotype.Service

@Service
class DataProvider(
    private val accountService: AccountService,
    private val transactionService: TransactionService,
) {
    
    fun getData(pdfTemplateType: PdfTemplateType, id: Long?): Any {
        return when(pdfTemplateType) {
            PdfTemplateType.TRANSACTION -> {
                if (id != null) {
                    transactionService.getTransactionsByAccountId(id)
                } else {
                    transactionService.getTransactions()
                }
            }
            PdfTemplateType.ACCOUNT -> {
                id?.let { accountService.getAccountById(it) }
                    ?: throw IllegalArgumentException("Account id is missing!")
            }
        }
    }
    
}