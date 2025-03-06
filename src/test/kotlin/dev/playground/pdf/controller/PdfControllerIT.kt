package dev.playground.pdf.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import kotlin.test.Test

@SpringBootTest
@AutoConfigureMockMvc
class PdfControllerIT {
    
    @Autowired
    private lateinit var mockMvc: MockMvc
    
    @Test
    fun `should return PDF for all transactions`() {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/pdf/generate")
            .param("type", "TRANSACTION")
            .accept(MediaType.APPLICATION_PDF))
            .andExpect(status().isOk)
            .andExpect(content().contentType("application/pdf"))
    }
    
    @Test
    fun `should return PDF for account with ID`() {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/pdf/generate")
            .param("type", "ACCOUNT")
            .param("id", "1")
            .accept(MediaType.APPLICATION_PDF))
            .andExpect(status().isOk)
            .andExpect(content().contentType("application/pdf"))
    }
    
    @Test
    fun `should return 400 when account ID is missing`() {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/pdf/generate")
            .param("type", "ACCOUNT")
            .accept(MediaType.APPLICATION_PDF))
            .andExpect(status().isBadRequest)
    }

}