package app.tarcisio.currencyconverter.entity

import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDateTime

object TransactionHistoryEntityFixture {

    fun example(): TransactionHistoryEntity {
        return TransactionHistoryEntity(
            id = 1L,
            userId = 1L,
            sourceCurrency = "USD",
            amount = BigDecimal.valueOf(100.00),
            targetCurrency = "BRL",
            exchangeRate = 5.0,
            registrationDate = LocalDateTime.now()
        )
    }

    fun mapperExample(): TransactionHistoryEntity {
        return TransactionHistoryEntity(
            id = 1L,
            userId = 1,
            sourceCurrency = "USD",
            amount = BigDecimal("10.00").setScale(2, RoundingMode.HALF_EVEN),
            targetCurrency = "BRL",
            exchangeRate = 5.50,
            registrationDate = LocalDateTime.of(2020, 1, 1, 1, 0, 0).withNano(0)
        )
    }

    fun createTransactionHistoryEntity1(): TransactionHistoryEntity {
        return TransactionHistoryEntity(
            id = null,
            userId = 123L,
            sourceCurrency = "USD",
            amount = BigDecimal("100.50"),
            targetCurrency = "BRL",
            exchangeRate = 5.077,
            registrationDate = LocalDateTime.now()
        )
    }

    fun createTransactionHistoryEntity2(): TransactionHistoryEntity {
        return TransactionHistoryEntity(
            id = null,
            userId = 456L,
            sourceCurrency = "EUR",
            amount = BigDecimal("50.00"),
            targetCurrency = "USD",
            exchangeRate = 1.0864,
            registrationDate = LocalDateTime.now().minusDays(1).withHour(15).withMinute(30).withSecond(0).withNano(0)
        )
    }

}