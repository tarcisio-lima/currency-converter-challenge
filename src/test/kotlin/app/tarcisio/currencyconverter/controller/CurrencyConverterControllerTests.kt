package app.tarcisio.currencyconverter.controller

import app.tarcisio.currencyconverter.dto.application.ExchangeRequestFixture
import app.tarcisio.currencyconverter.dto.application.ExchangeResponseFixture
import app.tarcisio.currencyconverter.exception.ExceptionFixture
import app.tarcisio.currencyconverter.service.CurrencyConverterService
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import java.math.BigDecimal

@WebMvcTest(CurrencyConverterController::class)
class CurrencyConverterControllerTests {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @MockitoBean
    lateinit var currencyConverterService: CurrencyConverterService

    @Test
    fun `peformExchange should return ExchangeResponse and OK status`() {

        whenever(currencyConverterService.peformExchange(any())).thenReturn(
            ResponseEntity.ok(ExchangeResponseFixture.example())
        )

        mockMvc.post("/currency-converter/exchange") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(ExchangeRequestFixture.example())
            accept = MediaType.APPLICATION_JSON
        }
            .andExpect {
                status { isOk() }
            }
            .andExpect {
                content().contentType(MediaType.APPLICATION_JSON)
            }
            .andExpect {
                jsonPath("$.userId").value(1)
            }
            .andExpect {
                jsonPath("$.targetAmount").value(500.00)
            }
            .andExpect {
                jsonPath("$.exchangeRate").value(5.0)
            }
    }

    @Test
    fun `peformExchange should return ERROR status`() {

        whenever(currencyConverterService.peformExchange(any())).thenThrow(
            ExceptionFixture.restClientResponseException(HttpStatus.BAD_REQUEST)
        )

        mockMvc.post("/currency-converter/exchange") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(ExchangeRequestFixture.example())
            accept = MediaType.APPLICATION_JSON
        }
            .andExpect {
                status { isBadRequest() }
            }
            .andExpect {
                content().contentType(MediaType.APPLICATION_JSON)
            }
            .andExpect {
                jsonPath("$.message").isNotEmpty
            }
    }

    @Test
    fun `getTransactions should return a list of ExchangeResponse and OK status`() {
        val userId = 1L
        val transactions = listOf(
            ExchangeResponseFixture.example(),
            ExchangeResponseFixture.example().copy(
                sourceCurrency = "EUR",
                exchangeRate = 4.0,
                targetAmount = BigDecimal("400.00")
            )
        )

        whenever(currencyConverterService.getTransactions(userId)).thenReturn(ResponseEntity.ok(transactions))

        mockMvc.get("/currency-converter/transactions/{userId}", userId) {
            accept = MediaType.APPLICATION_JSON
        }
            .andExpect {
                status { isOk() }
            }
            .andExpect {
                content().contentType(MediaType.APPLICATION_JSON)
            }
            .andExpect {
                jsonPath("$[0].userId").value(1)
            }
            .andExpect {
                jsonPath("$[0].targetAmount").value(500.00)
            }
            .andExpect {
                jsonPath("$[0].exchangeRate").value(5.0)
            }
            .andExpect {
                jsonPath("$[0].sourceCurrency").value("USD")
            }
            .andExpect {
                jsonPath("$[1].userId").value(1)
            }
            .andExpect {
                jsonPath("$[1].targetAmount").value(400.00)
            }
            .andExpect {
                jsonPath("$[1].exchangeRate").value(4.0)
            }
            .andExpect {
                jsonPath("$[1].sourceCurrency").value("EUR")
            }
    }

    @Test
    fun `getTransactions should return NOT FOUND status`() {
        val userId = 2L

        whenever(currencyConverterService.getTransactions(userId)).thenThrow(
            ExceptionFixture.restClientResponseException(HttpStatus.NOT_FOUND)
        )

        mockMvc.get("/currency-converter/transactions/{userId}", userId) {
            accept = MediaType.APPLICATION_JSON
        }
            .andExpect {
                status { isNotFound() }
            }
            .andExpect {
                content().contentType(MediaType.APPLICATION_JSON)
            }
            .andExpect {
                jsonPath("$.message").isNotEmpty
            }

    }


}