package app.tarcisio.currencyconverter.dto.application

import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDateTime

object ExchangeResponseFixture {

    fun example(): ExchangeResponse {
        return ExchangeResponse(
            1L,
            1L,
            "USD",
            BigDecimal("100.00"),
            "BRL",
            BigDecimal("500.00"),
            BigDecimal(5.0),
            LocalDateTime.now()
        )
    }

    fun exampleMapper(): ExchangeResponse {
        return ExchangeResponse(
            id = 1L,
            userId = 1,
            sourceCurrency = "USD",
            sourceAmount = BigDecimal("10.00").setScale(2, RoundingMode.HALF_EVEN),
            targetCurrency = "BRL",
            exchangeRate = BigDecimal("5.50"),
            registrationDate = LocalDateTime.of(2020, 1, 1, 1, 0),
            targetAmount = BigDecimal("55.00").setScale(2, RoundingMode.HALF_EVEN)
        )
    }

}
