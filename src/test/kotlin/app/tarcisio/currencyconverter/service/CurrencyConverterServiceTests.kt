package app.tarcisio.currencyconverter.service

import app.tarcisio.currencyconverter.client.ExchangeRateClient
import app.tarcisio.currencyconverter.common.TransactionalContext
import app.tarcisio.currencyconverter.dto.application.ExchangeRequestFixture
import app.tarcisio.currencyconverter.dto.application.ExchangeResponse
import app.tarcisio.currencyconverter.dto.application.ExchangeResponseFixture
import app.tarcisio.currencyconverter.dto.web.ExchangeRateApiResponseFixture
import app.tarcisio.currencyconverter.entity.TransactionHistoryEntity
import app.tarcisio.currencyconverter.entity.TransactionHistoryEntityFixture
import app.tarcisio.currencyconverter.repository.TransactionHistoryRespository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

@ExtendWith(MockitoExtension::class)
class CurrencyConverterServiceTests {

    @Mock
    private lateinit var client: ExchangeRateClient

    @Mock
    private lateinit var transactionHistoryRespository: TransactionHistoryRespository

    private lateinit var currencyConverterService: CurrencyConverterService

    private val apiKey = "test-api-key"

    @BeforeEach
    fun setUp() {
        currencyConverterService = CurrencyConverterService(
            client,
            transactionHistoryRespository,
            apiKey
        )
    }

    @Test
    fun `peformExchange must perform the conversion and save the history`() {

        val transactionalContext = TransactionalContext(ExchangeRequestFixture.example())
        val apiResponse = ExchangeRateApiResponseFixture.example()
        val transactionEntity = TransactionHistoryEntityFixture.example()
        val exchangeResponse = ExchangeResponseFixture.example()

        `when`(client.getExchange(transactionalContext.addHeader("apiKey", apiKey))).thenReturn(apiResponse)
        `when`(transactionHistoryRespository.save(ArgumentMatchers.any(TransactionHistoryEntity::class.java))).thenReturn(transactionEntity)

        val response = currencyConverterService.peformExchange(transactionalContext)

        // Assert
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(exchangeResponse.exchangeRate, response.body?.exchangeRate)
        assertEquals(exchangeResponse.sourceCurrency, response.body?.sourceCurrency)
        assertEquals(exchangeResponse.sourceAmount, response.body?.sourceAmount)
        assertEquals(exchangeResponse.userId, response.body?.userId)
        assertEquals(exchangeResponse.id, response.body?.id)

        verify(client, times(1)).getExchange(transactionalContext.addHeader("apiKey", apiKey))
        verify(transactionHistoryRespository, times(1)).save(any(TransactionHistoryEntity::class.java))
    }

    @Test
    fun `getTransactions should return the list of transactions for an existing user`() {

        val userId = 1L

        val transactionList = listOf(
            TransactionHistoryEntityFixture.example().copy(
                id = 1L,
                userId = userId
            ),
            TransactionHistoryEntityFixture.example().copy(
                id = 2L,
                userId = userId,
                sourceCurrency = "EUR",
                targetCurrency = "USD"
            )
        )

        val exchangeResponseList = listOf(
            ExchangeResponseFixture.example().copy(
                id = 1L,
                userId = userId
            ),
            ExchangeResponseFixture.example().copy(
                id = 2L,
                userId = userId,
                sourceCurrency = "EUR",
                targetCurrency = "USD"
            )
        )

        `when`(transactionHistoryRespository.findAllByUserId(userId)).thenReturn(transactionList)

        val response: ResponseEntity<List<ExchangeResponse>> = currencyConverterService.getTransactions(userId)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(exchangeResponseList.size, response.body?.size)
        assertEquals(exchangeResponseList[0].id, response.body?.get(0)?.id)
        assertEquals(exchangeResponseList[1].id, response.body?.get(1)?.id)

        verify(transactionHistoryRespository, times(1)).findAllByUserId(userId)
    }

    @Test
    fun `getTransactions should return NotFound when no transactions are found for the user`() {

        val userId = 2L
        `when`(transactionHistoryRespository.findAllByUserId(userId)).thenReturn(emptyList())

        val response: ResponseEntity<List<ExchangeResponse>> = currencyConverterService.getTransactions(userId)

        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
        assertEquals(null, response.body)

        verify(transactionHistoryRespository, times(1)).findAllByUserId(userId)
    }
}