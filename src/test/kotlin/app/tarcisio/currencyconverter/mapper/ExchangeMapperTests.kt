package app.tarcisio.currencyconverter.mapper

import app.tarcisio.currencyconverter.dto.application.ExchangeResponseFixture
import app.tarcisio.currencyconverter.dto.web.ExchangeRateApiResponseFixture
import app.tarcisio.currencyconverter.entity.TransactionHistoryEntityFixture
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class ExchangeMapperTests {

    @Test
    fun `mapToTransactionHistoryEntity should correctly map ExchangeRateApiResponse to TransactionHistoryEntity`() {

        val apiResponse = ExchangeRateApiResponseFixture.mapperExample()
        val expectedEntity = TransactionHistoryEntityFixture.mapperExample()

        val actualEntity = ExchangeMapper.mapToTransactionHistoryEntity(apiResponse)

        assertEquals(expectedEntity.sourceCurrency, actualEntity.sourceCurrency)
        assertEquals(expectedEntity.amount, actualEntity.amount)
        assertEquals(expectedEntity.targetCurrency, actualEntity.targetCurrency)
        assertEquals(expectedEntity.exchangeRate, actualEntity.exchangeRate)
        assertEquals(expectedEntity.registrationDate?.toLocalDate(), actualEntity.registrationDate?.toLocalDate())
    }

    @Test
    fun `mapToExchangeResponse should correctly map TransactionHistoryEntity to ExchangeResponse`() {

        val transactionEntity = TransactionHistoryEntityFixture.mapperExample()
        val expectedResponse = ExchangeResponseFixture.exampleMapper()

        val actualResponse = ExchangeMapper.mapToExchangeResponse(transactionEntity)

        assertEquals(expectedResponse.id, actualResponse.id)
        assertEquals(expectedResponse.userId, actualResponse.userId)
        assertEquals(expectedResponse.sourceCurrency, actualResponse.sourceCurrency)
        assertEquals(expectedResponse.sourceAmount, actualResponse.sourceAmount)
        assertEquals(expectedResponse.targetCurrency, actualResponse.targetCurrency)
        assertEquals(expectedResponse.exchangeRate, actualResponse.exchangeRate)
        assertEquals(expectedResponse.registrationDate, actualResponse.registrationDate)
        assertEquals(expectedResponse.targetAmount, actualResponse.targetAmount)
    }
}